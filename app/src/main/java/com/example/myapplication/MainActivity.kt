package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun type(view: View) {
        val txt: EditText = findViewById(R.id.editText) as EditText
        val string =findViewById(R.id.textView) as TextView
        string.setText(txt.text);
        txt.text.clear()
        }
}
