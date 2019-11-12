package com.stimuluz.photobooking.utilities

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.stimuluz.photobooking.R

class Utils {
    companion object{
        fun createLoaderDialog(context: Context, message: String = "Please wait...") : Dialog {
            val view = LayoutInflater.from(context).inflate(R.layout.loader_dialog_all, null)
            val messageTv: TextView = view.findViewById(R.id.dialog_tv)
            messageTv.text = message

            val builder = android.app.AlertDialog.Builder(context)
            builder.setView(view)
            builder.setCancelable(false)

            return builder.create()
        }

    }
}