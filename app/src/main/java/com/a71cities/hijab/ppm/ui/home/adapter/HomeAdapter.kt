package com.a71cities.hijab.ppm.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.database.model.SaleEntity
import com.a71cities.hijab.ppm.databinding.HomeRecLytBinding
import com.a71cities.hijab.ppm.ui.home.model.HomeData
import com.a71cities.hijab.ppm.ui.home.model.HomeDataResponse

class HomeAdapter(val array: List<HomeDataResponse.Data>) :RecyclerView.Adapter<HomeAdapter.VH>() {

    inner class VH(val binding: HomeRecLytBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(HomeRecLytBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = array.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.model = array[position]
        holder.binding.executePendingBindings()

        holder.itemView.apply {
            holder.binding.apply {
                array[position].let {
                    productCodeTxt.text = "#ABA${it.id}"
                    productItemsTxt.text = it.soldItems
                    salePriceTxt.text = "₹${it.paidAmount}"
                }
            }
        }

    }
}