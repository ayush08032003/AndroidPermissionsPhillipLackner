package com.ayushwalker.androidpermissions

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRequestPermissions = findViewById<Button>(R.id.btnRequestPermissions)
        Log.d("Checking System", "All is fine..1")
        btnRequestPermissions.setOnClickListener {
            requestPermissions()
        }
        Log.d("Checking System", "All is fine..2")
    }

    private fun hasWriteExternalStoragePermissions() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationForegroundPermissions() =
        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationBackgroundPermissions() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
             false
        }

//    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestPermissions(){ // always define permissions in an array..!
        val permissionsToRequest = mutableListOf<String>()
        if(!hasWriteExternalStoragePermissions()){
            permissionsToRequest.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(!hasLocationForegroundPermissions()){
            permissionsToRequest.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if(!hasLocationBackgroundPermissions() && hasLocationForegroundPermissions()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissionsToRequest.add(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
        }
        Log.d("Checking System", "All is fine..3(permissions)")
        if(permissionsToRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        }
        Log.d("Checking System", "All is fine..4(permissions)")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()){
            for(i in grantResults.indices){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("PermissionsRequest", "${permissions[i]} granted.!")
                }
            }
        }
    }
}