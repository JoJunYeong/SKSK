package com.example.fragmentuse

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyFragmentAdapter(fm:FragmentManager) :   FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                chating()
            }
            1->{
                like()
            }
            2->{
                heart()
            }
            else->{
                chating()
            }
        }
    }
    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0->{
                "메시지"
            }
            1->{"좋아요"}
            else->{"심쿵"}
        }
    }
}