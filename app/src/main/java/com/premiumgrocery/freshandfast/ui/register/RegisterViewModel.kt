package com.premiumgrocery.freshandfast.ui.register

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel(), Observable {

    @get:Bindable
    var registerFormEmail = ""
        set(value) = setEmail(value)
    @get:Bindable
    var registerFormPassword = ""
        set(value) = setPassword(value)
    @get:Bindable
    var registerFormPhone = ""
        set(value) = setPhone(value)
    @get:Bindable
    var registerFormFirstname = ""
        set(value) = setFirstname(value)

    private fun setEmail(value: String) {

    }
    private fun setPassword(value: String) {

    }
    private fun setPhone(value: String) {

    }
    private fun setFirstname(value: String) {

    }

    fun onRegister() {

    }

    private val mCallbacks = PropertyChangeRegistry()
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.add(callback)
    }
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallbacks.remove(callback)
    }
}