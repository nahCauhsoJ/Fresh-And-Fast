package com.premiumgrocery.freshandfast.remote

import com.google.gson.Gson
import com.premiumgrocery.freshandfast.remote.model.*
import com.premiumgrocery.freshandfast.remote.model.login.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiGrocery: ApiGrocery,
    private val gson: Gson
): ILoginRepository {
    override fun register(
        email: String,
        password: String,
        phone: String,
        firstname: String
    ) = flow {
        apiGrocery.postRegisterUser(
            RegisterRequestBody(
                email = email,
                password = password,
                mobile = phone,
                firstName = firstname )
        ).apply{
            body()?.let {
                emit(
                    RegisterResponseSealed.Success(
                        gson.fromJson(gson.toJsonTree(it), RegisterSuccessResponse::class.java)
                ))
            }
            errorBody()?.let {
                val error = it.string()
                emit(
                    RegisterResponseSealed.Error(
                        gson.fromJson(error, ErrorResponse::class.java)
                ))
            }
        }
    }

    override fun login(email: String, password: String) = flow {
        apiGrocery.postLoginUser(
            LoginRequestBody( email, password )
        ).apply{
            body()?.let {
                emit(
                    LoginResponseSealed.Success(
                        gson.fromJson(gson.toJsonTree(it), LoginSuccessResponse::class.java)
                ))
            }
            errorBody()?.let {
                val error = it.string() // This one is a stream. Only run it once.
                emit(
                    LoginResponseSealed.Error(
                        gson.fromJson(error, ErrorResponse::class.java)
                ))
            }
        }
    }
}

interface ILoginRepository {
    fun register(email: String, password: String, phone: String,
                 firstname: String): Flow<RegisterResponseSealed>
    fun login(email: String,password: String): Flow<LoginResponseSealed>
}