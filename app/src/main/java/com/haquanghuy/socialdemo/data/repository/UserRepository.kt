package com.haquanghuy.socialdemo.data.repository

import com.haquanghuy.socialdemo.domain.User

interface UserRepository {
    suspend fun getAllUser(): List<User>
    suspend fun getUserById(id: Int?): User?
}