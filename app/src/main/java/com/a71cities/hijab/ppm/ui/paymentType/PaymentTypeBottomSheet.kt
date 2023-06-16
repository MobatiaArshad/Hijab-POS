package com.a71cities.hijab.ppm.ui.paymentType

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.databinding.FragmentPaymentTypeBottomSheetBinding
import com.a71cities.hijab.ppm.utils.SingleViewSelection
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentTypeBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = PaymentTypeBottomSheet()
    }

    private lateinit var viewModel: PaymentTypeBottomSheetViewModel
    private lateinit var binding: FragmentPaymentTypeBottomSheetBinding
    private lateinit var singleSelection: SingleViewSelection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentTypeBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PaymentTypeBottomSheetViewModel::class.java]

        val paymentViews = arrayListOf(binding.gPayBtn,binding.phonePeBtn,binding.cashPayBtn)
        singleSelection = SingleViewSelection(paymentViews)

        paymentViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                singleSelection.setSelection(index)


            }
        }


        binding.include2.closeBtn.setOnClickListener{
            dismiss()
        }
        binding.confirmBtn.setOnClickListener {
            dismiss()
        }
    }

}