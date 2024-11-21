package com.aimmore.triplogger_ca4

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddBlog : AppCompatActivity() {

    private lateinit var blogTitle: EditText
    private lateinit var blogDate: EditText
    private lateinit var blogTime: EditText
    private lateinit var blogContent: EditText
    private lateinit var locationEditText: EditText // Add this line
    private lateinit var saveBtn: Button
    private lateinit var backBtn: ImageButton
    private lateinit var pickImage: ImageButton // Button to pick image
    private var imageUri: Uri? = null // URI to store selected image

    // Register the image picker activity result launcher
    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            imageUri = uri
            pickImage.setImageURI(uri)
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Image not available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blog)

        // Initialize views
        blogTitle = findViewById(R.id.titleEditText)
        blogContent = findViewById(R.id.contentEditText)
        locationEditText = findViewById(R.id.locationEditText) // Initialize the location EditText
        saveBtn = findViewById(R.id.addBlogBtn)
        backBtn = findViewById(R.id.backBtn)
        pickImage = findViewById(R.id.imageButton)

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        pickImage.setOnClickListener {
            getImage.launch("image/*")
        }

        saveBtn.setOnClickListener {
            val title = blogTitle.text.toString().trim()
            val content = blogContent.text.toString().trim()
            val location = locationEditText.text.toString().trim() // Fetch location input

            if (title.isEmpty() || content.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val blogImageByteArray = imageUri?.let {
                val inputStream = contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                bitmap?.let { bmp ->
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    byteArrayOutputStream.toByteArray()
                }
            }

            val blog = BlogModel(
                0,
                title,
                location,
                content,
                false,
                blogImageByteArray
            )

            val db = TripLoggerDB(this)
            val id = db.insertBlog(blog)

            if (id > 0) {
                Toast.makeText(this, "Blog added successfully!", Toast.LENGTH_SHORT).show()
                // Navigate back to main activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Failed to add blog. Try again.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
