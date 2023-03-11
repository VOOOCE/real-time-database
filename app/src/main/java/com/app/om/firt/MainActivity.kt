package com.app.om.firt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.om.firt.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var db : FirebaseDatabase
    private lateinit var myRef : DatabaseReference
    private var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.database
        myRef = db.reference
        binding.addData.setOnClickListener {
            binding.apply {
                if (name.text.toString().isNotEmpty() && age.text.toString().isNotEmpty() && gpa.text.toString().isNotEmpty()){
                    val student = hashMapOf("name" to name.text.toString(),"age" to age.text.toString(),"gpa" to gpa.text.toString())
                    addData(student)
                }
            }
        }
        binding.getData.setOnClickListener{
            getData()
        }

    }
    private fun addData(student:HashMap<String,String>){
        myRef.child("students").child("$counter").setValue(student).addOnSuccessListener {
            counter++
        }
    }
    private fun getData(){
        myRef.child("students").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                Toast.makeText(this@MainActivity,value.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}