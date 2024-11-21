package com.aimmore.triplogger_ca4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.Date
import java.sql.Time

class TripLoggerDB(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "TripLogger.db"
        private const val DATABASE_VERSION = 1

        // Table and Column Names
        private const val TABLE_NAME = "Blogs"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_LOCATION = "location"
        private const val COLUMN_BLOG = "blog"
        private const val COLUMN_IS_FAVORITE = "isFavorite"
        private const val COLUMN_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_LOCATION TEXT NOT NULL,
                $COLUMN_BLOG TEXT NOT NULL,
                $COLUMN_IS_FAVORITE INTEGER DEFAULT 0,
                $COLUMN_IMAGE BLOB
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertBlog(blog: BlogModel): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, blog.name)
            put(COLUMN_LOCATION, blog.location)
            put(COLUMN_BLOG, blog.blog)
            put(COLUMN_IS_FAVORITE, blog.isFavorite)
            put(COLUMN_IMAGE, blog.image)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun deleteBlog(id: Long) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getAllBlogs(): List<BlogModel> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val blogs = mutableListOf<BlogModel>()
        while (cursor.moveToNext()) {
            val blog = BlogModel(
                id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                blog = cursor.getString(cursor.getColumnIndex(COLUMN_BLOG)),
                isFavorite = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAVORITE)) == 1,
                image = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
            )
            blogs.add(blog)
        }
        cursor.close()
        db.close()
        return blogs
    }

    fun getBlogById(id: Long): BlogModel? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        val blog = if (cursor.moveToNext()) {
            BlogModel(
                id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                blog = cursor.getString(cursor.getColumnIndex(COLUMN_BLOG)),
                isFavorite = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAVORITE)) == 1,
                image = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
            )
        } else {
            null
        }
        cursor.close()
        db.close()
        return blog
    }

    fun getFavoriteBlogs(): List<BlogModel> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_IS_FAVORITE = ?",
            arrayOf("1"),
            null,
            null,
            null
        )
        val blogs = mutableListOf<BlogModel>()
        while (cursor.moveToNext()) {
            val blog = BlogModel(
                id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                blog = cursor.getString(cursor.getColumnIndex(COLUMN_BLOG)),
                isFavorite = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAVORITE)) == 1,
                image = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
            )
            blogs.add(blog)
        }
        cursor.close()
        db.close()
        return blogs
    }

    fun updateFavoriteBlog(id: Long, isFav: Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_FAVORITE, if (isFav) 1 else 0)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }
}
