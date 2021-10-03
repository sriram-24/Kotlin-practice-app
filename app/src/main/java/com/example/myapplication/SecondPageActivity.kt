package com.example.myapplication

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class SecondPageActivity : AppCompatActivity() {
    private lateinit var tvDataFromMainActivity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondpage)

//        default toast
        val showToastButton = findViewById<Button>(R.id.buttonShowToast)
        showToastButton.setOnClickListener(){
            Toast.makeText(this,"Toast button triggered",Toast.LENGTH_LONG).show()
        }

//         Custom Toast
        val customToastButton = findViewById<Button>(R.id.buttonShowCustomToast)
        val layoutInflater = LayoutInflater.from(applicationContext)
        val toastView=findViewById<ViewGroup>(R.id.ToastActivity)
        customToastButton.setOnClickListener(){
            Toast(this).apply {
                duration=Toast.LENGTH_LONG
                view = layoutInflater.inflate(R.layout.activity_custom_toast_message,toastView)
                show()
            }
        }

//        receive data from MainActivity
        val dataFromMainActivity = intent.getSerializableExtra("birth") as PersonData
        tvDataFromMainActivity = findViewById(R.id.textViewDataFromMainActivity)
        tvDataFromMainActivity.text = dataFromMainActivity.toString()

//      permisson implementation
        requestPermission()

//        Alert Dialog implementation
        val foodItems = arrayOf("Cheese","olives","tomatoes")
        val addMultiselectDialog =
            AlertDialog.Builder(this)
                .setTitle("Select toppings")
                .setMultiChoiceItems(foodItems, booleanArrayOf(false,false,false)){ _,i,isChecked ->
                    if (isChecked){
                        Toast.makeText(this, "You selected ${foodItems[i]}",Toast.LENGTH_LONG).show()
                    }
                }
                .setPositiveButton("ok"){_,_->
                    Toast.makeText(this,"selection complete",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("cancel"){_,_->
                    Toast.makeText(this,"Selection canceled",Toast.LENGTH_SHORT).show()
                }
                .create()

//        Listener for alert dialog
        val showDialogButton = findViewById<Button>(R.id.ButtonShowDialog);
        showDialogButton.setOnClickListener(){
            addMultiselectDialog.show();
        }
    }

    private fun hasWriteExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    private fun hasCoarseLocationPermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    private  fun hasBackgroundLocationPermission() =
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission() {
        val permissionPendingList = mutableListOf<String>()
        if (!hasWriteExternalStoragePermission()){
            permissionPendingList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
//        if (!hasBackgroundLocationPermission()){
//            permissionPendingList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//        }
        if (!hasCoarseLocationPermission()){
            permissionPendingList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (permissionPendingList.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permissionPendingList.toTypedArray(),100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==100 && grantResults.isNotEmpty()){
            for (index in grantResults.indices){
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED){
                    Log.d("AppPermissions","${permissions[index]} granted")
                }
            }
        }
    }
}