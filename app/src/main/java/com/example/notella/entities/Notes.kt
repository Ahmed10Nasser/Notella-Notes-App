package com.example.notella.entities

import androidx.room.*

@Entity(tableName = "Notes")
data class Notes (

    @PrimaryKey(autoGenerate = true) var id: Int,

    @ColumnInfo(name = "title") var title: String,

    @ColumnInfo(name = "sub_title") var sub_title: String,

    @ColumnInfo(name = "text") var text: String,

    @ColumnInfo(name = "date_time") var dateTime: String,

    @ColumnInfo(name = "img_path") var imgPath: String,

    @ColumnInfo(name = "link") var link: String,

    @ColumnInfo(name = "color") var color: String


)
{
override fun toString(): String {
    return "$title : $dateTime"
}
}


