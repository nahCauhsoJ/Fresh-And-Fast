package com.premiumgrocery.freshandfast.ui.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.premiumgrocery.freshandfast.BR
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.remote.ILoginRepository
import com.premiumgrocery.freshandfast.remote.model.login.LoginResponseSealed
import com.premiumgrocery.freshandfast.utils.ILoginPrefAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak") // Lint doesn't know the context is application context.
class LoginViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val loginPrefAdapter: ILoginPrefAdapter,
    @ApplicationContext val context: Context
): ViewModel(), Observable {
    val isAlreadyLoggedIn = loginPrefAdapter.getUserToken() != null

    // Note that with this way of setting values, getting loginFormEmail and other
    //      similar variables from setEmail() and similar will give the old value.
    //      To use the new value, use the value provided by the parameter instead.
    @get:Bindable
    var loginFormEmail = ""
        set(value) = setEmail(value).also { field = value }
    @get:Bindable
    var loginFormPassword = ""
        set(value) = setPassword(value).also { field = value }

    @get:Bindable
    var loginFormIsFilled = false
    @get:Bindable
    var loginIsSubmitting = false

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
        loginFormIsFilled = email.isNotBlank() && password.isNotBlank()
        notifyPropertyChanged(BR.loginFormIsFilled)
    }

    private fun setLoginError(value: String) {
        loginFormError = value
        notifyPropertyChanged(BR.loginFormError)
    }

    fun fillInForm(email: String, password: String) {
        loginFormEmail = email
        loginFormPassword = password
        notifyPropertyChanged(BR.loginFormEmail)
        notifyPropertyChanged(BR.loginFormPassword)
    }

    fun onLogin() {
        loginIsSubmitting = true
        notifyPropertyChanged(BR.loginIsSubmitting)

        viewModelScope.launch(ioDispatcher) {
            loginRepository.login(loginFormEmail, loginFormPassword).collect{
                if (it is LoginResponseSealed.Success) {
                    _finishLogin.postValue(true)
                    loginPrefAdapter.setUserId(it.successResponse!!.user.id)
                    loginPrefAdapter.setUserEmail(it.successResponse.user.email)
                    loginPrefAdapter.setUserToken(it.successResponse.token)
                    setLoginError("")
                }
                else {
                    setLoginError(it.errorResponse?.message?:
                        context.getString(R.string.unknownError)
                    )
                }
                loginIsSubmitting = false
                notifyPropertyChanged(BR.loginIsSubmitting)
            }
        }
    }

    private val mCallbacks = PropertyChangeRegistry()
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.add(callback)
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.remove(callback)
    }
    private fun notifyPropertyChanged(fieldId: Int) {
        mCallbacks.notifyCallbacks(this, fieldId, null)
    }
}