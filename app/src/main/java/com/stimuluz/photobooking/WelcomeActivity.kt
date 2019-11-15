package com.stimuluz.photobooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        materialButton_goLogin.setOnClickListener {
            val intent = Intent(this, MyLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        materialButton_goRegister.setOnClickListener {
            val intent = Intent(this, MyRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }


}
