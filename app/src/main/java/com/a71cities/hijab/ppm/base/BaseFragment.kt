package com.a71cities.hijab.ppm.base

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.extras.gravityTop
import com.a71cities.hijab.ppm.utils.Loader
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

abstract class BaseFragment: Fragment() {
    abstract val viewModel: BaseViewModel

    override fun onStart() {
        super.onStart()

        viewModel.loader.observe(viewLifecycleOwner) {
            Loader.show(it)
        }

        viewModel.showAlertRes.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), getString(it!!), Snackbar.LENGTH_LONG).apply {
                gravityTop()
                view.background = ContextCompat.getDrawable(context, R.drawable.bg_snack_container)
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }.show()
        }

        viewModel.showAlertTxt.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it.toString(), Snackbar.LENGTH_LONG).apply {
                gravityTop()
                view.background = ContextCompat.getDrawable(context, R.drawable.bg_snack_container)
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }.show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) return
        postponeEnterTransition()
        view.post { postponeEnterTransition(0, TimeUnit.MILLISECONDS) }
    }

    override fun onPause() {
        super.onPause()
        viewModel.loader.removeObservers(viewLifecycleOwner)
    }
}