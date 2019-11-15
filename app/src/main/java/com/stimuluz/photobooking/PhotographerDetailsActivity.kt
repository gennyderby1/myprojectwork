package com.stimuluz.photobooking

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.stimuluz.photobooking.model.Photographer
import kotlinx.android.synthetic.main.activity_photographer_details.*


class PhotographerDetailsActivity : AppCompatActivity() {

    private lateinit var photographer: Photographer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photographer_details)

        photographer = intent.extras?.get("PHOTOGRAPHER") as Photographer

        Picasso.get().load(photographer.picture).into(circleImageView_detailsPic)
        textView_detailsName.text = photographer.name
        textView_detailsLocation.text = photographer.location
        textView_detailsAbout.text = photographer.about
        textView_detailsFee.text = getString(R.string.photographer_fee, photographer.fee.toString())
        materialButton_detailsPortfolio.setOnClickListener {
            val intent = Intent(this, PortfolioActivity::class.java)
            intent.putExtra("PORTFOLIO", photographer.portfolio)
            startActivity(intent)

        }



        materialButton_detailsBook.setOnClickListener {
            callPhotographer()
        }

    }

    fun callPhotographer() {
        // Check if the Camera permission has been granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already available, start camera preview
//            layout.showSnackbar(R.string.camera_permission_available, Snackbar.LENGTH_SHORT)
            startCall()
        } else {
            // Permission is missing and must be requested.
            requestPhoneCallPermission()
        }

    }

    private fun requestPhoneCallPermission() {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CALL_PHONE), 1
        )
    }

    @SuppressLint("MissingPermission")
    private fun startCall() {
        val callIntent = Intent(Intent.ACTION_CALL);
        callIntent.data = Uri.parse("tel:${photographer.phone}")
        startActivity(callIntent)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startCall()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
