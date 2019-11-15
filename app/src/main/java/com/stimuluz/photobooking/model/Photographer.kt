package com.stimuluz.photobooking.model

import java.io.Serializable

data class Photographer(
    var name: String,
    var picture: String,
    var location: String,
    var phone: String,
    var about: String,
    var portfolio: ArrayList<String>?,
    var fee: Long

) : Serializable