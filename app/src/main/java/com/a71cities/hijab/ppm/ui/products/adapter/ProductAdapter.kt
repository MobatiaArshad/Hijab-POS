package com.a71cities.hijab.ppm.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.databinding.ProductRecLytBinding
import com.a71cities.hijab.ppm.extras.loadGlideWithoutBaseUrl

class ProductAdapter(val list: List<ProductsEntity>): RecyclerView.Adapter<ProductAdapter.VH>() {

    inner class VH(val binding: ProductRecLytBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ProductRecLytBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            holder.itemView.apply {
                list[position].let {
                    productCodeTxt.text = "#${it.productCode}"
                    productNameTxt.text = it.productName
                    productTypeTxt.text = it.productTypeName
                    productPriceTxt.text = "₹${it.salePrice}"
                    productImg.loadGlideWithoutBaseUrl("https://cdn.shopify.com/s/files/1/0015/5579/1931/products/7_20-2_grande.jpg?v=1610442160")
                }
            }
        }
    }
}