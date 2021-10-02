package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.content.Intent


class MainActivity : AppCompatActivity() {
    lateinit var fistName:EditText ;
    lateinit var lastName : EditText
    lateinit var birthDate :EditText
    lateinit var country : EditText
    lateinit var tvCount : TextView
    lateinit var ivImageViewer : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val submitButton = findViewById<Button>(R.id.submitButton)
        fistName = findViewById(R.id.editTextFirstName)
        lastName = findViewById(R.id.editTextLastName)
        birthDate = findViewById(R.id.editTextBirthDate)
        country = findViewById(R.id.editTextCountry)


        submitButton.setOnClickListener(){
            Log.d(
                "MainActivity",
                "First Name : ${fistName.text} \n " +
                        "Last Name : ${lastName.text} \n " +
                        "Birth Date : ${birthDate.text} \n " +
                        "Country : ${country.text}"
            )
        }

//        counter
        val countButton = findViewById<Button>(R.id.buttonCount)
        val clearButton = findViewById<Button>(R.id.buttonClear)
        tvCount = findViewById(R.id.textViewCount);
        var count = 0
        countButton.setOnClickListener(){
            count++
            tvCount.text="Lets count together : $count"
        }

        clearButton.setOnClickListener(){
            count=0;
            tvCount.text = "Lets count together : 0"
        }
//            Add image to image viewer
        val AddImageButton = findViewById<Button>(R.id.ButtonAddImage)
        ivImageViewer = findViewById(R.id.IvImage)
        AddImageButton.setOnClickListener(){
            ivImageViewer.setImageResource(R.drawable.profile)
        }

//        passing data to second page
        val secondButton = findViewById<Button>(R.id.buttonSecondPage)
        secondButton.setOnClickListener(){
            val person = PersonData(
                fistName.text.toString(),
                lastName.text.toString(),

                //empty check for integer variable passed to second page
                if (birthDate.text.toString()=="") 0 else birthDate.text.toString().toInt(),

                country.text.toString()
            )

            Intent(this,SecondPageActivity::class.java ).also {
               it.putExtra("birth",person) //passing serializable parameter to the second page
                startActivity(it)
            }
//            val myintent = Intent(this, SecondPageActivity::class.java)
//            this.startActivity(myintent)
        }
    }

}