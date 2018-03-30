/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.saffron.tabon.placecomplete

import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.PlaceBufferResponse
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.RuntimeRemoteException
import com.google.android.gms.tasks.OnCompleteListener
import com.saffron.tabon.R

class PlaceCompleteActivity : SampleActivityBase() {
    /**
     * GeoDataClient wraps our service connection to Google Play services and provides access
     * to the Google Places API for Android.
     */
    protected lateinit var mGeoDataClient: GeoDataClient
    private var mAdapter: PlaceAutocompleteAdapter? = null
    private var mAutocompleteView: AutoCompleteTextView? = null
    private var mPlaceDetailsText: TextView? = null
    private var mPlaceDetailsAttribution: TextView? = null
    /**
     * Callback for results from a Places Geo Data Client query that shows the first place result in
     * the details view on screen.
     */
    private val mUpdatePlaceDetailsCallback = OnCompleteListener<PlaceBufferResponse> { task ->
        try {
            val places = task.result

            // Get the Place object from the buffer.
            val place = places.get(0)

            // Format details of the place for display and show it in a TextView.
            mPlaceDetailsText!!.text = formatPlaceDetails(resources, place.name,
                    place.id, place.address, place.phoneNumber,
                    place.websiteUri)

            // Display the third party attributions if set.
            val thirdPartyAttribution = places.attributions
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution!!.visibility = View.GONE
            } else {
                mPlaceDetailsAttribution!!.visibility = View.VISIBLE
                mPlaceDetailsAttribution!!.text = Html.fromHtml(thirdPartyAttribution.toString())
            }

            Log.i(SampleActivityBase.TAG, "Place details received: " + place.name)

            places.release()
        } catch (e: RuntimeRemoteException) {
            // Request did not complete successfully
            Log.e(SampleActivityBase.TAG, "Place query did not complete.", e)
            return@OnCompleteListener
        }
    }
    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data Client
     * to retrieve more details about the place.
     *
     * @see GeoDataClient.getPlaceById
     */
    private val mAutocompleteClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
        val item = mAdapter!!.getItem(position)
        val placeId = item!!.placeId
        val primaryText = item.getPrimaryText(null)

        Log.i(SampleActivityBase.TAG, "Autocomplete item selected: " + primaryText)

        /*
             Issue a request to the Places Geo Data Client to retrieve a Place object with
             additional details about the place.
              */
        val placeResult = mGeoDataClient.getPlaceById(placeId)
        placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback)

        Toast.makeText(applicationContext, "Clicked: " + primaryText,
                Toast.LENGTH_SHORT).show()
        Log.i(SampleActivityBase.TAG, "Called getPlaceById to get Place details for " + placeId!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Construct a GeoDataClient for the Google Places API for Android.
        mGeoDataClient = Places.getGeoDataClient(this, null)

        setContentView(R.layout.place_complete)

        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        mAutocompleteView = findViewById(R.id.autocomplete_places)

        // Register a listener that receives callbacks when a suggestion has been selected
        mAutocompleteView!!.onItemClickListener = mAutocompleteClickListener

        // Retrieve the TextViews that will display details and attributions of the selected place.
        mPlaceDetailsText = findViewById(R.id.place_details)
        mPlaceDetailsAttribution = findViewById(R.id.place_attribution)

        // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
        mAdapter = PlaceAutocompleteAdapter(this, mGeoDataClient, BOUNDS_GREATER_SYDNEY, null)
        mAutocompleteView!!.setAdapter<PlaceAutocompleteAdapter>(mAdapter)

        // Set up the 'clear text' button that clears the text in the autocomplete view
        val clearButton = findViewById<Button>(R.id.button_clear)
        clearButton.setOnClickListener { v -> mAutocompleteView!!.setText("") }
    }

    companion object {

        private val BOUNDS_GREATER_SYDNEY = LatLngBounds(
                LatLng(-34.041458, 150.790100), LatLng(-33.682247, 151.383362))

        private fun formatPlaceDetails(res: Resources, name: CharSequence, id: String,
                                       address: CharSequence, phoneNumber: CharSequence, websiteUri: Uri): Spanned {
            Log.e(SampleActivityBase.TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                    websiteUri))
            return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                    websiteUri))

        }
    }
}
