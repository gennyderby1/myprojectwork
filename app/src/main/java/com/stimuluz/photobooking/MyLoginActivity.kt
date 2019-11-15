package com.stimuluz.photobooking

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.stimuluz.photobooking.utilities.Utils
import kotlinx.android.synthetic.main.activity_my_login.*


class MyLoginActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var loaderDialog: Dialog

    private var email: String = ""
    private var password: String = ""

    override fun onResume() {
        super.onResume()
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_login)

        mAuth = FirebaseAuth.getInstance()
        loaderDialog = Utils.createLoaderDialog(this)

        materialButton_login.setOnClickListener {
            email = textInputEditText_email.text.toString()
            password = textInputEditText_password.text.toString()

            if (email.isEmpty()) {
                textInputEditText_email.error = "Enter a valid email address"
                textInputEditText_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || (password.length < 6)) {
                textInputEditText_password.error = "Enter a valid password"
                textInputEditText_password.requestFocus()
                return@setOnClickListener
            }

            loaderDialog.show()


            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("ZORBAM", "signInWithEmail:success")
                        val user = mAuth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@MyLoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }

                }
        }

        materialButton_goRegister.setOnClickListener {
            val intent = Intent(this@MyLoginActivity, MyRegisterActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

    }

    private fun updateUI(user: FirebaseUser?) {
        loaderDialog.hide()
        if (user != null) {
            val intent = Intent(this@MyLoginActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finishAffinity()

        }
    }

    override fun onStop() {
        loaderDialog.dismiss()
        super.onStop()
    }
}
