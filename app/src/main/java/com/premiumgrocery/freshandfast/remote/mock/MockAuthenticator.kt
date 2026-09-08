package com.premiumgrocery.freshandfast.remote.mock

import com.premiumgrocery.freshandfast.remote.model.UserData
import com.premiumgrocery.freshandfast.remote.model.login.LoginRequestBody

class MockAuthenticator {
    private val users = listOf(
        UserData(
            createdAt = "2023-01-01",
            email = "user1@example.com",
            firstName = "User One",
            id = "user1",
            mobile = "1234567890",
            password = "password1",
            v = 0
        ),
        UserData(
            createdAt = "2023-01-01",
            email = "user2@example.com",
            firstName = "User Two",
            id = "user2",
            mobile = "1234567891",
            password = "password2",
            v = 0
        ),
        UserData(
            createdAt = "2023-01-01",
            email = "user3@example.com",
            firstName = "User Three",
            id = "user3",
            mobile = "1234567892",
            password = "password3",
            v = 0
        )
    )

    fun authenticate(loginBody: LoginRequestBody): UserData? {
        return users.find { it.email == loginBody.email && it.password == loginBody.password }
    }
}
