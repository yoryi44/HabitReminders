package com.example.home_data.local.typeconverter


import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

@ProvidedTypeConverter
class HomeTypeConverter @Inject constructor(
    private val gson: Gson
){

    @TypeConverter
    fun fromFrequency(days: List<Int>): String {
        return gson.toJson(days) ?: ""
    }

    @TypeConverter
    fun toFrequency(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    @TypeConverter
    fun fromcompletedDates(days: List<Long>): String {
        return gson.toJson(days) ?: ""
    }

    @TypeConverter
    fun tocompletedDates(value: String): List<Long> {
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

}