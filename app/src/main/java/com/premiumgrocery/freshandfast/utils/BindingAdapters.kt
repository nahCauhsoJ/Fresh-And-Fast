package com.premiumgrocery.freshandfast.utils

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

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
}