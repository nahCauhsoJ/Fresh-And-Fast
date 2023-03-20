package com.premiumgrocery.freshandfast.ui.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.premiumgrocery.freshandfast.R
import com.premiumgrocery.freshandfast.databinding.FragmentCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheckoutFragment @Inject constructor() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentCheckoutBinding>(
        inflater, R.layout.fragment_checkout, container, false
    ).apply {
        vm = activityViewModels<ShopViewModel>().value
        lifecycleOwner = viewLifecycleOwner // This is necessary for flows to work with databinding.
        checkoutButton.setOnClickListener {
            findNavController().navigate(
                ShopFragmentDirections.actionNavigationShopToNavigationOrder(),
                navOptions {
                    // This fixes an obnoxious weird behavior where after pressing
                    //      the checkout button, orderResponseUser cannot navigate back to
                    //      the Shop tab. Probably something with the backstack.
                    this.popUpTo(R.id.navigation_home)
                }
            )
        }
    }.root
}