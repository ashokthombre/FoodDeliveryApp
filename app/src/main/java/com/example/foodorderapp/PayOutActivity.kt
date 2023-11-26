package com.example.foodorderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foodorderapp.databinding.ActivityPayOutBinding
import com.example.foodorderapp.fragments.CongratsBottomSheetFragment
import com.example.foodorderapp.model.CartItems
import com.example.foodorderapp.model.OrderDetails
import com.example.foodorderapp.model.OrderDetailsExtra
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.properties.Delegates

class PayOutActivity : AppCompatActivity() {
    private val binding:ActivityPayOutBinding by lazy {
        ActivityPayOutBinding.inflate(layoutInflater)
    }
    private lateinit var name:String
    private lateinit var address:String
    private lateinit var phone:String
    private var total by Delegates.notNull<Double>()
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var listItemCount: MutableList<CartItems>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference
        listItemCount =intent.getSerializableExtra("list") as MutableList<CartItems>
         total = totalPrice(listItemCount)
         binding.totalAmount.setText("₹$total")
         setData()


        binding.paymyorder.setOnClickListener {

             val n=binding.name.text.toString().trim()
             val a=binding.address.text.toString().trim()
             val p=binding.phone.text.toString().trim()
              if (n.isBlank() || a.isBlank() || p.isBlank())
              {
                  Toast.makeText(this,"Please Fill Details..",Toast.LENGTH_SHORT).show()
                  return@setOnClickListener
              }
            else
              {
                  placeOredr()
              }

        }

    }

    private fun setData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userRef = database.child("user").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                         name = snapshot.child("name").getValue(String::class.java).toString()
                        address= snapshot.child("address").getValue(String::class.java).toString()
                         phone=snapshot.child("phone").getValue(String::class.java).toString()
                        binding.name.setText(name)
                        binding.address.setText(address)
                        binding.phone.setText(phone)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Error ",error.message)
                }
            })

        }
    }

    private fun placeOredr() {
        var lstOforderDetailExtra:MutableList<OrderDetailsExtra> = mutableListOf()
          val userId:String= auth.currentUser!!.uid
          val time:Long=System.currentTimeMillis()
          val itemPushKey=database.child("OrderDetails").push().key

        val orderDetails:OrderDetails= OrderDetails(userId,name,address,total,phone,false,false,itemPushKey,time,listItemCount)
//          for (list in listItemCount)
//          {
//              val orderDetailExtra:OrderDetailsExtra=OrderDetailsExtra(userId,name,address,total.toString(),phone,false,false,itemPushKey,time,list.foodDescription,list.foodImage
//              ,list.foodName,list.foodPrice,list.foodQuantity.toString())
//
//          }

        val orderRefrence=database.child("OrderDetails").child(itemPushKey!!)
        orderRefrence.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsBottomSheetFragment()
            bottomSheetDialog.show(supportFragmentManager, "test")
            removeItemFromCart()
            addOrderToHistory(orderDetails)

        }
            .addOnFailureListener {
                Toast.makeText(this,"Something went wrong.",Toast.LENGTH_SHORT).show()
                Log.d("error", it.message.toString())
            }

    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
          val userId= auth.currentUser!!.uid
        database.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }

    }

    private fun removeItemFromCart() {
        val userId= auth.currentUser!!.uid
       val cartItemsRefrence=database.child("user").child(userId).child("CartItems")
       cartItemsRefrence.removeValue()
    }

    private fun totalPrice(listItemCount: MutableList<CartItems>):Double {
        var sum:Double=0.0
        for (list in listItemCount)
        {
            var p=list.foodPrice.toString()
            var price= p.toDouble()
            sum += price!!
        }
        return sum
    }

}






