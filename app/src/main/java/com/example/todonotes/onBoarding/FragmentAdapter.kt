package com.example.todonotes.onBoarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


// this adapter will be responsible for holding the state if a fragment is visible or not
// fragment manager is a manager for a fragment which handles its action
@Suppress("DEPRECATION")
class FragmentAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        // which fragment it has to show and on which position. here the positions are 0 and 1
        when (position) {
            0 -> return OnBoardingOneFragment()
            1 -> return OnBoardingTwoFragment()
            else -> return Fragment()
        }
    }

    // represent 2 fragments in the view pager
    override fun getCount(): Int {
        return 2
    }

}