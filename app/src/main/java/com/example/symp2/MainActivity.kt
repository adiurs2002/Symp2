package com.example.symp2

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.symp2.utils.UserSession
import java.security.AccessController.getContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class MainActivity : AppCompatActivity(),TimePickerDialog.OnTimeSetListener  {
    var times: ArrayList<Calendar>? = null
    var bundle = Bundle();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val firstFragment = HomeFragment()


        times = ArrayList()

       setCurrentFragment(firstFragment)
        val userSession = UserSession(this);
        if (!userSession.isLoggedIn){
            val intent = Intent(this@MainActivity,UserSessionActivity::class.java);
            startActivity(intent);
        }


    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val c = Calendar.getInstance()
        c[Calendar.HOUR_OF_DAY] = p1
        c[Calendar.MINUTE] = p2
        c[Calendar.SECOND] = 0
        Log.d("TAG", "onTimeSet: "+c.timeInMillis+"   "+p1+"  "+p2)
        times?.add(c);
        var num = times?.size
        if (num != null) {
            bundle.putInt("size",num.toInt())
            bundle.putLong(num.toString(),c.timeInMillis)
        }
        val viewGroup: ViewGroup = findViewById(android.R.id.content)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_medicine, viewGroup, false)
        var textView = dialogView.findViewById<TextView>(R.id.timeText);
        var str = textView.text.toString()
        if (p1>=12)
        str += c.timeInMillis.toString()+"PM"
        else
            str += c.timeInMillis.toString()+"AM"
        textView.text = str
        Log.d("TAG", "onTimeSet: "+times?.size)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

}