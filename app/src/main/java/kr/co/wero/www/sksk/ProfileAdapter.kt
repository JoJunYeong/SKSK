package kr.co.wero.www.sksk

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import java.lang.Exception

class ProfileAdapter(val list:ArrayList<Int>):PagerAdapter() {

    lateinit var gesture:GestureDetector

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view==obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        gesture= GestureDetector(container.context, SingleTabGestureListener(container.context))
        val inflater=LayoutInflater.from(container.context)
        val view=inflater.inflate(R.layout.profile,container,false)
        view.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            gesture.onTouchEvent(motionEvent)
        })


        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return list.size
    }
}

class SingleTabGestureListener(var context:Context): GestureDetector.SimpleOnGestureListener() {
    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        try{
            if(velocityX<0){
                //오른쪽으로 이동
                Log.i("profile","MOVE TO RIGHT")
                Toast.makeText(context,"LIKE",Toast.LENGTH_SHORT).show()
            }else if(velocityX<0){
                //왼쪽으로 이동
                Log.i("profile","MOVE TO LEFT")
                Toast.makeText(context,"REFUSE",Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){

        }
        return true
    }

    override fun onContextClick(e: MotionEvent?): Boolean {
        try{
            Log.i("profile","IS TABBED")
            Toast.makeText(context,"SHOW PROFILE",Toast.LENGTH_SHORT).show()
        }catch (e:Exception){

        }
        return true
    }
}