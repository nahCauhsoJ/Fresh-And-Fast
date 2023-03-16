package com.premiumgrocery.freshandfast.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.premiumgrocery.freshandfast.Const

@Entity(Const.dbTableCategory)
data class LocalCategoryData(
    @ColumnInfo("catDescription")
    val catDescription: String,
    @ColumnInfo("catId")
    val catId: Int,
    @ColumnInfo("catImage")
    val catImage: String,
    @ColumnInfo("catName")
    val catName: String,
    @PrimaryKey
    @ColumnInfo("_id")
    val id: String,
    @ColumnInfo("position")
    val position: Int,
    @ColumnInfo("slug")
    val slug: String,
    @ColumnInfo("status")
    val status: Boolean,
    @ColumnInfo("__v")
    val v: Int
)