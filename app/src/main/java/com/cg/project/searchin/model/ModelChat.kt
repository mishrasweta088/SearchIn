package com.cg.project.searchin.model

import android.os.Message
import android.os.ResultReceiver
import kotlin.properties.Delegates

class ModelChat {

    var message:String?=null
    var receiver:String?=null
    var sender:String?=null
    var timestamp:String?=null
    var isSeen by Delegates.notNull<Boolean>()

    constructor() {}

    constructor(
        message: String?,
        receiver: String?,
        sender: String?,
        timestamp: String?,
        isSeen: Boolean
    ) {
        this.message = message
        this.receiver = receiver
        this.sender = sender
        this.timestamp = timestamp
        this.isSeen = isSeen
    }
}