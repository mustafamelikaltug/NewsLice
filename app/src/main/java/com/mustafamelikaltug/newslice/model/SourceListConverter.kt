package com.mustafamelikaltug.newslice.model

import androidx.recyclerview.widget.SortedList
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SourceListConverter {
    @TypeConverter
    fun fromString(value : String?) :Source?{
        val listType = object : TypeToken<Source?>() {}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun toString(list : Source) : String ? {
        val gson = Gson()
        return gson.toJson(list)
    }
}