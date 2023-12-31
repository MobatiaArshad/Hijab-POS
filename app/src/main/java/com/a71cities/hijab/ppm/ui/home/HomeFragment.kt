package com.a71cities.hijab.ppm.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.base.BaseFragment
import com.a71cities.hijab.ppm.database.HijabRoomDatabase
import com.a71cities.hijab.ppm.databinding.FragmentHomeBinding
import com.a71cities.hijab.ppm.extras.clippingBottomRec
import com.a71cities.hijab.ppm.ui.home.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        observers()

        binding.newBillBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createBillFragment)
        }
    }

    private fun observers() {
        viewModel.saleResponse.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = HomeAdapter(it)
            binding.recyclerView.clippingBottomRec(80)
        }
    }

}