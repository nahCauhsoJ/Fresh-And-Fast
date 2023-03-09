package com.premiumgrocery.freshandfast.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {
    private lateinit var viewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentNotificationsBinding>(
        inflater, R.layout.fragment_notifications, container, false
    ).apply {
        viewModel = ViewModelProvider(this@NotificationsFragment)[NotificationsViewModel::class.java]
    }.root
}