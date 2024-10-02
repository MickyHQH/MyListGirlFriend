package com.haquanghuy.socialdemo.data.repository

import com.haquanghuy.socialdemo.domain.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    private val listUserDummy = listOf(
        User(1, "https://i.mydramalist.com/rPL27_5c.jpg", "Huy Ha 1", "Hello there"),
        User(
            2,
            "https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2021/10/10/962246/Suzy3.jpeg",
            "Huy Ha 2",
            "Hello there"
        ),
        User(3, "https://i.mydramalist.com/rPL27_5c.jpg", "Huy Ha 3", "Hello there. "),
        User(
            4,
            "https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2021/10/10/962246/Suzy3.jpeg",
            "Huy Ha 4",
            "Hello there"
        ),
    )

    override suspend fun getAllUser(): List<User> {
        return listUserDummy
    }

    override suspend fun getUserById(id: Int?): User? {
        return listUserDummy.firstOrNull { it.id == id }
    }
}