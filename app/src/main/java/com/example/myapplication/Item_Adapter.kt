package com.example.myapplication

import android.app.AlertDialog
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
    val items_lsit:ArrayList<todo_item>,
    val prefs: SharedPreferences?
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
            if (!holder.isdone.isChecked) {
                item.isdone=true
                holder.isdone.setChecked(true)
                this.saveArrayList(this.items_lsit,"todoItmes")
                val text = "TODO "+holder.disc.text+" is now DONE. BOOM!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(holder.itemView.context, text, duration)
                toast.show()
            }
        }

        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)

            // Set the alert dialog title
            builder.setTitle("Remove Item")

            // Display a message on alert dialog
            builder.setMessage("Are You Sure You Want To Delete This Item?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("YES"){dialog, which ->
                this.items_lsit.remove(item)
                this.notifyDataSetChanged()
                this.saveArrayList(this.items_lsit,"todoItmes")
                Toast.makeText(holder.itemView.context,"the Item "+holder.disc.text+"Was Successfully Removed",Toast.LENGTH_SHORT).show() }

            // Display a neutral button on alert dialog
            builder.setNeutralButton("Cancel"){_,_ ->
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
            return@setOnLongClickListener true
            }
        }

    fun saveArrayList(list: ArrayList<todo_item>?, key: String?) {
        val editor = this.prefs?.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        if (editor != null) {
            editor.putString(key, json)
        }
        if (editor != null) {
            editor.apply()
        }
    }
    }

