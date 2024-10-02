package com.haquanghuy.socialdemo.ui.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haquanghuy.socialdemo.data.repository.UserRepository
import com.haquanghuy.socialdemo.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun getUserById(id: Int?) {
        viewModelScope.launch {
            userRepository.getUserById(id)?.let {
                _user.value = it
            }
        }
    }
}