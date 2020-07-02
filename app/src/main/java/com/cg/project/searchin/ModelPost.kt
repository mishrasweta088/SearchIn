package com.cg.project.searchin


class ModelPost {
    var pId: String? = null
    var pTitle: String? = null
    var pDescr: String? = null
    var pImage: String? = null
    var pTime: String? = null
    var uId: String? = null
    var uEmail: String? = null
    var uDp: String? = null
    var uName: String? = null

    constructor() {}
    constructor(
        pId: String?,
        pDescr: String?,
        pTitle: String?,
        pImage: String?,
        pTime: String?,
        uId: String?,
        uEmail: String?,
        uDp: String?,
        uName: String?
    ) {
        this.pId = pId
        this.pDescr = pDescr
        this.pTitle = pTitle
        this.pImage = pImage
        this.pTime = pTime
        this.uId = uId
        this.uEmail = uEmail
        this.uDp = uDp
        this.uName = uName
    }

    fun getpId(): String? {
        return pId
    }

    fun setpId(pId: String?) {
        this.pId = pId
    }

    fun getpTitle(): String? {
        return pTitle
    }

    fun setpTitle(pTitle: String?) {
        this.pTitle = pTitle
    }

    fun getpDescr(): String? {
        return pDescr
    }

    fun setpDescr(pDescr: String?) {
        this.pDescr = pDescr
    }

    fun getpImage(): String? {
        return pImage
    }

    fun setpImage(pImage: String?) {
        this.pImage = pImage
    }

    fun getpTime(): String? {
        return pTime
    }

    fun setpTime(pTime: String?) {
        this.pTime = pTime
    }

    fun getuId(): String? {
        return uId
    }

    fun setuId(uId: String?) {
        this.uId = uId
    }

    fun getuEmail(): String? {
        return uEmail
    }

    fun setuEmail(uEmail: String?) {
        this.uEmail = uEmail
    }

    fun getuDp(): String? {
        return uDp
    }

    fun setuDp(uDp: String?) {
        this.uDp = uDp
    }

    fun getuName(): String? {
        return uName
    }

    fun setuName(uName: String?) {
        this.uName = uName
    }
}