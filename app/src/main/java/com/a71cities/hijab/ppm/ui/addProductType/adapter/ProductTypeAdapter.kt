package com.a71cities.hijab.ppm.ui.addProductType.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import com.a71cities.hijab.ppm.databinding.ProductTypeRecLytBinding

class ProductTypeAdapter(val array: List<ProductTypeEntity>): RecyclerView.Adapter<ProductTypeAdapter.VH>() {

    inner class VH(val binding: ProductTypeRecLytBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ProductTypeRecLytBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = array.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.model = array[position]
        holder.binding.executePendingBindings()

    }
}