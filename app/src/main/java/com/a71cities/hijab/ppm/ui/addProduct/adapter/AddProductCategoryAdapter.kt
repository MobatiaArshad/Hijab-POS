package com.a71cities.hijab.ppm.ui.addProduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.databinding.ProductCategoryRecLytBinding
import com.a71cities.hijab.ppm.ui.addProductType.model.ProductTypeResponse

class AddProductCategoryAdapter(
    private val arrayList: List<ProductTypeResponse.Data>,
    selectedId: Int = 0,
    private val click: (ProductTypeResponse.Data) -> Unit
): RecyclerView.Adapter<AddProductCategoryAdapter.VH>() {

    var selection = selectedId

    inner class VH(val binding: ProductCategoryRecLytBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ProductCategoryRecLytBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.apply {
            holder.binding.apply {
                arrayList[position].let {
                    categoryTxt.text = it.productType

                    categoryLyt.background = if (selection == 0)
                        AppCompatResources.getDrawable(context, if (selection == position) R.drawable.bg_white_round_black_stroke else R.drawable.bg_white_round_grey_stroke)
                    else
                        AppCompatResources.getDrawable(context, if (selection == it.id) R.drawable.bg_white_round_black_stroke else R.drawable.bg_white_round_grey_stroke)

                    setOnClickListener { _ ->
                        selection = it.id!!
                        click.invoke(it)

                        notifyDataSetChanged()
                    }

                }
            }
        }

    }
}