package com.tawk.to.ui.search

import com.tawk.to.db.dao.UserDao
import com.tawk.to.db.entity.User
import com.tawk.to.db.entity.UserDetails
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val userDao: UserDao
) {
    //function for search query from DB
    fun searchUser(searchQuery: String): List<UserDetails> {
        return userDao.searchUser(searchQuery)
    }

}