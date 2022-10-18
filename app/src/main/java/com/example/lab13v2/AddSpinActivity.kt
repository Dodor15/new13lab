package com.example.lab13v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.lab13v2.data.BookDataBase
import com.example.lab13v2.data.DATABASE_NAME
import com.example.lab13v2.data.models.StyleType
import com.example.lab13v2.databinding.ActivityAddSpinBinding
import com.example.lab13v2.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class AddSpinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSpinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpinBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var db: BookDataBase = Room.databaseBuilder(this, BookDataBase::class.java, DATABASE_NAME).build()
        val bookDAO = db.bookDAO()
        val executor = Executors.newSingleThreadExecutor()
        val books = bookDAO.getAllBook()

        binding.btnAdd.setOnClickListener {
            if(!binding.addSpin.text.isEmpty()){
                executor.execute {
                    bookDAO.addStyle(StyleType(0, binding.addSpin.text.toString()))
                }
            }
        }
    }
}