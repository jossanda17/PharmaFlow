package com.company.pharmaflow.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.pharmaflow.ui.navigation.Screen
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var user: MutableState<FirebaseUser?> = mutableStateOf(Firebase.auth.currentUser)

    var isAuthenticated: MutableState<Boolean> = mutableStateOf(user.value != null)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        viewModelScope.launch {
            Firebase.auth.signOut()
            user.value = null
            isAuthenticated.value = false
            restartApp(Screen.Login.route)
        }
    }
}