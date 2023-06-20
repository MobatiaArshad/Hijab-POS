package com.a71cities.hijab.ppm.ui.paymentType

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a71cities.hijab.ppm.databinding.FragmentPaymentTypeBottomSheetBinding
import com.a71cities.hijab.ppm.extras.Constants.PAYMENT_COMPLETED
import com.a71cities.hijab.ppm.extras.getCurrentDateTime
import com.a71cities.hijab.ppm.extras.openSingleButtonAlert
import com.a71cities.hijab.ppm.utils.SingleViewSelection
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentTypeBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {

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
        binding = FragmentPaymentTypeBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PaymentTypeBottomSheetViewModel::class.java]

        observers()

        val paymentViews = arrayListOf(binding.gPayBtn, binding.phonePeBtn, binding.cashPayBtn)
        singleSelection = SingleViewSelection(paymentViews)
        singleSelection.setSelection(0)

        paymentViews.forEachIndexed { index, view ->
            view.setOnClickListener {
                singleSelection.setSelection(index)

                when (it.id) {
                    binding.gPayBtn.id -> viewModel.passedCardData.value?.paymentType = 1
                    binding.phonePeBtn.id -> viewModel.passedCardData.value?.paymentType = 2
                    binding.cashPayBtn.id -> viewModel.passedCardData.value?.paymentType = 3
                }
            }
        }

        binding.include2.closeBtn.setOnClickListener(this)
        binding.confirmBtn.setOnClickListener(this)
    }

    private fun observers() {
        viewModel.dataSubmitted.observe(viewLifecycleOwner) {
            if (it!!) {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    PAYMENT_COMPLETED,
                    true
                )
                dismiss()
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.include2.closeBtn.id -> dismiss()
            binding.confirmBtn.id -> checkValidation()
        }
    }

    private fun checkValidation() {
        if (binding.paidAmountEdt.text.isNullOrEmpty()) {
            openSingleButtonAlert("Warning!!", "Please enter the Paid Amount", "OK")
        } else {
            viewModel.passedCardData.value?.paidAmount = binding.paidAmountEdt.text.toString().toInt()
            viewModel.passedCardData.value?.phoneNumber = binding.phoneNumberEdt.text.toString()
            viewModel.passedCardData.value?.date = getCurrentDateTime()

            viewModel.addNewBill()

        }
    }

}