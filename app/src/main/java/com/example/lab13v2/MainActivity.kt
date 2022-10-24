package com.example.lab13v2

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.room.Room
import com.example.lab13v2.data.BookDataBase
import com.example.lab13v2.data.DATABASE_NAME
import com.example.lab13v2.data.models.BookType
import com.example.lab13v2.data.models.StyleType
import com.example.lab13v2.databinding.ActivityMainBinding
import java.util.Collections.addAll
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var bookType: MutableList<StyleType> = mutableListOf()
    private var styleType: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var db: BookDataBase=Room.databaseBuilder(this, BookDataBase::class.java, DATABASE_NAME).build()
        val bookDAO = db.bookDAO()
        val executor = Executors.newSingleThreadExecutor()
        val books = bookDAO.getAllStyle()

        var spinnerAdapter: ArrayAdapter<String>

        books.observe(this){
            bookType.addAll( it )
            styleType.clear()
            it.forEach{
                styleType.add(it.stule)
            }

            spinnerAdapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1, styleType)
            binding.spinner.adapter = spinnerAdapter
        }




        binding.button.setOnClickListener {
            if(!binding.editTextTextPerson.text.isEmpty()){

                val tvspinner = binding.spinner.selectedItem.toString()


                executor.execute{
                    bookDAO.addBook(BookType(0, tvspinner,
                        binding.editTextTextPerson.text.toString(),
                        binding.editTextTextPersontAuthors.text.toString(),
                        binding.editTextTextPersonIndex.text.toString(),
                        binding.editTextNumberPageCount.text.toString().toInt()
                    ))
                }
                val books = bookDAO.getAllBook()
                books.observe(this){ it ->
                    styleType.clear()
                    it.forEach{
                        styleType.add(it.stule)
                    }
                }

            }
            else{
                Toast.makeText(this, "Данные введены неверно", Toast.LENGTH_LONG).show()
            }
        }
        binding.button3.setOnClickListener {
            intent= Intent(this, AddSpinActivity::class.java)
            startActivity(intent)
        }
        binding.goCheck.setOnClickListener {
            intent=Intent(this, BooksActivity::class.java)
            startActivity(intent)
        }

    }
}