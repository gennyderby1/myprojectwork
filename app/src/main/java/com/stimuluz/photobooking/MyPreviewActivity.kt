package com.stimuluz.photobooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_preview.*

class MyPreviewActivity : AppCompatActivity() {
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_preview)

        imageUrl = intent.extras?.get("PREVIEW") as String

        Picasso.get().load(imageUrl).fit().centerCrop().into(imageView_preview)
    }

    override fun onResume() {
        super.onResume()
        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}
