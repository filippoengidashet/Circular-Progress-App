package com.filippoengidashet.circularprogressapp.data.api

import com.filippoengidashet.circularprogressapp.data.model.Constants
import com.filippoengidashet.circularprogressapp.data.model.User
import retrofit2.http.GET

/**
 * @author Filippo 13/07/2021
 */
interface ApiService {

    @GET(Constants.ENDPOINT_USER)
    suspend fun getUser(): User
}