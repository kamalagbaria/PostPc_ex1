package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class to_do_edit : AppCompatActivity() {
    val firestore:FirebaseFirestore= FirebaseFirestore.getInstance()
    lateinit var id:TextView
    lateinit var content:EditText
    lateinit var created_on:TextView
    lateinit var modified:TextView
    lateinit var is_done:CheckBox
    lateinit var delete_button:Button
    lateinit var apply_button:Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_edit);

        val item:todo_item?=intent.getParcelableExtra("item")

        id=findViewById(R.id.editText2)
        content = findViewById(R.id.editText5) as EditText
        created_on = findViewById(R.id.editText3) as TextView
        modified = findViewById(R.id.editText4) as TextView
        is_done = findViewById(R.id.checkBox) as CheckBox
        delete_button = findViewById(R.id.button2) as Button
        apply_button = findViewById(R.id.button) as Button

        if (item != null) {
           id.setText(item.id.toString())
            content.setText(item.discription.toString())
            created_on.setText(item.created)
            modified.setText(item.last_updat)
            is_done.isChecked= item.isdone!!
       }
        if(is_done.isChecked){
            delete_button.visibility= View.VISIBLE
        }
        else{
            apply_button.visibility= View.VISIBLE
        }
        is_done.setOnClickListener(View.OnClickListener {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted = current.format(formatter)
            val item:todo_item= todo_item(content.text.toString(),is_done.isChecked,created_on.text.toString(),formatted,Integer.parseInt(id.text.toString()))
            firestore.collection("Items").document(this.id.text.toString()).set(item)
            val intent= Intent(it.context,MainActivity::class.java)
            it.context.startActivity(intent)
            finish()
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun apply(view: View) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = current.format(formatter)
        val item:todo_item= todo_item(content.text.toString(),is_done.isChecked,created_on.text.toString(),formatted,Integer.parseInt(id.text.toString()))
        firestore.collection("Items").document(this.id.text.toString()).set(item).addOnSuccessListener {
            val text = "To Do Content Changed successfully"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
    }

    fun delete(view: View) {
        val builder = AlertDialog.Builder(view.context)

        // Set the alert dialog title
        builder.setTitle("Remove Item")

        // Display a message on alert dialog
        builder.setMessage("Are You Sure You Want To Delete This Item?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){dialog, which ->
            firestore.collection("Items").document(this.id.text.toString()).delete()
            Toast.makeText(view.context,"the Item "+this.content.text+"Was Successfully Removed",Toast.LENGTH_SHORT).show()

            val intent= Intent(view.context,MainActivity::class.java)
            view.context.startActivity(intent)
            finish()
        }
        // Display a neutral button on alert dialog
        builder.setNeutralButton("Cancel"){_,_ ->
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }



}
