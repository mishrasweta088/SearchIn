package com.cg.project.searchin


class ModelPost {
    var pId: String? = null
    var pTitle: String? = null
    var pDescr: String? = null
    var pImage: String? = null
    var pTime: String? = null
    var uid: String? = null
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
        this.uid = uId
        this.uEmail = uEmail
        this.uDp = uDp
        this.uName = uName
    }


}