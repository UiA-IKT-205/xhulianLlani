package com.example.piano3

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.piano3.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG:String = "Piano:MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var piano:Piano

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        signInAnonymously()

        piano = supportFragmentManager.findFragmentById(binding.piano.id) as Piano

        piano.onSave = {
            this.upload(it)
        }
    }

    private fun upload(file: Uri){

        val ref = FirebaseStorage.getInstance().reference.child("melodies/${file.lastPathSegment}")
        var uploadTask = ref.putFile(file)

        uploadTask.addOnSuccessListener {
            Log.d(TAG,"Saved file to fb ${it.toString()}")
        }.addOnFailureListener{
            Log.e(TAG,"Error saving file to fb", it)
        }
    }

    private fun signInAnonymously(){
        auth.signInAnonymously().addOnSuccessListener {
            println("Messages")
            Log.d(TAG, "Login success ${it.user.toString()}")
        }.addOnFailureListener{
            Log.e(TAG, "Login failed", it)
        }
    }

}