package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {
    var array_list = ArrayList<todo_item>()
    var prefs: SharedPreferences? = null
    var Adapter = Item_Adapter(array_list,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recycleview= findViewById(R.id.todo_list) as RecyclerView
        resetData(recycleview,savedInstanceState)
        Log.d("TODO LIST SIZE",array_list.size.toString())
    }

    fun resetData(recycleview:RecyclerView,savedInstanceState: Bundle?) {
        prefs = this.getPreferences(Context.MODE_PRIVATE)
        if (savedInstanceState !=null)
        {
            recycleview.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            (recycleview.layoutManager as LinearLayoutManager).onRestoreInstanceState(savedInstanceState.getParcelable("layout"))
            array_list= savedInstanceState.getParcelableArrayList<todo_item>("items") as ArrayList<todo_item>
            Adapter=Item_Adapter(array_list,prefs)
            recycleview.adapter=Adapter
            Adapter.notifyDataSetChanged()
        }
        else
        {
            recycleview.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            this.array_list=this.getArrayList("todoItmes")
            Adapter=Item_Adapter(array_list,prefs)
            recycleview.adapter=Adapter
            this.Adapter.notifyDataSetChanged()
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
            this.saveArrayList(this.array_list,"todoItmes")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val recycleview= findViewById(R.id.todo_list) as RecyclerView
        val mListState= recycleview.getLayoutManager()?.onSaveInstanceState();
        outState.putParcelable("layout",mListState)
        outState.putParcelableArrayList("items",array_list)
    }

    fun saveArrayList(list: ArrayList<todo_item>?, key: String?) {
        //val prefs = this.getPreferences(Context.MODE_PRIVATE)
        val editor = prefs?.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        if (editor != null) {
            editor.putString(key, json)
        }
        if (editor != null) {
            editor.apply()
        } // This line is IMPORTANT !!!
    }

    fun getArrayList(key: String?): ArrayList<todo_item> {
        //val prefs = this.getPreferences(Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs?.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<todo_item?>?>() {}.type
        return gson.fromJson(json, type)
    }
}
