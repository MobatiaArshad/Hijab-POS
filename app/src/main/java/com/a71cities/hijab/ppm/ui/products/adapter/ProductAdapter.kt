package com.a71cities.hijab.ppm.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.databinding.ProductRecLytBinding
import com.a71cities.hijab.ppm.extras.loadGlideWithoutBaseUrl
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse

class ProductAdapter(
    private val list: List<ProductsResponse.Data>,
    private val click: (ProductsResponse.Data) -> Unit
) : RecyclerView.Adapter<ProductAdapter.VH>() {

    inner class VH(val binding: ProductRecLytBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ProductRecLytBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            holder.itemView.apply {
                list[position].let {
                    productCodeTxt.text = "#${it.productCode}"
                    productNameTxt.text = it.productName
                    productTypeTxt.text = it.productTypeName
                    productPriceTxt.text = "₹${it.salePrice}"
                    productQtyTxt.text = "Qty: ${it.quantity}"
                    productImg.loadGlideWithoutBaseUrl(
                        when(it.productTypeName) {
                            "Hijab" -> "https://cdn.siasat.com/wp-content/uploads/2021/10/sana-khan.jpg"
                            "Muzwalla" -> "https://media.istockphoto.com/id/1053754316/photo/muslim-prayers-in-tashahhud-posture.jpg?s=612x612&w=0&k=20&c=DbOv2EITeTSbbO8kGwtW978FfrcC8CMLyfPnP_x2-l0="
                            else  -> "https://cdn.shopify.com/s/files/1/0015/5579/1931/products/7_20-2_grande.jpg?v=1610442160"
                        }

                    )

                    if (it.quantity!!.toInt() <= 3)
                        productQtyTxt.setTextColor(ContextCompat.getColor(context, R.color.splash_red))
                    else
                        productQtyTxt.setTextColor(ContextCompat.getColor(context, R.color.hashTagColor))


                    setOnClickListener { _ ->
                        click.invoke(it)
                    }
                }
            }
        }
    }
}