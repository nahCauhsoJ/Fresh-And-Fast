package com.premiumgrocery.freshandfast.local

import androidx.room.TypeConverter
import com.premiumgrocery.freshandfast.local.model.CurrentUserOrder

class RoomTypeConverters {
    @TypeConverter
    fun toCurrentUserOrder(data: HashMap<String, Int>) =
        data.map { CurrentUserOrder(it.key, it.value) }

    @TypeConverter
    fun fromCurrentUserOrder(data: List<CurrentUserOrder>) =
        HashMap(data.associate { it.id to it.amount })
}