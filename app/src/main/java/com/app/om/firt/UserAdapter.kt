package com.app.om.firt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.om.firt.databinding.UserItemBinding

class UserAdapter (private val allUsers : ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var onClick : ((User) -> Unit)? = null

    inner class ViewHolder (val binding:UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = allUsers[position]
        holder.binding.apply {
            name.text = "NAME IS : " + user.name
            age.text = "AGE IS : " + user.age
            gpa.text = "GPA IS : " + user.gpa
            delete.setOnClickListener {
                onClick!!.invoke(user)
            }
        }
    }
    override fun getItemCount(): Int = allUsers.size

}