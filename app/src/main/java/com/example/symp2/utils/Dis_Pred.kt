package com.example.symp2.utils

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.symp2.R
import com.example.symp2.RetrofitClient.APIUtils
import com.example.symp2.models.SymptomsRequest
import com.example.symp2.models.SymptomsResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Dis_Pred : Fragment() {

    lateinit var symp1:EditText
    lateinit var symp2:EditText
    lateinit var symp3:EditText
    lateinit var symp4:EditText
    lateinit var symp5:EditText
    lateinit var diagnosis:Button
    lateinit var dialogView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dis__pred, container, false)
        symp1 = view.findViewById(R.id.editText);
        symp2 = view.findViewById(R.id.editTextSymp2);
        symp3 = view.findViewById(R.id.editTextSymp3);
        symp4 = view.findViewById(R.id.editTextSymp4);
        symp5 = view.findViewById(R.id.editTextSymp5);
        diagnosis = view.findViewById(R.id.predDisease)
        var ob = object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkAll()
            }

        }
        checkAll()
        symp1.addTextChangedListener(ob)
        symp2.addTextChangedListener(ob)
        symp3.addTextChangedListener(ob)
        symp4.addTextChangedListener(ob)
        symp5.addTextChangedListener(ob)
        diagnosis.setOnClickListener(View.OnClickListener {
            val builder =
                AlertDialog.Builder(context)
            val viewGroup =
                view!!.findViewById<ViewGroup>(android.R.id.content)
            dialogView = LayoutInflater.from(view!!.context)
                .inflate(R.layout.prediction_dialog, viewGroup, false)
            builder.setView(dialogView)
            val alertDialog = builder.create()
            alertDialog.show()
            dialogView.findViewById<TextView>(R.id.text).visibility = View.GONE
            dialogView.findViewById<TextView>(R.id.pred).visibility = View.GONE
            dialogView.findViewById<LottieAnimationView>(R.id.lottieStress)
            thread.start()
        })



        return view
    }

    var thread: Thread = object : Thread() {
        override fun run() {
            super.run()
            val apiService = APIUtils.getAPIService();
            val symptomsRequest = SymptomsRequest()
            symptomsRequest.symp1 = symp1.text.toString()
            symptomsRequest.symp2 = symp2.text.toString()
            symptomsRequest.symp3 = symp3.text.toString()
            symptomsRequest.symp4 = symp4.text.toString()
            symptomsRequest.symp5 = symp5.text.toString()
            apiService.getDisease(symptomsRequest).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful){
                        val g = Gson()
                        val symptomsResponse = g.fromJson<SymptomsResponse>(response.body(),SymptomsResponse::class.java)
                        Log.d("TAG", "onResponse: "+symptomsResponse.tree)
                        dialogView.findViewById<TextView>(R.id.text).visibility = View.VISIBLE
                        dialogView.findViewById<TextView>(R.id.pred).visibility = View.VISIBLE
                        val diseases: MutableList<String> =
                            ArrayList()
                        var i =0
                        if (!diseases.contains(symptomsResponse.tree)){
                            diseases.add(symptomsResponse.tree)
                        }
                        if (!diseases.contains(symptomsResponse.knn)){
                            diseases.add(symptomsResponse.knn)
                        }
                        if (!diseases.contains(symptomsResponse.naives)){
                            diseases.add(symptomsResponse.naives)
                        }
                        if (!diseases.contains(symptomsResponse.random)){
                            diseases.add(symptomsResponse.random)
                        }
                        var string:String=""

                        for (str in diseases) {
                            string = string +"\n"+str
                        }
                        dialogView.findViewById<TextView>(R.id.pred).text = string

                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {}
            })
        }
    }

    private fun checkAll(){
        diagnosis.isEnabled = symp1.text!=null && symp2.text!=null && symp3.text!=null && symp4.text!=null && symp5.text!=null


    }


}


