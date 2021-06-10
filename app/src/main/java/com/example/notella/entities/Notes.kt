package com.example.notella.entities

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "Notes")
class Notes:Serializable {

    @PrimaryKey(autoGenerate = true) var id: Int?=null

    @ColumnInfo(name = "title") var title: String?=null

    @ColumnInfo(name = "sub_title") var sub_title: String?=null

    @ColumnInfo(name = "text") var text: String?=null

    @ColumnInfo(name = "date_time") var dateTime: String?=null

    @ColumnInfo(name = "img_path") var imgPath: String?=null

    @ColumnInfo(name = "link") var link: String?=null

    @ColumnInfo(name = "color") var color: String?=null


    override fun toString(): String {
        return "$title : $dateTime"
    }
}


