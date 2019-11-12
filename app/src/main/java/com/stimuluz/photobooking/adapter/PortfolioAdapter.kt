package com.stimuluz.photobooking.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stimuluz.photobooking.MyPreviewActivity
import com.stimuluz.photobooking.R
import com.stimuluz.photobooking.inflate
import kotlinx.android.synthetic.main.item_portfolio.view.*

class PortfolioAdapter(private val portfolio: ArrayList<String>): RecyclerView.Adapter<PortfolioAdapter.PortfolioHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioHolder {
        val inflatedView = parent.inflate(R.layout.item_portfolio, false)
        return PortfolioHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return portfolio.size
    }

    override fun onBindViewHolder(holder: PortfolioHolder, position: Int) {
        val picture = portfolio[position]
        holder.bindPicture(picture)
    }


    class PortfolioHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var imageUrl: String? = null

        override fun onClick(v: View?) {
            val context = itemView.context
            val showPhotoIntent = Intent(context, MyPreviewActivity::class.java)
            showPhotoIntent.putExtra("PREVIEW", imageUrl)
            context.startActivity(showPhotoIntent)
        }

        init {
            itemView.setOnClickListener(this)
        }



        fun bindPicture(picture: String){
            Picasso.get().load(picture).fit().into(itemView.imageView_picture)
            imageUrl = picture

        }

    }
}