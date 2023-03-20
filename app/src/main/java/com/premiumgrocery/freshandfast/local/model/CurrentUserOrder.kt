package com.premiumgrocery.freshandfast.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.premiumgrocery.freshandfast.Const

@Entity(Const.dbTableCurrentOrder)
data class CurrentUserOrder(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,
    @ColumnInfo("amount")
    val amount: Int
)