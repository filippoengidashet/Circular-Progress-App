package com.filippoengidashet.circularprogressapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippoengidashet.circularprogressapp.data.api.ApiService
import com.filippoengidashet.circularprogressapp.data.mapper.UserDataTransformer
import com.filippoengidashet.circularprogressapp.data.model.Constants
import com.filippoengidashet.circularprogressapp.data.model.UserData
import com.filippoengidashet.circularprogressapp.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Filippo 13/07/2021
 */
class UserViewModel : ViewModel() {

    private val repository = UserRepository(
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    )

    private val _user = MutableLiveData<UserData>()
    private val userDataTransformer by lazy { UserDataTransformer() }

    fun getUserLiveData(): LiveData<UserData> = _user

    fun loadUser(callback: (message: String?) -> Unit) {
        viewModelScope.launch {
            try {
                _user.value = userDataTransformer.map(repository.getUser())
            } catch (e: Exception) {
                callback(e.message)
            }
        }
    }
}