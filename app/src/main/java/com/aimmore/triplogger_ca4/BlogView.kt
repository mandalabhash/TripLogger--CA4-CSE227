package com.aimmore.triplogger_ca4

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.*

class BlogView : AppCompatActivity() {
    private lateinit var blogTitle: TextView
    private lateinit var tripLocation: TextView
    private lateinit var blogContent: TextView
    private lateinit var favButton: ImageButton
    private lateinit var delButton: ImageButton
    private lateinit var blogImageView: ImageView // ImageView to display blog image
    private lateinit var toolbar: MaterialToolbar // Toolbar reference
    private lateinit var scrollView: ScrollView

    private var blogId: Long = -1L
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_view)

        // Initialize Views
        blogTitle = findViewById(R.id.blogTitle)
        tripLocation = findViewById(R.id.tripLocation)
        blogContent = findViewById(R.id.blogContent)
        favButton = findViewById(R.id.favoriteButton)
        delButton = findViewById(R.id.deleteButton)
        blogImageView = findViewById(R.id.imageView)
        toolbar = findViewById(R.id.toolbar)
        scrollView = findViewById(R.id.scrollView)

        // Set up the toolbar's back navigation
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            // Navigate back to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Finish the current activity to remove it from the back stack
        }

        // Get the blog ID from Intent
        blogId = intent.getLongExtra("BLOG_ID", -1L)
        if (blogId == -1L) {
            Toast.makeText(this, "Invalid blog ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Fetch and Display Blog Details
        fetchAndDisplayBlog()

        // Handle Favorite Button Click
        favButton.setOnClickListener {
            isFavorite = !isFavorite
            updateFavoriteStatus(isFavorite)
        }

        // Handle Delete Button Click
        delButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun fetchAndDisplayBlog() {
        val db = TripLoggerDB(this)
        val blog = db.getBlogById(blogId)

        if (blog != null) {
            blogTitle.text = blog.name
            tripLocation.text = blog.location
            blogContent.text = blog.blog

            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

            blog.image?.let { imageByteArray ->
                val bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                blogImageView.setImageBitmap(bitmap) // Display the image in the ImageView
            }

            isFavorite = blog.isFavorite
            updateFavoriteButtonUI()
        } else {
            Toast.makeText(this, "Blog not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateFavoriteStatus(isFav: Boolean) {
        val db = TripLoggerDB(this)
        db.updateFavoriteBlog(blogId, isFav)

        val message = if (isFav) {
            "Added to Favorites"
        } else {
            "Removed from Favorites"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        updateFavoriteButtonUI()
    }

    private fun updateFavoriteButtonUI() {
        val favoriteIcon = if (isFavorite) {
            R.drawable.ic_favorite
        } else {
            R.drawable.ic_favorite_border
        }
        favButton.setImageResource(favoriteIcon)
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Blog")
            .setMessage("Are you sure you want to delete this blog?")
            .setPositiveButton("Yes") { _, _ -> deleteBlog() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteBlog() {
        val db = TripLoggerDB(this)
        db.deleteBlog(blogId)
        Toast.makeText(this, "Blog deleted successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}
