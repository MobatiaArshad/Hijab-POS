package com.a71cities.hijab.ppm.utils.bottomBar

import android.widget.ImageView

class BottomBar(val arrayList: ArrayList<ImageView>) {

    fun setSelection(position: Int) {
        arrayList.forEachIndexed { index, view ->
            if (index == position) {
                view.animate()
                    .alpha(1f)
                    .duration = 500
            } else {
                view.animate()
                    .alpha(0.25f)
                    .duration = 500
            }
        }
    }
}