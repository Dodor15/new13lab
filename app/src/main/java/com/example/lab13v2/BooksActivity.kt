package com.example.lab13v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.lab13v2.data.BookDataBase
import com.example.lab13v2.data.DATABASE_NAME
import com.example.lab13v2.data.models.BookType
import java.util.concurrent.Executors

class BooksActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var bookList: MutableList<BookType>
    private lateinit var SelectBook:MutableList<String>
    var indexChanged=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        var db: BookDataBase = Room.databaseBuilder(this, BookDataBase::class.java, DATABASE_NAME).build()
        val bookDAO = db.bookDAO()
        val executor = Executors.newSingleThreadExecutor()
        val books = bookDAO.getAllBook()


        if(indexChanged==-1){

        }
        val adapter=BookRVAdapter(this, bookList)

        books.observe(this){
            bookList.addAll( it )
            SelectBook.clear()
            it.forEach{
                SelectBook.add(it.stule)
            }

            val adapter = BookRVAdapter(this, bookList)
            val rvListener = object : BookRVAdapter.ItemClickListener{
                override fun onItemClick(view: View?, position: Int) {
                    val intent = Intent(this@BooksActivity,MainActivity::class.java)
                    indexChanged= position
                    intent.putExtra("num", position)
                    startActivity(intent)
                    Toast.makeText(this@BooksActivity, "position: $position",
                        Toast.LENGTH_SHORT).show()
                }
            }
            adapter.setClickListener(rvListener)
            rv = findViewById(R.id.recyclerView)
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(this)


    }
}