package com.a71cities.hijab.ppm.utils

import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import com.a71cities.hijab.ppm.R

class SingleViewSelection(val array: ArrayList<LinearLayoutCompat>) {

    fun setSelection(position: Int) {
        array.forEachIndexed { index, view ->
            view.apply {
                background = AppCompatResources.getDrawable(context,if (index == position) R.drawable.bg_white_round_black_stroke else R.drawable.bg_white_round_grey_stroke)
            }
        }
    }
}