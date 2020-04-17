package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private var array_list = ArrayList<todo_item>()
    private var Adapter = Item_Adapter(array_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycleview= findViewById(R.id.todo_list) as RecyclerView
        resetData(recycleview,savedInstanceState)
    }

    fun resetData(recycleview:RecyclerView,savedInstanceState: Bundle?) {
        if (savedInstanceState !=null)
        {
            recycleview.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            (recycleview.layoutManager as LinearLayoutManager).onRestoreInstanceState(savedInstanceState.getParcelable("layout"))
            array_list= savedInstanceState.getParcelableArrayList<todo_item>("items") as ArrayList<todo_item>
            Adapter=Item_Adapter(array_list)
            recycleview.adapter=Adapter
            Adapter.notifyDataSetChanged()
        }
        else
        {
            recycleview.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            recycleview.adapter=Adapter
        }
    }

    fun type(view: View) {
        val input: EditText = findViewById(R.id.editText) as EditText
        if (input.text.isEmpty()){
            val text = "you can't create an empty TODO item!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }
        else
        {
            array_list.add(todo_item(input.text.toString(),false))
            Adapter.notifyDataSetChanged()
            input.text.clear()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val recycleview= findViewById(R.id.todo_list) as RecyclerView
        val mListState= recycleview.getLayoutManager()?.onSaveInstanceState();
        outState.putParcelable("layout",mListState)
        outState.putParcelableArrayList("items",array_list)
    }
}
