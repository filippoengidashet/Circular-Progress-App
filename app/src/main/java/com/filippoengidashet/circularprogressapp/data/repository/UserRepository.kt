package com.filippoengidashet.circularprogressapp.data.repository

import com.filippoengidashet.circularprogressapp.data.api.ApiService
import com.filippoengidashet.circularprogressapp.data.model.User

/**
 * @author Filippo 13/07/2021
 */
class UserRepository constructor(
    private val service: ApiService
) {
    suspend fun getUser(): User {
        return service.getUser()
    }
}
