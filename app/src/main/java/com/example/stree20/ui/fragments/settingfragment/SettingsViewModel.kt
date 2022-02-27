package com.example.stree20.ui.fragments.settingfragment

import androidx.lifecycle.ViewModel
import com.example.stree20.data.repository.StreeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: StreeRepo
) : ViewModel() {

    fun getToken(): String? {
        return repo.getToken()
    }

}