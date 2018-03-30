package com.saffron.tabon.model

/**
 * Created by Rahul on 07-03-2018.
 */
class NavDrawerItem {
    var isShowNotify: Boolean = false
    var title: String? = null


    constructor() {

    }

    constructor(showNotify: Boolean, title: String) {
        this.isShowNotify = showNotify
        this.title = title
    }
}
