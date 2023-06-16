package com.a71cities.hijab.ppm.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.base.BaseFragment
import com.a71cities.hijab.ppm.databinding.FragmentProfileBinding
import com.a71cities.hijab.ppm.ui.profile.adapter.ProfileAdapter
import com.a71cities.hijab.ppm.ui.profile.model.ProfileDataClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override lateinit var viewModel: ProfileViewModel
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding.recyclerView.adapter = ProfileAdapter(getProfileData()) { position ->
            when(position) {
                0 -> findNavController().navigate(R.id.action_profileFragment_to_addProductTypeFragment)
                1 -> {}
            }
        }
    }

    private fun getProfileData() : ArrayList<ProfileDataClass> =
        arrayListOf(
            ProfileDataClass(
                R.drawable.arrow_forward_ico,
                "Add Product Type"
            ),
            ProfileDataClass(
                R.drawable.export_file_ico,
                "Export Records"
            )
        )
}