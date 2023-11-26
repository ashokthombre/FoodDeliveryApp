package com.example.foodorderapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.ActivityDetailBinding
import com.example.foodorderapp.databinding.FragmentProfileBinding
import com.example.foodorderapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {
  private lateinit var binding: FragmentProfileBinding
  private val auth=FirebaseAuth.getInstance()
    private val database=FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        binding.saveInformationBtn.isEnabled=false
        setUserData()
        binding.userName.isEnabled=false
        binding.userAddress.isEnabled=false
        binding.userEmail.isEnabled=false
        binding.userPhone.isEnabled=false

        binding.editprofileBtn.setOnClickListener {
            binding.saveInformationBtn.isEnabled=true
            binding.userName.isEnabled=true
            binding.userAddress.isEnabled=true
            binding.userEmail.isEnabled=true
            binding.userPhone.isEnabled=true
        }
        
        binding.saveInformationBtn.setOnClickListener {
            val name = binding.userName.text.toString()
            val email = binding.userEmail.text.toString()
            val address = binding.userAddress.text.toString()
            val phone = binding.userPhone.text.toString()
            updateUserData(name, email, address, phone)

        }
        
        return binding.root
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {

        val userId= auth.currentUser?.uid
        if (userId !=null)
        {
            val userRef=database.getReference("user").child(userId)

            val userData:HashMap<String,String> = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone
            )
            userRef.updateChildren(userData as Map<String, String>).addOnSuccessListener {
                Toast.makeText(requireContext(),"Profile updated Successfully..",Toast.LENGTH_SHORT).show()

            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Something went wrong..",Toast.LENGTH_SHORT).show()
                  Log.d("Error",it.toString())
                }

//            userRef.setValue(userData).addOnSuccessListener {
//                Toast.makeText(requireContext(),"Profile updated Successfully..",Toast.LENGTH_SHORT).show()
//            }
//                .addOnFailureListener {
//                    Toast.makeText(requireContext(),"Something went wrong..",Toast.LENGTH_SHORT).show()
//                    Log.d("Error",it.toString())
//                }
        }
    }

    private fun setUserData() {
      val userId= auth.currentUser?.uid
        if (userId!=null)
        {
            val userRef=database.getReference("user").child(userId)

             userRef.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists())
                    {
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        binding.userName.setText(userProfile!!.name)
                        binding.userAddress.setText(userProfile.address)
                        binding.userEmail.setText(userProfile.email)
                        binding.userPhone.setText(userProfile.phone)

                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }


    }

    companion object {

    }
}