package com.example.foodorderapp.model

import android.os.Parcel
import android.os.Parcelable

class OrderDetails():Parcelable {

    var userId:String?=null
    var userName:String?=null
    var address:String?=null
    var totalPrice:String?=null
    var phoneNumber:String?=null
    var orderAccepted:Boolean=false
    var paymentReceived:Boolean=false
    var itemPushKey:String?=null
    var currentTime:Long=0
    var itemList: MutableList<CartItems>? = null

    constructor(parcel: Parcel) : this() {
        userId = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        address: String,
        total: Double,
        phone: String,
        b: Boolean,
        b1: Boolean,
        itemPushKey: String?,
        time: Long,
        listItemCount: MutableList<CartItems>
    ) : this()
    {
        this.userId=userId
        this.userName=name
        this.address=address
        this.totalPrice= total.toString()
        this.phoneNumber=phone
        this.orderAccepted=b
        this.paymentReceived=b1
        this.itemPushKey=itemPushKey
        this.currentTime=time
        this.itemList=listItemCount
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }


}