package com.stimuluz.photobooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stimuluz.photobooking.model.Photographer
import kotlinx.android.synthetic.main.activity_photographer_details.*

class PhotographerDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photographer_details)

        val photographer: Photographer = intent.extras?.get("PHOTOGRAPHER") as Photographer
        materialButton_portfolio.setOnClickListener {
            val intent = Intent(this, PortfolioActivity::class.java)
            intent.putExtra("PORTFOLIO", photographer.portfolio)
            startActivity(intent)
        }
    }
}
