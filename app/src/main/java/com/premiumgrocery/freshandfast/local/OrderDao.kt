package com.premiumgrocery.freshandfast.local

import androidx.room.*
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.local.model.CurrentUserOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrentOrders(data: List<CurrentUserOrder>)

    @Query("select * from ${Const.dbTableCurrentOrder}")
    fun getCurrentOrders(): Flow<List<CurrentUserOrder>>

    @Query("delete from ${Const.dbTableCurrentOrder} where id = :id")
    fun deleteCurrentOrder(id: String)

    @Query("DELETE FROM ${Const.dbTableCurrentOrder}")
    fun clearCurrentOrder()
}