package com.example.foodorderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.foodorderapp.databinding.ActivityDetailBinding
import com.example.foodorderapp.model.CartItems
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private lateinit var foodName:String
    private lateinit var foodPrice:String
    private lateinit var foodDescription:String
    private lateinit var foodImage:String
    private lateinit var foodIngrediant:String
    private lateinit var quantity:String

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth=Firebase.auth


        val adapter = intent.getStringExtra("adapter")

        if (adapter.equals("MenuAdapter"))
        {
            foodName= intent.getStringExtra("foodname").toString()
            foodPrice= intent.getStringExtra("foodprice").toString()
            foodDescription= intent.getStringExtra("description").toString()
            foodImage= intent.getStringExtra("imageuri").toString()
            foodIngrediant= intent.getStringExtra("ingredient").toString()

            setValues(foodName,foodPrice,foodDescription,foodImage,foodIngrediant)


        }
        else if (adapter.equals("PopulerAdapter"))
        {

            foodName= intent.getStringExtra("foodname").toString()
            foodPrice= intent.getStringExtra("foodprice").toString()
            foodDescription= intent.getStringExtra("fooddescription").toString()
            foodImage= intent.getStringExtra("foodimage").toString()
            foodIngrediant= intent.getStringExtra("foodingredient").toString()

            setValues(foodName,foodPrice,foodDescription,foodImage,foodIngrediant)

        }


        binding.backbutton.setOnClickListener {
            finish()
        }

        binding.addtocartBtn.setOnClickListener {
           if (binding.quantityCount.text.equals("0"))
           {
               Toast.makeText(this,"Add some Quantity",Toast.LENGTH_SHORT).show()

           }
            else
           {
             addItemToCart()
               var intent=Intent(this,MainActivity::class.java)
               startActivity(intent)
               finish()
           }
        }
        var count:Int=1
        binding.plusBtn.setOnClickListener {
            count++
            binding.quantityCount.text=count.toString()
            binding.totalPrice.text=(count*foodPrice.toDouble()).toString()
            binding.minusBtn.isClickable=true


        }
        binding.minusBtn.setOnClickListener {
            if (binding.quantityCount.text.equals("0"))
            {
                binding.minusBtn.isClickable=false
                return@setOnClickListener
            }
            count--
            binding.quantityCount.text=count.toString()
            binding.totalPrice.text=(count*foodPrice.toDouble()).toString()
        }

    }

    private fun setValues(foodName: String?, foodPrice: String?, foodDescription: String?, foodImage: String?, foodIngrediant: String?) {
         binding.foodname.text=foodName
        binding.description.text=foodDescription
        binding.gradient.text=foodIngrediant
        binding.quantityCount.text="1"
        binding.totalPrice.text=foodPrice
        Glide.with(this).load(foodImage).into(binding.foodimage)

    }

    private fun addItemToCart() {
    var database:DatabaseReference=FirebaseDatabase.getInstance().reference
     val userId:String=auth.currentUser?.uid?:""
        var q=binding.quantityCount.text.toString()
        var quantity=q.toInt()
        var price=binding.totalPrice.text.toString()


        val cartItems:CartItems=CartItems(foodName.toString(),price.toString(),foodDescription.toString(),foodImage.toString(),quantity)
        database.child("user").child(userId).child("CartItems").push().setValue(cartItems).addOnSuccessListener {
            Toast.makeText(this,"Item added to cart ",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            Log.d("Eroor",it.toString())
        }

    }
}