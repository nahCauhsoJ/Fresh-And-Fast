package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.remote.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiGrocery: ApiGrocery
): ILoginRepository {
    override fun register(
        email: String,
        password: String,
        phone: String,
        firstname: String
    ) = flow {
        apiGrocery.postRegisterUser(
            RegisterRequestBody( email, password, phone, firstname )
        ).body()?.apply {
            emit(
                if (this is RegisterSuccessResponse) RegisterResponseSealed.Success(this)
                else RegisterResponseSealed.Error (this as ErrorResponse)
            )
        }
    }

    override fun login(email: String, password: String) = flow {
        apiGrocery.postLoginUser(
            LoginRequestBody( email, password )
        ).apply{
            println(this.toString())
            println(this.body().toString())
            println(this.errorBody().toString())
        }.body()?.apply {
            emit(
                if (this is LoginSuccessResponse) LoginResponseSealed.Success(this)
                else LoginResponseSealed.Error (this as ErrorResponse)
            )
        }
    }

}

interface ILoginRepository {
    fun register(email: String, password: String, phone: String,
                 firstname: String): Flow<RegisterResponseSealed>
    fun login(email: String,password: String): Flow<LoginResponseSealed>
}