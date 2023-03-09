package com.premiumgrocery.freshandfast.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentDashboardBinding>(
        inflater, R.layout.fragment_dashboard,  container, false,
    ).apply {
        viewModel = ViewModelProvider(this@DashboardFragment)[DashboardViewModel::class.java]
    }.root
}