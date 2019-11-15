package com.stimuluz.photobooking.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stimuluz.photobooking.PhotographerDetailsActivity
import com.stimuluz.photobooking.R
import com.stimuluz.photobooking.inflate
import com.stimuluz.photobooking.model.Photographer
import kotlinx.android.synthetic.main.item_photographer.view.*

class PhotographerAdapter(private val photographers: ArrayList<Photographer>) :
    RecyclerView.Adapter<PhotographerAdapter.PhotographerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotographerHolder {
        val inflatedView = parent.inflate(R.layout.item_photographer, false)
        return PhotographerHolder(inflatedView)

    }

    override fun getItemCount(): Int {
        return photographers.size
    }

    override fun onBindViewHolder(holder: PhotographerHolder, position: Int) {
        val photographer = photographers[position]
        holder.bindPhotographer(photographer)

    }

    class PhotographerHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        //2
        private var view: View = itemView
        private var photographer: Photographer? = null

        //3
        init {
            itemView.setOnClickListener(this)
        }

        fun bindPhotographer(photographer: Photographer) {
            this.photographer = photographer
            photographer.picture
            Picasso.get().load(photographer.picture).into(view.circleImageView_photographerPic)
            view.textView_photographerName.text = photographer.name
            view.textView_photographerLocation.text = photographer.location
            view.textView_photographerFee.text =
                itemView.context.getString(R.string.photographer_fee, photographer.fee.toString())
        }


        //4
        override fun onClick(v: View) {
            val context = itemView.context
            val showPhotoIntent = Intent(context, PhotographerDetailsActivity::class.java)
            showPhotoIntent.putExtra(PHOTOGRAPHER_KEY, photographer)
            context.startActivity(showPhotoIntent)
        }

        companion object {
            //5
            private const val PHOTOGRAPHER_KEY = "PHOTOGRAPHER"
        }
    }
}