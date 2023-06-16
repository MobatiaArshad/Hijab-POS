package com.a71cities.hijab.ppm.ui.reports

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a71cities.hijab.ppm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SalesReportFragment : Fragment() {

    companion object {
        fun newInstance() = SalesReportFragment()
    }

    private lateinit var viewModel: SalesReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sales_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SalesReportViewModel::class.java]
    }
}