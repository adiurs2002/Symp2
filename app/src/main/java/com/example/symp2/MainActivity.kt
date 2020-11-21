package com.example.symp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.symp2.utils.UserSession
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val firstFragment = HomeFragment()
//        val secondFragment = MedicFragment()
//        val thirdFragment = ProfileFragment()

       // setCurrentFragment(firstFragment)
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
}