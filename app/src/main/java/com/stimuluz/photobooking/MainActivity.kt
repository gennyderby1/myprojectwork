package com.stimuluz.photobooking

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.stimuluz.photobooking.adapter.PhotographerAdapter
import com.stimuluz.photobooking.model.Photographer
import com.stimuluz.photobooking.utilities.Utils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: PhotographerAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var photographerList: ArrayList<Photographer> = ArrayList()

    private lateinit var database: FirebaseFirestore
    lateinit var loaderDialog: Dialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        loaderDialog = Utils.createLoaderDialog(this)



    }

    private fun setUpRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView_photographers.layoutManager = linearLayoutManager
//        val dividerItemDecoration = DividerItemDecoration(
//            recyclerView_photographers.context,
//            linearLayoutManager.orientation
//        )
//        recyclerView_photographers.addItemDecoration(dividerItemDecoration)
        mAdapter = PhotographerAdapter(photographerList)
        recyclerView_photographers.adapter = mAdapter

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser == null) {
            val loginIntent = Intent(this, WelcomeActivity::class.java)
            this@MainActivity.startActivity(loginIntent)
            this@MainActivity.finish()
            return
        }
        database = FirebaseFirestore.getInstance()

        database.collection("photographers ")
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Successfully received data. List in querySnapshot.documents
                if (photographerList.isNotEmpty()) {
                    photographerList.clear()
                }
                for (document in querySnapshot.documents) {
                    photographerList.add(
                        Photographer(
                            document.get("name") as String,
                            document.get("picture") as String,
                            document.get("location") as String,
                            document.get("phone") as String,
                            document.get("about") as String,
                            document.get("pictures") as ArrayList<String>?,
                            document.get("fee") as Long
                        )
                    )


                }

                setUpRecyclerView()
            }
            .addOnFailureListener { exception ->
                // An error occurred while getting data
                Log.d("ZORBAM", "Error getting documents: ", exception)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                mAuth.signOut()
                updateUI(mAuth.currentUser)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
