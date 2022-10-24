package com.example.lab13v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.lab13v2.data.BookDAO
import com.example.lab13v2.data.BookDataBase
import com.example.lab13v2.data.DATABASE_NAME
import com.example.lab13v2.data.models.BookType
import com.example.lab13v2.data.models.StyleType
import java.util.concurrent.Executors

class BooksActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private var bookList: MutableList<BookType> = mutableListOf()
    private var selectBook:MutableList<StyleType> = mutableListOf()
    var index=-1


private lateinit var db: BookDataBase
    private lateinit var bookDAO: BookDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        var db = Room.databaseBuilder(this, BookDataBase::class.java, DATABASE_NAME).build()
        bookDAO = db.bookDAO()




        DataBasetoList()
        updateInfo()

    }

    fun DataBasetoList(){
        bookList.clear()
        selectBook.clear()

        val books = bookDAO.getAllBook()
        books.observe(this, androidx.lifecycle.Observer {
            it.forEach {
                bookList.add(BookType(it.id,it.stule, it.title, it.authors, it.index, it.pageCount))
                updateInfo()
            }
        })
    }
    fun updateInfo() {
        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = BookRVAdapter(this, bookList)
        val rvListener = object : BookRVAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val intent = Intent(this@BooksActivity, MainActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("id", bookList[position].id.toString())
                index = position
                startActivity(intent)
            }
        }
        adapter.setOnClickListener(rvListener)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }
}

