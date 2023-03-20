package com.premiumgrocery.freshandfast.ui.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.premiumgrocery.freshandfast.BR
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.remote.LoginRepository
import com.premiumgrocery.freshandfast.utils.LoginPrefAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak") // Lint doesn't know the context is application context.
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val loginPrefAdapter: LoginPrefAdapter,
    @ApplicationContext val context: Context
): ViewModel(), Observable {
    val isAlreadyLoggedIn = loginPrefAdapter.getUserToken() != null

    // Note that with this way of setting values, any functions run inside setEmail()
    //      and setPassword() will get the old value of loginFormEmail. Use
    //      the given value as the new value.
    @get:Bindable
    var loginFormEmail = ""
        set(value) = setEmail(value).also { field = value }
    @get:Bindable
    var loginFormPassword = ""
        set(value) = setPassword(value).also { field = value }

    @get:Bindable
    var loginFormCanSubmit = false

    @get:Bindable
    var loginFormError = ""

    private val _finishLogin = MutableLiveData(false)
    val finishLogin: LiveData<Boolean> = _finishLogin


    private fun setEmail(value: String) {
        checkValidInput(e = value)
    }
    private fun setPassword(value: String) {
        checkValidInput(p = value)
    }

    // Due to how the Bindable is set up, without supplying the parameters
    //      the check will use the values before the change.
    private fun checkValidInput(e: String? = null, p: String? = null) {
        val email = e ?: loginFormEmail
        val password = p ?: loginFormPassword
        loginFormCanSubmit = email.isNotBlank() && password.isNotBlank()
        notifyPropertyChanged(BR.loginFormCanSubmit)
    }

    private fun setLoginError(value: String) {
        loginFormError = value
    }

    fun onLogin() {
        /*viewModelScope.launch(ioDispatcher) {
            loginRepository.login(loginFormEmail, loginFormPassword).collect{
                if (it is LoginResponseSealed.Success) {
                    println("Success?")
                }
                else {
                    setLoginError(it.errorResponse?.message?:
                        context.getString(R.string.unknownError)
                    )
                }
            }
        }*/
        // Let's do a backdoor access now. Login doesn't seem to work.
        _finishLogin.value = true
        loginPrefAdapter.setUserId(Const.placeholderUserId)
        loginPrefAdapter.setUserEmail(Const.placeholderUserEmail)
        loginPrefAdapter.setUserToken(Const.placeholderToken)
    }

    private val mCallbacks = PropertyChangeRegistry()
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.add(callback)
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.remove(callback)
    }
    fun notifyPropertyChanged(fieldId: Int) {
        mCallbacks.notifyCallbacks(this, fieldId, null)
    }
}