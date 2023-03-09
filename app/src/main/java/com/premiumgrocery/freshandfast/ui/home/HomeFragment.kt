package com.premiumgrocery.freshandfast.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentHomeBinding>(
        inflater, R.layout.fragment_home, container, false
    ).apply {
        viewModel = ViewModelProvider(this@HomeFragment)[HomeViewModel::class.java]
    }.root
}