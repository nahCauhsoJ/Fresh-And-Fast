package com.premiumgrocery.freshandfast.utils

import android.content.SharedPreferences
import javax.inject.Inject

class LoginPrefAdapter @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ILoginPrefAdapter {
    override fun getUserId() = sharedPreferences.getString(userId,null)
    override fun getUserEmail() = sharedPreferences.getString(userEmail,null)
    override fun getUserToken() = sharedPreferences.getString(token,null)


    override fun setUserId(userId: String) {
        sharedPreferences.edit().putString(LoginPrefAdapter.userId, userId).apply()
    }

    override fun setUserEmail(userEmail: String) {
        sharedPreferences.edit().putString(LoginPrefAdapter.userEmail, userEmail).apply()
    }

    override fun setUserToken(token: String) {
        sharedPreferences.edit().putString(LoginPrefAdapter.token, token).apply()
    }

    override fun logout() {
        sharedPreferences.edit().apply {
            remove(userId)
            remove(userEmail)
            remove(token)
        }.apply()
    }

    companion object {
        private const val userId = "userId"
        private const val userEmail = "userEmail"
        private const val token = "token"
    }
}

interface ILoginPrefAdapter {
    fun getUserId(): String?
    fun setUserId(userId: String)
    fun getUserEmail(): String?
    fun setUserEmail(userEmail: String)
    fun getUserToken(): String?
    fun setUserToken(token: String)
    fun logout()
}