package com.a71cities.hijab.ppm.ui.createBill.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.databinding.BillRecLytBinding
import com.a71cities.hijab.ppm.extras.loadGlideWithoutBaseUrl
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse

class CreateBillAdapter(val arrayList: ArrayList<ProductsResponse.Data>): RecyclerView.Adapter<CreateBillAdapter.VH>() {

    inner class VH(val binding: BillRecLytBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(BillRecLytBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            holder.itemView.apply {
                arrayList[position].let {
                    productCodeTxt.text = "#${it.productCode.toString()}"
                    productNameTxt.text = it.productName
                    productTypeTxt.text = it.productTypeName
                    salePriceTxt.text = "₹${it.salePrice}/-"
                    productImg.loadGlideWithoutBaseUrl("https://cdn.shopify.com/s/files/1/0015/5579/1931/products/7_20-2_grande.jpg?v=1610442160")
                }

            }
        }
    }
}