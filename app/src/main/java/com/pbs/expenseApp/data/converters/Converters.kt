package com.pbs.expenseApp.data.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@ProvidedTypeConverter
class DateConverters {

    private val zoneId: ZoneId = ZoneId.systemDefault()
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { Instant.ofEpochSecond(it).atZone(zoneId).toLocalDate() }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.atStartOfDay(zoneId)?.toEpochSecond()
    }
}
