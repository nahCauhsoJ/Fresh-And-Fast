package com.premiumgrocery.freshandfast.ui.register

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
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.remote.ILoginRepository
import com.premiumgrocery.freshandfast.remote.model.login.RegisterResponseSealed
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak") // Lint doesn't know the context is application context.
class RegisterViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext val context: Context
): ViewModel(), Observable {

    @get:Bindable
    var registerFormEmail = ""
        set(value) = setEmail(value).also { field = value }
    @get:Bindable
    var registerFormPassword = ""
        set(value) = setPassword(value).also { field = value }
    @get:Bindable
    var registerFormPhone = ""
        set(value) = setPhone(value).also { field = value }
    @get:Bindable
    var registerFormFirstname = ""
        set(value) = setFirstname(value).also { field = value }
    @get:Bindable
    var registerFormError = ""

    @get:Bindable
    var registerFormIsFilled = false
    @get:Bindable
    var registerIsSubmitting = false

    private val _finishRegister = MutableLiveData(false)
    val finishRegister: LiveData<Boolean> = _finishRegister

    private fun setEmail(value: String) {
        checkValidInput(e = value)
    }
    private fun setPassword(value: String) {
        checkValidInput(pw = value)
    }
    private fun setPhone(value: String) {
        checkValidInput(ph = value)
    }
    private fun setFirstname(value: String) {
        checkValidInput(n = value)
    }

    private fun checkValidInput(
        e: String? = null,
        pw: String? = null,
        ph: String? = null,
        n: String? = null
    ) {
        val email = e ?: registerFormEmail
        val password = pw ?: registerFormPassword
        val phone = ph ?: registerFormPhone
        val name = n ?: registerFormFirstname
        registerFormIsFilled = email.isNotBlank() && password.isNotBlank() &&
                phone.isNotBlank() && name.isNotBlank()
        notifyPropertyChanged(BR.registerFormIsFilled)
    }

    private fun setRegisterError(value: String) {
        registerFormError = value
        notifyPropertyChanged(BR.registerFormError)
    }

    fun onRegister() {
        registerIsSubmitting = true
        notifyPropertyChanged(BR.registerIsSubmitting)
        viewModelScope.launch(ioDispatcher) {
            loginRepository.register(
                registerFormEmail,
                registerFormPassword,
                registerFormPhone,
                registerFormFirstname
            ).collect{
                if (it is RegisterResponseSealed.Success) {
                    _finishRegister.postValue(true)
                    setRegisterError("")
                } else {
                    setRegisterError(
                        it.errorResponse?.message ?: context.getString(R.string.unknownError)
                    )
                }
                registerIsSubmitting = false
                notifyPropertyChanged(BR.registerIsSubmitting)
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