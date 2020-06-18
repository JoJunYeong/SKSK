package kr.co.wero.www.sksk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class ProfileAdapter(val list:ArrayList<Int>):PagerAdapter() {
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view==obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater=LayoutInflater.from(container.context)
        val view=inflater.inflate(R.layout.profile,container,false)


        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return list.size
    }
}