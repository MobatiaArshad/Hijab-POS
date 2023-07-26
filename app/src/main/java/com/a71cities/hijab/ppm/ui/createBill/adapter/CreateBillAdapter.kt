package com.a71cities.hijab.ppm.ui.createBill.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.databinding.BillRecLytBinding
import com.a71cities.hijab.ppm.extras.loadGlideWithoutBaseUrl
import com.a71cities.hijab.ppm.extras.log
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse

class CreateBillAdapter(private val arrayList: ArrayList<ProductsResponse.Data>): RecyclerView.Adapter<CreateBillAdapter.VH>() {

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
                    elegantLyt.qtyCountTxt.text = it.cartQty.toString()
                    log(it.cartQty)

                    productImg.loadGlideWithoutBaseUrl(
                        when(it.productTypeName) {
                            "Hijab" -> "https://cdn.siasat.com/wp-content/uploads/2021/10/sana-khan.jpg"
                            "Muzwalla" -> "https://media.istockphoto.com/id/1053754316/photo/muslim-prayers-in-tashahhud-posture.jpg?s=612x612&w=0&k=20&c=DbOv2EITeTSbbO8kGwtW978FfrcC8CMLyfPnP_x2-l0="
                            else  -> "https://cdn.shopify.com/s/files/1/0015/5579/1931/products/7_20-2_grande.jpg?v=1610442160"
                        }

                    )

                    elegantLyt.plusBtn.setOnClickListener { _ ->
                        if (it.cartQty <= 1) {
                            it.cartQty.minus(1)
                        }
                        notifyItemChanged(position)
                    }

                    elegantLyt.minusBtn.setOnClickListener { _ ->
//                        if (it.cartQty <= it.quantity!!.toInt()) {
                            it.cartQty.plus(1)
//                        }
                        notifyItemChanged(position)
                    }

                }

            }
        }
    }
}