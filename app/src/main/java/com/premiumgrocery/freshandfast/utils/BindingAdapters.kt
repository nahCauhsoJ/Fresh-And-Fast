package com.premiumgrocery.freshandfast.utils

import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.reflect.KFunction1

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("setupWithTab", "holderFragment", "pageFragments")
    fun setViewPagerAdapter(
        viewPager: ViewPager2,
        tabLayout: TabLayout,
        holderFragment: Fragment,
        pageFragments: List<Pair<String,Fragment>>
    ) {
        ViewPagerAdapter(holderFragment, pageFragments).apply {
            viewPager.adapter = this
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getPageName(position)
            }.attach()
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["navigateFunc", "navigateBundle"],
        requireAll = false
    )
    fun setNavigateFunction(
        fragmentContainerView: FragmentContainerView,
        navigate: KFunction1<Bundle?, Unit>?,
        bundle: Bundle?
    ) { navigate?.invoke(bundle) }
}