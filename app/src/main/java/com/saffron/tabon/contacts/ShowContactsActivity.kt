package com.saffron.tabon.contacts

import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.WRITE_CONTACTS
import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.saffron.tabon.R
import com.saffron.tabon.ui.MaterialLetterIcon
import com.saffron.tabon.ui.TextDrawable
import java.util.*

class ShowContactsActivity : AppCompatActivity() {
    private val RequestPermissionCode = 100
    internal var contactList: ArrayList<String>? = null
    internal var cursor: Cursor? = null
    internal var counter: Int = 0
    private var recyclerView: RecyclerView? = null
    private var pDialog: ProgressDialog? = null
    private var updateBarHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_contacts)
        recyclerView = findViewById(R.id.recyclerview)
        setupRecyclerView()

        pDialog = ProgressDialog(this)
        pDialog!!.setMessage("Reading contacts...")
        pDialog!!.setCancelable(false)
        updateBarHandler = Handler()


        if (checkPermission()) {

            if (!isFinishing)
                pDialog!!.show()

            Thread { getContacts() }.start()

        } else {

            requestPermission()
        }

    }

    @TargetApi(23)
    fun checkPermission(): Boolean {

        val result = ContextCompat.checkSelfPermission(applicationContext, WRITE_CONTACTS)
        val result1 = ContextCompat.checkSelfPermission(applicationContext, READ_CONTACTS)

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(23)
    private fun requestPermission() {

        ActivityCompat.requestPermissions(this, arrayOf(WRITE_CONTACTS, READ_CONTACTS), RequestPermissionCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            RequestPermissionCode -> if (grantResults.size > 0) {

                val WritePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val ReadPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if (WritePermission && ReadPermission) {

                    if (!isFinishing)
                        pDialog!!.show()

                    getContacts()
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()

                }
            }
        }
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    /*@Override
    public void onBackPressed() {
        showDialog();
    }*/

    private fun showDialog() {
        AlertDialog.Builder(this).setMessage("Are you sure want to exit from app?").setPositiveButton("Yes") { dialogInterface, i ->
            dialogInterface.dismiss()
            finish()
        }.setNegativeButton("No") { dialogInterface, i -> dialogInterface.dismiss() }.setNeutralButton("Rate us") { dialogInterface, i ->
            dialogInterface.dismiss()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=com.imagelettericon")
            startActivity(intent)
        }.create().show()
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.contacts:
                setContactsAdapter(desuNoto);
                return true;
            case R.id.countries:
                setCountriesAdapter(countries);
                return true;
            case R.id.alternate_countries:
                setAlternateCountriesAdapter(countries);
                return true;
            case R.id.alternate_contacts:
                setAlternateContactsAdapter(countries);
                return true;
            case R.id.play_store:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.imagelettericon"));
                startActivity(intent);
                return true;
            case R.id.other_apps:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://search?q=pub:AkashJain"));
                startActivity(intent);
                return true;
            case R.id.github:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://github.com/akashandroid90/ImageLetterIcon"));
                startActivity(intent);
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Material Icon");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=com.imagelettericon");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private fun setupRecyclerView() {
        recyclerView!!.layoutManager = LinearLayoutManager(recyclerView!!.context)
        //setContactsAdapter(desuNoto);
    }

    private fun setContactsAdapter(array: Array<String>) {
        recyclerView!!.adapter = SimpleStringRecyclerViewAdapter(this, Arrays.asList(*array), CONTACTS)
    }

    private fun setCountriesAdapter(array: Array<String>) {
        recyclerView!!.adapter = SimpleStringRecyclerViewAdapter(this, Arrays.asList(*array), COUNTRIES)
    }

    private fun setAlternateCountriesAdapter(array: Array<String>) {
        recyclerView!!.adapter = SimpleStringRecyclerViewAdapter(this, Arrays.asList(*array), ALTERNATECOUNTRIES)
    }

    private fun setAlternateContactsAdapter(array: Array<String>) {
        recyclerView!!.adapter = SimpleStringRecyclerViewAdapter(this, Arrays.asList(*array), ALTERNATECONTACTS)
    }

    fun getContacts() {

        contactList = ArrayList()

        var phoneNumber: String
        var email: String

        val CONTENT_URI = ContactsContract.Contacts.CONTENT_URI
        val _ID = ContactsContract.Contacts._ID
        val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
        val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER

        val PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER

        val EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        val EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID
        val DATA = ContactsContract.CommonDataKinds.Email.DATA

        var output: StringBuffer

        val contentResolver = contentResolver

        cursor = contentResolver.query(CONTENT_URI, null, null, null, null)

        // Iterate every contact in the phone

        if (cursor != null && cursor!!.count > 0) {

            counter = 0
            while (cursor!!.moveToNext()) {
                output = StringBuffer()

                // Update the progress message
                updateBarHandler!!.post { pDialog!!.setMessage("Reading contacts : " + counter++ + "/" + cursor!!.count) }

                val contact_id = cursor!!.getString(cursor!!.getColumnIndex(_ID))
                val name = cursor!!.getString(cursor!!.getColumnIndex(DISPLAY_NAME))

                val hasPhoneNumber = Integer.parseInt(cursor!!.getString(cursor!!.getColumnIndex(HAS_PHONE_NUMBER)))

                if (hasPhoneNumber > 0) {

                    output/*.append("\n First Name:")*/.append(name)

                    //This is to read multiple phone numbers associated with the same contact
                    val phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", arrayOf(contact_id), null)


                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER))
                            //output.append("\n Phone number:").append(phoneNumber);
                            output.append("\n").append(phoneNumber)

                        }
                    }

                    phoneCursor?.close()

                    // Read every email id associated with the contact
                    val emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", arrayOf(contact_id), null)


                    if (emailCursor != null) {
                        while (emailCursor.moveToNext()) {

                            email = emailCursor.getString(emailCursor.getColumnIndex(DATA))

                            output.append("\n Email:").append(email)

                        }
                    }

                    emailCursor?.close()
                }

                // Add the contact to the ArrayList
                contactList!!.add(output.toString())
            }

            // ListView has to be updated using a ui thread
            runOnUiThread {
                /*ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.list_item, android.R.id.text1, contactList);
                mListView.setAdapter(adapter);*/
                recyclerView!!.adapter = SimpleStringRecyclerViewAdapter(
                        this@ShowContactsActivity, contactList!!, CONTACTS)
            }

            // Dismiss the progressbar after 500 milliSeconds
            updateBarHandler!!.postDelayed({
                if (!isFinishing)
                    pDialog!!.cancel()
            }, 500)
        }

    }

    override fun onStop() {
        super.onStop()

        if (pDialog != null) {
            pDialog!!.dismiss()
            pDialog = null
        }

    }

    class SimpleStringRecyclerViewAdapter internal constructor(context: Context, private val mValues: List<String>, private val mType: Int) : RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>() {

        private val mTypedValue = TypedValue()
        private val mBackground: Int
        private val mMaterialColors: IntArray
        private val mImageResource: IntArray

        init {
            context.theme.resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true)
            mMaterialColors = context.resources.getIntArray(R.array.colors)
            mImageResource = intArrayOf(R.drawable.one, R.drawable.one, R.drawable.one, R.drawable.one, R.drawable.one, R.drawable.one, R.drawable.one, R.drawable.one, R.drawable.one)
            mBackground = mTypedValue.resourceId
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            view.setBackgroundResource(mBackground)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            when (mType) {
                CONTACTS -> {
                    holder.mIcon.setImageDrawable(TextDrawable.builder().beginConfig().textColor(Color.WHITE).bold().toUpperCase().endConfig().buildRect("aa", Color.RED))
                    holder.mIcon.textSize = 18
                    //holder.mIcon.setOval(false);
                    holder.mIcon.textColor = Color.WHITE
                    holder.mIcon.borderColor = Color.BLACK
                    holder.mIcon.borderWidth = 1
                    holder.mIcon.shapeColor = mMaterialColors[RANDOM.nextInt(mMaterialColors.size)]
                    holder.mIcon.text = mValues[position]
                }
                COUNTRIES -> {
                    holder.mIcon.letterCount = 3
                    holder.mIcon.textSize = 16
                    holder.mIcon.isOval = true
                    holder.mIcon.text = mValues[position]
                    holder.mIcon.shapeColor = mMaterialColors[RANDOM.nextInt(mMaterialColors.size)]
                }
                ALTERNATECOUNTRIES -> if (position % 2 == 0) {
                    holder.mIcon.isOval = true
                    holder.mIcon.setImageResource(mImageResource[RANDOM.nextInt(mImageResource.size)])
                } else {
                    holder.mIcon.isOval = true
                    holder.mIcon.shapeColor = mMaterialColors[RANDOM.nextInt(mMaterialColors.size)]
                    holder.mIcon.text = mValues[position]
                    holder.mIcon.letterCount = 2
                    holder.mIcon.textSize = 18
                }
                ALTERNATECONTACTS -> {
                    holder.mIcon.cornerRadius = holder.itemView.resources.getDimensionPixelSize(R.dimen._50sdp)
                    if (position % 2 == 0) {
                        holder.mIcon.isOval = false
                        holder.mIcon.setImageResource(mImageResource[RANDOM.nextInt(mImageResource.size)])
                    } else {
                        holder.mIcon.isOval = false
                        holder.mIcon.shapeColor = mMaterialColors[RANDOM.nextInt(mMaterialColors.size)]
                        holder.mIcon.text = mValues[position]
                        holder.mIcon.letterCount = 2
                        holder.mIcon.textSize = 18
                    }
                }
            }
            holder.mBoundString = mValues[position]
            holder.mTextView.text = mValues[position]
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mIcon: MaterialLetterIcon
            val mTextView: TextView
            var mBoundString: String? = null

            init {
                mIcon = mView.findViewById(R.id.icon)
                mTextView = mView.findViewById(android.R.id.text1)
            }

            override fun toString(): String {
                return super.toString() + " '" + mTextView.text
            }
        }
    }

    companion object {

        private val desuNoto = arrayOf("Alane Avey", "Belen Brewster", "Brandon Brochu", "Carli Carrol", "Della Delrio", "Esther Echavarria", "Etha Edinger", "Felipe Flecha", "Ilse Island", "Kecia Keltz", "Lourie Lucas", "Lucille Leachman", "Mandi Mcqueeney", "Murray Matchett", "Nadia Nero", "Nannie Nipp", "Ozella Otis", "Pauletta Poehler", "Roderick Rippy", "Sherril Sager", "Taneka Tenorio", "Treena Trentham", "Ulrike Uhlman", "Virgina Viau", "Willis Wysocki")
        private val countries = arrayOf("Albania", "Australia", "Belgium", "Canada", "China", "Dominica", "Egypt", "Estonia", "Finland", "France", "Germany", "Honduras", "Italy", "Japan", "Madagascar", "Netherlands", "Norway", "Panama", "Portugal", "Romania", "Russia", "Slovakia", "Vatican", "Zimbabwe")
        private val CONTACTS = 0
        private val COUNTRIES = 1
        private val ALTERNATECOUNTRIES = 2
        private val ALTERNATECONTACTS = 3
        private val RANDOM = Random()
    }
}
