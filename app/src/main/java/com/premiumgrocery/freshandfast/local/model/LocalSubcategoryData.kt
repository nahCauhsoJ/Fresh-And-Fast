package com.premiumgrocery.freshandfast.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.premiumgrocery.freshandfast.Const

@Entity(Const.dbTableSubcategory)
data class LocalSubcategoryData(
    @ColumnInfo("catId")
    val catId: Int,
    @PrimaryKey
    @ColumnInfo("_id")
    val id: String,
    @ColumnInfo("position")
    val position: Int,
    @ColumnInfo("status")
    val status: Boolean,
    @ColumnInfo("subDescription")
    val subDescription: String,
    @ColumnInfo("subId")
    val subId: Int,
    @ColumnInfo("subImage")
    val subImage: String,
    @ColumnInfo("subName")
    val subName: String,
    @ColumnInfo("__v")
    val v: Int
)