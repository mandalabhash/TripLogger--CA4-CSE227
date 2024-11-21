package com.aimmore.triplogger_ca4

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class FavBlogAdapter(
    private val context: Context,
    private var favoriteBlogList: List<BlogModel>,
    private val itemClickListener: (Long) -> Unit // Lambda function to handle item click
) : BaseAdapter() {

    override fun getCount(): Int = favoriteBlogList.size

    override fun getItem(position: Int): Any = favoriteBlogList[position]

    override fun getItemId(position: Int): Long = favoriteBlogList[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.blog_card_view, parent, false)

        val blog = favoriteBlogList[position]

        val blogName: TextView = view.findViewById(R.id.blogName)
        val blogDescription: TextView = view.findViewById(R.id.blogDescription)
        val blogImage: ImageView = view.findViewById(R.id.blogImage)

        blogName.text = blog.name
        blogDescription.text = blog.location

        if (blog.image != null) {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(blog.image, 0, blog.image.size)
            blogImage.setImageBitmap(bitmap)
        } else {
            blogImage.setImageResource(R.drawable.sample)
        }

        view.setOnClickListener {
            itemClickListener(blog.id)
        }

        return view
    }

    fun updateList(newList: List<BlogModel>) {
        favoriteBlogList = newList
        notifyDataSetChanged()
    }
}
