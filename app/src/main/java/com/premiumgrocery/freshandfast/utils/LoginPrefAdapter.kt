package com.premiumgrocery.freshandfast.utils

import android.content.SharedPreferences
import javax.inject.Inject

class LoginPrefAdapter @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ILoginPrefAdapter {
    override fun getUserId() = sharedPreferences.getString("userId",null)
    override fun getUserEmail() = sharedPreferences.getString("userEmail",null)
    override fun getUserToken() = sharedPreferences.getString("token",null)


    override fun setUserId(userId: String) {
        sharedPreferences.edit().putString("userId", userId).apply()
    }

    override fun setUserEmail(userEmail: String) {
        sharedPreferences.edit().putString("userEmail", userEmail).apply()
    }

    override fun setUserToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

}

interface ILoginPrefAdapter {
    fun getUserId(): String?
    fun setUserId(userId: String)
    fun getUserEmail(): String?
    fun setUserEmail(userEmail: String)
    fun getUserToken(): String?
    fun setUserToken(token: String)
}