package com.sumeyyesahin.dailynotion.View.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sumeyyesahin.dailynotion.R
import com.sumeyyesahin.dailynotion.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //toolbarda geri gitmek için
        supportActionBar!!.title = "" //toolbarda başlık

        auth= Firebase.auth


    }
    fun signUpClicked (view: View) {

        val email =
            binding.emailText.text.toString()  // e posta değişkeni oluşturuldu (emailText'ten alınır)
        val password =
            binding.passwordText.text.toString() // şifre değişkeni oluşturuldu (passwordText'ten alınır)

        if (email.equals("") || password.equals("")) { // eğer e posta veya şifre boş ise

            Toast.makeText(this, "Email and password cannot be empty!", Toast.LENGTH_LONG)
                .show() // hata mesajı gösterilir
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
                .addOnSuccessListener {
                    // send email verification
                    val user = auth.currentUser
                    user!!.sendEmailVerification()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Verification email sent to $email",
                                    Toast.LENGTH_LONG
                                ).show()
                                view?.let {
                                    Navigation.findNavController(it)
                                        .navigate(R.id.action_verificationFragment_to_mainPageFragment)

                                }
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    it.exception!!.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }}




                }
        }
    }}