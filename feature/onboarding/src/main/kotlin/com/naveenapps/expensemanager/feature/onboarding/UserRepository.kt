package com.naveenapps.expensemanager.feature.onboarding

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
    suspend fun getUser(email: String, password: String): UserEntity? = userDao.getUser(email, password)
}
