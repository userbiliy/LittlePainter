package com.example.littlepainter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (val name:String,val num:Int): Parcelable {
}