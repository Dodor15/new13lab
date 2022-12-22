package com.example.lab13v2

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.room.Room
import com.example.lab13v2.data.BookDAO
import com.example.lab13v2.data.BookDataBase
import com.example.lab13v2.data.DATABASE_NAME
import com.example.lab13v2.data.models.BookType
import com.example.lab13v2.data.models.StyleType
import com.example.lab13v2.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: BookDataBase
    private lateinit var bookDAO: BookDAO
    private var bookType: MutableList<BookType> = mutableListOf()
    private var styleTypes: MutableList<StyleType> = mutableListOf()
    private var styles: MutableList<String> = mutableListOf()
    /*private var db: BookDataBase=Room.databaseBuilder(this, BookDataBase::class.java, DATABASE_NAME).build()
    val bookDAO = db.bookDAO()*/
    private var styleID: Int =0
    private var booksID: Int =0
    var bookId =-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db=Room.databaseBuilder(this, BookDataBase::class.java, DATABASE_NAME).build()
        bookDAO = db.bookDAO()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val styl = bookDAO.getAllStyle()

        var spinnerAdapter: ArrayAdapter<String>

        styl.observe(this){
            styleTypes.addAll( it )
            styles.clear()
            it.forEach{
                styles.add(it.stule)
            }

            spinnerAdapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1, styles)
            binding.spinner.adapter = spinnerAdapter
        }

        bookId = intent.getIntExtra("index", -1)
        styleID = intent.getIntExtra("id", 0)






        if(bookId==-1){
            binding.delbutton.isVisible = false
            binding.goCheck.isVisible = true
            binding.button.setText("Добавить")
        }
        if(bookId!=-1){
            db = Room.databaseBuilder(this, BookDataBase::class.java, DATABASE_NAME).build()
            binding.delbutton.isVisible = true

            DBGet()
            binding.button.setText("Изменить")
            val books = bookDAO.getBook(bookId+1)
            books.observe(this){
                it.forEach{
                    binding.editTextTextPersontAuthors.setText(it.authors)
                    binding.editTextTextPersonIndex.setText(it.index)
                    binding.editTextNumberPageCount.setText(it.pageCount.toString())
                    binding.editTextTextPerson.setText(it.title)
                    binding.spinner.setSelection(it.id)

                }
            }
        }


        binding.button.setOnClickListener {

                if(bookId!=1){
                    val aurhor:String =binding.editTextTextPersontAuthors.text.toString();
                    val tvspinner = binding.spinner.selectedItem.toString()
                    val title:String =binding.editTextTextPerson.text.toString();
                    val index:String = binding.editTextTextPersonIndex.text.toString()
                    val pages:Int = binding.editTextNumberPageCount.text.toString().toInt()
                    val executor = Executors.newSingleThreadExecutor()
                    DBGet()
                    executor.execute {

                        bookDAO.saveBook( BookType(
                                    booksID,
                                    tvspinner,
                                    title.toString(),
                                    aurhor.toString(),
                                    index.toString(),
                                    pages.toInt()
                        ))
                    }
                }
                else if(bookId ==-1) {

                    val executor = Executors.newSingleThreadExecutor()
                    val tvspinner = binding.spinner.selectedItem.toString()
                    DBGet()
                        val aurhor:String =binding.editTextTextPersontAuthors.text.toString();
                        val tvspinner = binding.spinner.selectedItem.toString()
                        val title:String =binding.editTextTextPerson.text.toString();
                        val index:String = binding.editTextTextPersonIndex.text.toString()
                        val pages:Int = binding.editTextNumberPageCount.text.toString().toInt()
                        val executor = Executors.newSingleThreadExecutor()
                        DBGet()
                        executor.execute {

                            bookDAO.addBook( BookType(
                                booksID,
                                tvspinner,
                                title.toString(),
                                aurhor.toString(),
                                index.toString(),
                                pages.toInt()
                            ))
                        }
                        Log.d("QWE", "add book")
                    val books = bookDAO.getAllBook()
                    books.observe(this) { it ->
                        styles.clear()
                        it.forEach {
                            styles.add(it.stule)
                        }
                    }
                }


        }

        binding.delbutton.setOnClickListener {
            val aurhor: String = binding.editTextTextPersontAuthors.text.toString();
            val tvspinner = binding.spinner.selectedItem.toString()
            val title: String = binding.editTextTextPerson.text.toString();
            val index: String = binding.editTextTextPersonIndex.text.toString()
            val pages: Int = binding.editTextNumberPageCount.text.toString().toInt()

            val executor = Executors.newSingleThreadExecutor()

            executor.execute {
                bookDAO.killBook(
                BookType(
                        booksID, tvspinner,
                        binding.editTextTextPerson.text.toString(),
                        binding.editTextTextPersontAuthors.text.toString(),
                        binding.editTextTextPersonIndex.text.toString(),
                        binding.editTextNumberPageCount.text.toString().toInt()
                    ))

            }

            val intent = Intent(this, BooksActivity::class.java)
            startActivity((intent))
        }
        binding.button3.setOnClickListener {
            val intent= Intent(this, AddSpinActivity::class.java)
            startActivity(intent)
        }
        binding.goCheck.setOnClickListener {
            val intent=Intent(this, BooksActivity::class.java)
            startActivity(intent)
        }

    }
    fun DBGet(){
        var db = Room.databaseBuilder(this, BookDataBase::class.java, DATABASE_NAME).build()
        val bookDAO = db.bookDAO()
        styleTypes.clear()
        bookType.clear()
        val books = bookDAO.getAllBook()
        val styles = bookDAO.getAllStyle()
        styles.observe(this){
          styleTypes.addAll(it)
        }
        books.observe(this){
            bookType.addAll(it)
        }


    }

}