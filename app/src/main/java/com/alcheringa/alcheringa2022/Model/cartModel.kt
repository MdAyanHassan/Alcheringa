package com.alcheringa.alcheringa2022.Model

import android.os.Parcel
import android.os.Parcelable

class cartModel : Parcelable {
    @JvmField
    var name: String?
    @JvmField
    var type: String?
    @JvmField
    var size: String?
    @JvmField
    var price: String?
    @JvmField
    var image: String?
    @JvmField
    var count: String?
    var Image: String?

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        type = `in`.readString()
        size = `in`.readString()
        price = `in`.readString()
        image = `in`.readString()
        count = `in`.readString()
        Image = `in`.readString()
    }

    constructor(
        name: String?,
        type: String?,
        size: String?,
        price: String?,
        image: String?,
        count: String?,
        image1: String?
    ) {
        this.name = name
        this.type = type
        this.size = size
        this.price = price
        this.image = image
        this.count = count
        Image = image1
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(size)
        parcel.writeString(price)
        parcel.writeString(image)
        parcel.writeString(count)
        parcel.writeString(Image)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<cartModel?> = object : Parcelable.Creator<cartModel?> {
            override fun createFromParcel(`in`: Parcel): cartModel? {
                return cartModel(`in`)
            }

            override fun newArray(size: Int): Array<cartModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
