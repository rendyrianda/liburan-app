package com.example.holiyay.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Model (
    var title : String,
    var desc : String,
    var address : String,
    var price : String,
    var image : Int

) : Parcelable