package com.stimuluz.photobooking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.stimuluz.photobooking.adapter.PortfolioAdapter
import com.stimuluz.photobooking.utilities.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_portfolio.*

class PortfolioActivity : AppCompatActivity() {
    private lateinit var mAdapter: PortfolioAdapter
    private var portfolio: ArrayList<String> = ArrayList()
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)
        portfolio = intent.extras?.get("PORTFOLIO") as ArrayList<String>

        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {

        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView_portfolio.layoutManager = gridLayoutManager
        mAdapter = PortfolioAdapter(portfolio)
        recyclerView_portfolio.addItemDecoration(GridSpacingItemDecoration(2, 16, false))
        recyclerView_portfolio.adapter = mAdapter
        recyclerView_portfolio.setHasFixedSize(true)

    }
}
