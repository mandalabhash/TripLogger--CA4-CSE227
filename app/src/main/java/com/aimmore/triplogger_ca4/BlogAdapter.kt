package com.aimmore.triplogger_ca4

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class BlogAdapter(
    private val context: Context,
    private var blogList: MutableList<BlogModel>
) : BaseAdapter() {

    override fun getCount(): Int = blogList.size

    override fun getItem(position: Int): Any = blogList[position]

    override fun getItemId(position: Int): Long = blogList[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.blog_card_view, parent, false)

        val blog = blogList[position]

        val blogName: TextView = view.findViewById(R.id.blogName)
        val blogDescription: TextView = view.findViewById(R.id.blogDescription)
        val blogImage: ImageView = view.findViewById(R.id.blogImage)

        blogName.text = blog.name
        blogDescription.text = blog.blog

        if (blog.image != null) {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(blog.image, 0, blog.image.size)
            blogImage.setImageBitmap(bitmap)
        } else {
            blogImage.setImageResource(R.drawable.sample)
        }

        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        view.setOnClickListener {
            val intent = Intent(context, BlogView::class.java).apply {
                putExtra("BLOG_ID", blog.id)
            }
            context.startActivity(intent)
        }

        return view
    }

    fun updateList(newList: MutableList<BlogModel>) {
        blogList = newList
        notifyDataSetChanged()
    }
}
