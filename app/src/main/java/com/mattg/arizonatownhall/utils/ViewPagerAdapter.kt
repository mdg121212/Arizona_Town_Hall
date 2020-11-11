package com.mattg.arizonatownhall.utils

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mattg.arizonatownhall.ui.home.HomeFragment
import com.mattg.arizonatownhall.ui.registrationDonation.RegistrationDonationFragment
import com.mattg.arizonatownhall.ui.social2.SocialFragment


class ViewPagerAdapter(@NonNull fragmentActivity: FragmentActivity?) :
    FragmentStateAdapter(fragmentActivity!!) {
    @NonNull
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> SocialFragment()
            2 -> RegistrationDonationFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    companion object {
        private const val CARD_ITEM_SIZE = 3
    }
}