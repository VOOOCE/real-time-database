package com.app.om.firt

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.om.firt.databinding.GetDataBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.progress_dialog.*

class GetData : AppCompatActivity() {
    private lateinit var binding : GetDataBinding
    private lateinit var users: ArrayList<User>
    private lateinit var db : FirebaseFirestore
    private lateinit var userRecycler : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GetDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Firebase.firestore
        users = arrayListOf()
        userRecycler = binding.recycler
        userRecycler.layoutManager = LinearLayoutManager(this)
        userRecycler.setHasFixedSize(true)

        getUsers()

    }
    private fun getUsers(){
        db.collection("Students").get().addOnSuccessListener {
            if (!it.isEmpty){
                for (i in it){
                    users.add(User(i["name"].toString(),i["age"].toString(),i["gpa"].toString(),i.id))
                }
            }
            val adapter = UserAdapter(users)
            userRecycler.adapter = adapter
            adapter.onClick = {user ->
                deleteUser(user)
            }
        }.addOnFailureListener {
            Toast.makeText(this,"SOMETHING WENT WRONG !",Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteUser(user:User) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.progress_dialog)
        dialog.setCancelable(false)
        dialog.window!!.setLayout(1000,500)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.titleDialog.text = "DELETING -> ${user.name}"
        dialog.messageDialog.isVisible = false
        dialog.show()
        db.collection("Students").document(user.id.toString()).delete().addOnSuccessListener {
            users.clear()
            if (users.isEmpty()){
                dialog.dismiss()
                getUsers()
            }
        }.addOnFailureListener {
            dialog.dismiss()
            Toast.makeText(this,"NOT DELETED",Toast.LENGTH_LONG).show()
        }
    }
}