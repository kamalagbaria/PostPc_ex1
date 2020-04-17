package com.example.myapplication

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
data class todo_item(var discription: String?, var isdone:Boolean?=null):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(discription)
        parcel.writeValue(isdone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<todo_item> {
        override fun createFromParcel(parcel: Parcel): todo_item {
            return todo_item(parcel)
        }

        override fun newArray(size: Int): Array<todo_item?> {
            return arrayOfNulls(size)
        }
    }

}
