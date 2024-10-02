package com.haquanghuy.socialdemo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haquanghuy.socialdemo.data.repository.UserRepository
import com.haquanghuy.socialdemo.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository
): ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>>
        get() = _users.asStateFlow()

    init {
        viewModelScope.launch {
            _users.value = userRepository.getAllUser()
        }
    }
}