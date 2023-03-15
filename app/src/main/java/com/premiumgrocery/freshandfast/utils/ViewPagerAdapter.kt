package com.premiumgrocery.freshandfast.utils

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    holderFragment: Fragment,
    private val pageFragments: List<Pair<String,Fragment>>
): FragmentStateAdapter(holderFragment) {
    override fun getItemCount(): Int = pageFragments.size
    override fun createFragment(position: Int): Fragment = pageFragments[position].second
    fun getPageName(position: Int) = pageFragments[position].first
}