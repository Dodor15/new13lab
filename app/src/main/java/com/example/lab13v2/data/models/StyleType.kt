package com.example.lab13v2.data.models

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lab13v2.data.STYLE_TABLE

@Entity(tableName = STYLE_TABLE)
data class StyleType(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = BaseColumns._ID)
    val id: Int,
    var stule:String
)