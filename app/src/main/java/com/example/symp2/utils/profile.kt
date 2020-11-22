package com.example.symp2.utils

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.symp2.MainActivity
import com.example.symp2.R


class profile : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_profile_new, container, false)
        var name = view.findViewById<TextView>(R.id.textView6)
        var username = view.findViewById<TextView>(R.id.textView8)
        var userSession = UserSession(context)
        name.text = userSession.userDetails.get("name")
        username.text = userSession.userDetails.get("username")
        var signOut = view.findViewById<Button>(R.id.signOut);
        signOut.setOnClickListener(View.OnClickListener {
            userSession.logoutUser()
            userSession.turnZero()
            var intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        })


        return view
    }


}