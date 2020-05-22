package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson


class Item_Adapter(
    var items_lsit:ArrayList<todo_item>
): RecyclerView.Adapter<Item_Adapter.ViewHolder>() {
    lateinit var isdone:CheckBox
    lateinit var disc:TextView

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val isdone=itemView.findViewById(R.id.is_done) as CheckBox
        val disc=itemView.findViewById(R.id.discription) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items_lsit.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item:todo_item=items_lsit[position]

        holder.isdone.setChecked(item.isdone!!)
        holder.disc.text= item.discription

        holder.itemView.setOnClickListener {
            val intent= Intent(it.context,to_do_edit::class.java)
            intent.putExtra("item",item)
            it.context.startActivity(intent)

        }
        }
    }

