package com.premiumgrocery.freshandfast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.premiumgrocery.freshandfast.databinding.ActivityRegisterBinding
import com.premiumgrocery.freshandfast.ui.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val vm by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityRegisterBinding>(
            this, R.layout.activity_register
        ).apply {
            viewModel = viewModels<RegisterViewModel>().value
            lifecycleOwner = this@RegisterActivity
            vm.finishRegister.observe(this@RegisterActivity) {
                if (it) gotoLogin(vm.registerFormEmail, vm.registerFormPassword)
            }
        }
    }

    private fun gotoLogin(
        email: String,
        password: String
    ) = Intent(this, LoginActivity::class.java).apply {
        putExtra(email_arg, email)
        putExtra(password_arg, password)
        startActivity(this)
        finish()
    }

    companion object {
        const val email_arg = "email"
        const val password_arg = "password"
    }
}