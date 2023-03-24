package com.premiumgrocery.freshandfast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.premiumgrocery.freshandfast.databinding.ActivityLoginBinding
import com.premiumgrocery.freshandfast.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val vm by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(
            this, R.layout.activity_login
        ).apply {
            if (vm.isAlreadyLoggedIn) gotoMain()

            vm.fillInForm(
                intent.getStringExtra(RegisterActivity.email_arg) ?: "",
                intent.getStringExtra(RegisterActivity.password_arg) ?: ""
            )

            activity = this@LoginActivity
            viewModel = vm
            lifecycleOwner = this@LoginActivity
            vm.finishLogin.observe(this@LoginActivity) {
                if (it) gotoMain()
            }
        }
    }

    private fun gotoMain() = Intent(this@LoginActivity, MainActivity::class.java).apply {
        startActivity(this)
        finish()
    }

    fun gotoRegister() = startActivity(Intent(this, RegisterActivity::class.java))
}