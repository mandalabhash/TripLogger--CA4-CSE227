package com.aimmore.triplogger_ca4

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class FavBlog : AppCompatActivity() {
    private lateinit var favBlogList: ListView
    private lateinit var favBlogAdapter: FavBlogAdapter
    private lateinit var backBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_blog)

        favBlogList = findViewById(R.id.favBlogLV)
        backBtn = findViewById(R.id.backBtn)

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val db = TripLoggerDB(this)
        val favoriteBlogs = db.getFavoriteBlogs()

        favBlogAdapter = FavBlogAdapter(this, favoriteBlogs) { blogId ->
            // Open the BlogDetails activity on click, passing the selected blog ID
            val intent = Intent(this, BlogView::class.java)
            intent.putExtra("BLOG_ID", blogId)
            startActivity(intent)
        }

        favBlogList.adapter = favBlogAdapter
    }
}
