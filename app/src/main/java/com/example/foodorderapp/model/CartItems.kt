package com.example.foodorderapp.model

import java.io.Serializable

class CartItems(
    var foodName:String?=null,
    var foodPrice:String?=null,
    var foodDescription:String?=null,
    var foodImage:String?=null,
    var foodQuantity:Int ?=null
):Serializable {
}