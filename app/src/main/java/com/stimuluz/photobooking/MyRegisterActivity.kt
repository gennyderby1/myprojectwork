package com.stimuluz.photobooking

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.stimuluz.photobooking.model.User
import com.stimuluz.photobooking.utilities.Utils
import kotlinx.android.synthetic.main.activity_my_register.*


class MyRegisterActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var loaderDialog: Dialog

    private var email: String = ""
    private var fullName: String = ""
    private var phoneNumber: String = ""
    private var password: String = ""
    private var repeatPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_register)

        mAuth = FirebaseAuth.getInstance()
        loaderDialog = Utils.createLoaderDialog(this)

        materialButton_submit.setOnClickListener {

            email = textInputEditText_registerEmail.text.toString()
            fullName = textInputEditText_registerName.text.toString()
            phoneNumber = textInputEditText_registerPhone.text.toString()
            password = textInputEditText_registerPassword.text.toString()
            repeatPassword = textInputEditText_registerPassword2.text.toString()

            if (email.isEmpty()) {
                textInputEditText_registerEmail.error = "Enter a valid email address"
                textInputEditText_registerEmail.requestFocus()
                return@setOnClickListener
            }

            if (fullName.isEmpty()) {
                textInputEditText_registerName.error = "Enter your name"
                textInputEditText_registerName.requestFocus()
                return@setOnClickListener
            }

            if (phoneNumber.isEmpty() || (phoneNumber.length < 10)) {
                textInputEditText_registerPhone.error = "Enter a valid phone number"
                textInputEditText_registerPhone.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || (password.length < 6)) {
                textInputEditText_registerPassword.error = "Enter a valid password"
                textInputEditText_registerPassword.requestFocus()
                return@setOnClickListener
            }

            if (repeatPassword.isEmpty() || !(repeatPassword.equals(password, false))) {
                textInputEditText_registerPassword2.error = "Passwords do not match"
                textInputEditText_registerPassword2.requestFocus()
                return@setOnClickListener
            }

            loaderDialog.show()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("ZORBAM-1", "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        val newUser = User(user!!.uid, fullName, email, phoneNumber)
                        FirebaseFirestore.getInstance().collection("users").add(newUser)
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("ZORBAM-2", "createUserWithEmail:failure", task.exception)
                        if (task.exception is FirebaseAuthUserCollisionException) {
                            Toast.makeText(
                                this@MyRegisterActivity,
                                "The email address is already in use by another account.",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                            return@addOnCompleteListener
                        }
                        Toast.makeText(
                            this@MyRegisterActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }

                }
        }

        materialButton_goLogin.setOnClickListener {
            val intent = Intent(this@MyRegisterActivity, MyLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    override fun onResume() {
        super.onResume()
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun updateUI(user: FirebaseUser?) {
        loaderDialog.hide()
        val intent = Intent(this@MyRegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStop() {
        super.onStop()
        loaderDialog.dismiss()
    }
}
