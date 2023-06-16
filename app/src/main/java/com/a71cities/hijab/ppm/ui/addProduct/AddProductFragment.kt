package com.a71cities.hijab.ppm.ui.addProduct

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.base.BaseFragment
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.databinding.FragmentAddProductBinding
import com.a71cities.hijab.ppm.extras.clippingRec
import com.a71cities.hijab.ppm.extras.createImageFile
import com.a71cities.hijab.ppm.extras.goBack
import com.a71cities.hijab.ppm.extras.imageChooserDialog
import com.a71cities.hijab.ppm.extras.showPermanentlyDeniedDialog
import com.a71cities.hijab.ppm.extras.showRationaleDialog
import com.a71cities.hijab.ppm.ui.products.adapter.ProductCategoryAdapter
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.anyShouldShowRationale
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddProductFragment : BaseFragment(), View.OnClickListener, PermissionRequest.Listener {

    companion object {
        fun newInstance() = AddProductFragment()
    }

    private var prodTypeId: Int? = null
    private var prodTypeName: String? = null
    override lateinit var viewModel: AddProductViewModel
    private lateinit var binding: FragmentAddProductBinding
    var file: File? = null

    private val request by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsBuilder(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_IMAGES,
            ).build()
        } else {
            permissionsBuilder(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).build()
        }

    }

    private val cameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            Glide.with(requireActivity())
                .load(Uri.fromFile(file?.absoluteFile!!))
                .into(binding.chosenImage)

            viewModel.imgToUpload.value = file?.absolutePath!!
        }
    }

    private val galleryLauncher = registerImagePicker {
        Glide.with(requireActivity())
            .load(it[0].uri)
            .into(binding.chosenImage)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddProductViewModel::class.java]

        setPermission()
        observers()

        binding.chooseImage.setOnClickListener(this)
        binding.closeBtn.setOnClickListener(this)
        binding.submitBtn.setOnClickListener(this)
    }

    private fun observers() {
        viewModel.types.observe(viewLifecycleOwner) { list ->

            prodTypeId = list[0].id
            prodTypeName = list[0].productType

            binding.categoryRec.apply {
                clippingRec(list.size)
                adapter = ProductCategoryAdapter(list) {
                    prodTypeId = it.id
                    prodTypeName = it.productType

                }
            }
        }

    }

    private fun setPermission() {
        request.addListener(this)
        request.addListener {
            if (it.anyPermanentlyDenied()) {
                viewModel.showAlertTxt.value = "Please grant all permission"
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.closeBtn.id -> goBack()
            binding.chooseImage.id -> request.send()
            binding.submitBtn.id -> {

                val products = ProductsEntity(
                    productName = binding.proNameEdt.text?.trim().toString(),
                    productCode = binding.proCodeEdt.text?.trim().toString(),
                    productTypeName = prodTypeName!!,
                    productTypeId = prodTypeId!!,
                    size = binding.proSizeEdt.text?.trim().toString(),
                    price = binding.proPriceEdt.text?.trim().toString().toInt(),
                    salePrice = binding.proSalePriceEdt.text?.trim().toString().toInt()
                )

                viewModel.addProduct(products)

                lifecycleScope.launch {
                    delay(1000)
                    goBack()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onPermissionsResult(result: List<PermissionStatus>) {
        when {
            result.anyPermanentlyDenied() -> showPermanentlyDeniedDialog(result)
            result.anyShouldShowRationale() -> showRationaleDialog(result, request)
            result.allGranted() -> imageChooserDialog {
                when(it) {
                    "Gallery" -> {
                        openGallery()
                    }
                    "Camera" -> {
                        openCamera()
                    }
                }
            }
        }
    }

    private fun openGallery() {

        val config = ImagePickerConfig {
            theme = R.style.Theme_Hijab
            mode = ImagePickerMode.SINGLE
            isFolderMode = true
            folderTitle = "Pick your file"
            isShowCamera = false
            isIncludeVideo = true
            isIncludeAnimation = true
        }

        galleryLauncher.launch(config)
    }

    private fun openCamera() {
        file = createImageFile()
        val photoURI = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.fileprovider", file!!)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }
        cameraResult.launch(intent)
    }
}