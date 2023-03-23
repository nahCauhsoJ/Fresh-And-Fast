package com.premiumgrocery.freshandfast.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.premiumgrocery.freshandfast.utils.ILoginPrefAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val loginPrefAdapter: ILoginPrefAdapter
): ViewModel() {
    private val _isLoggedOut = MutableLiveData(false)
    val isLoggedOut: LiveData<Boolean> = _isLoggedOut

    // index correlates to the string array in strings.xml
    fun activateSettings(index: Int) {
        when (index) {
            0 -> {}
            1 -> {
                loginPrefAdapter.logout()
                _isLoggedOut.value = true
            }
            else -> {}
        }
    }
}