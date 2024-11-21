package com.aimmore.triplogger_ca4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var addFAB: FloatingActionButton
    private lateinit var favFAB: FloatingActionButton
    private lateinit var searchView: SearchView
    private lateinit var blogListView: ListView
    private lateinit var adapter: BlogAdapter
    private lateinit var logout: ImageButton
    private lateinit var auth: FirebaseAuth

    private val blogList = mutableListOf<BlogModel>()
    private val filteredList = mutableListOf<BlogModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("TripLoggerPrefs", Context.MODE_PRIVATE)
        val userUid = sharedPref.getString("UID", null)
        if (userUid == null) {
            startActivity(Intent(this, LoginPage::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        addFAB = findViewById(R.id.addFabBtn)
        favFAB = findViewById(R.id.favFabBtn)
        searchView = findViewById(R.id.searchView)
        blogListView = findViewById(R.id.blogsList)
        logout = findViewById(R.id.logout)

        val db = TripLoggerDB(this)
        blogList.addAll(db.getAllBlogs())
        filteredList.addAll(blogList)

        adapter = BlogAdapter(this, filteredList)
        blogListView.adapter = adapter

        // Logout functionality
        logout.setOnClickListener {
            val editor = sharedPref.edit()
            editor.clear() // Clear all stored data, including UID
            editor.apply()

            auth.signOut()

            val intent = Intent(this, LoginPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        addFAB.setOnClickListener {
            val intent = Intent(this, AddBlog::class.java)
            startActivity(intent)
            finish()
        }

        favFAB.setOnClickListener {
            val intent = Intent(this, FavBlog::class.java)
            startActivity(intent)
            finish()
        }

        // Search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList.clear()
                if (newText.isNullOrEmpty()) {
                    filteredList.addAll(blogList)
                } else {
                    val searchText = newText.lowercase()
                    blogList.forEach { blog ->
                        if (blog.name.lowercase().contains(searchText)) {
                            filteredList.add(blog)
                        }
                    }
                }
                adapter.updateList(filteredList)
                return true
            }
        })
    }
}
