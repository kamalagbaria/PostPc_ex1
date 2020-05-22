package com.example.myapplication

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    var array_list = ArrayList<todo_item>()
    var Adapter = Item_Adapter(array_list)
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val LOG_TAG = "UsersInFirebaseManager"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycleview = findViewById(R.id.todo_list) as RecyclerView
        recycleview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycleview.adapter = Adapter
        resetData(recycleview, savedInstanceState)
        Log.d("TODO LIST SIZE", array_list.size.toString())
    }

    fun resetData(recycleview: RecyclerView, savedInstanceState: Bundle?) {
            var addOnFailureListener = firestore.collection("Items")
                .get()
                .addOnCompleteListener { task: Task<QuerySnapshot> ->
                    val result = task.result
                    if (task.isSuccessful && result != null) {
                        this.array_list.clear()
                        var i:Int=0
                        for (document in result) {
                            val documentId = document.id
                            val item = document.toObject(todo_item::class.java)
                            this.array_list.add(item)
                            this.Adapter.notifyItemInserted(i)
                            i++
                        }
                    } else {
                        Log.d(LOG_TAG, "Error getting documents: ", task.exception)
                    }
                }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun type(view: View) {
        val input: EditText = findViewById(R.id.editText) as EditText
        if (input.text.isEmpty()) {
            val text = "you can't create an empty TODO item!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        } else {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted = current.format(formatter)
            val id:Int=getId()
            this.array_list.add(todo_item(input.text.toString(), false, formatted, formatted, id))
            writeNewUser(id, input.text.toString(), formatted, formatted, false)
            this.Adapter.notifyDataSetChanged()
            input.text.clear()
        }
    }

    private fun writeNewUser(
        itmeId: Int,
        content: String,
        created: String?,
        modified: String,
        is_done: Boolean
    ) {
        val item = todo_item(content, is_done, created, modified, itmeId)
        firestore.collection("Items").document(itmeId.toString()).set(item)
    }

    fun getId(): Int {
        var i: Int = 0
        var id: Int = 0
        while (i < array_list.size) {
            if (array_list[i].id == id) {
                id += 1
            }
            i += 1
        }
        return id
    }

}
