package com.adamsolomon.hwk2_messages


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MessageActivity : AppCompatActivity() {
    private lateinit var NameText: EditText
    private lateinit var CityText:EditText
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        db= FirebaseFirestore.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_activitiy)
        var showCityButton=findViewById<Button>(R.id.showCityButton)
        var greetingsButton= findViewById<Button>(R.id.greetingsButton)
        var goodByeButton= findViewById<Button>(R.id.GoodBye)
        NameText=findViewById(R.id.NameField)
        CityText=findViewById(R.id.CityEditText)


        greetingsButton.setOnClickListener {
            Toast.makeText(this@MessageActivity,getString(R.string.GreetingsButtonText), Toast.LENGTH_SHORT).show()
        }
        goodByeButton.setOnClickListener {
            val text="@string/GoodbyeButtonLabel"
            Toast.makeText(this@MessageActivity,getString(R.string.GoodbyeButtonText), Toast.LENGTH_SHORT).show()
        }


        showCityButton.setOnClickListener {


            val DocumentKey=NameText.text.toString()
             if (DocumentKey.isEmpty() ==false) {
                 if (DocumentKey == "Jane Doe" || DocumentKey == "Mateo Lopez") {

                     try {

                         db.collection("Hwk2Addresses")
                             .whereEqualTo("name", DocumentKey)
                             .get()
                             .addOnSuccessListener { result ->

                                 var doc = result.documents.get(0)
                                 Log.d("MYDEBUG", "${doc.id} => ${doc.data}")
                                 var CityData = doc.getString("city")
                                 CityText.setText(CityData)


                             }
                             .addOnFailureListener { exception ->
                                 Log.w("MYDEBUG", "Error getting documents.", exception)
                                 Toast.makeText(this@MessageActivity,"Error getting documents.", Toast.LENGTH_SHORT).show()
                             }
                     } catch (e: Exception) {
                         Toast.makeText(this@MessageActivity, e.message, Toast.LENGTH_SHORT).show()
                     }

                 }
                 else if (DocumentKey != "Jane Doe" || DocumentKey != "Mateo Lopez"){
                     Toast.makeText(this@MessageActivity,getString(R.string.seconderrorMessage), Toast.LENGTH_SHORT).show()
                 }
             }





        }
    }

}