package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class todo_item(
    var discription: String? ="", var isdone:Boolean?=false,
    var created:String?="",
    var last_updat:String?="",
    var id:Int=0):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(discription)
        parcel.writeValue(isdone)
        parcel.writeString(created)
        parcel.writeString(last_updat)
        parcel.writeInt(id)
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
