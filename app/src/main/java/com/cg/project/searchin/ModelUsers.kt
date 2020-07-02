package com.cg.project.searchin

class ModelUsers {

    var firstname: String? = null
    var lastname: String? = null
    var email: String? = null
    var search: String? = null
    var phone: String? = null
    var image: String? = null
    var cover: String? = null
    var uid: String?=null

    constructor() {}
    constructor(
        name: String?,
        lastname: String?,
        email: String?,
        search: String?,
        phone: String?,
        image: String?,
        cover: String?,
        uid: String?
    ) {
        this.firstname = name
        this.lastname = lastname
        this.email = email
        this.search = search
        this.phone = phone
        this.image = image
        this.cover = cover
        this.uid=uid
    }


}