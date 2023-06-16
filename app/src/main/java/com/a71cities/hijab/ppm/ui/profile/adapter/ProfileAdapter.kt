package com.a71cities.hijab.ppm.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.databinding.ProfileRecLytBinding
import com.a71cities.hijab.ppm.ui.profile.model.ProfileDataClass

class ProfileAdapter(
    val arrayList: ArrayList<ProfileDataClass>,
    val click: (Int) -> Unit
    ): RecyclerView.Adapter<ProfileAdapter.VH>() {

    inner class VH(val binding: ProfileRecLytBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ProfileRecLytBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            holder.itemView.apply {
                arrayList[position].let {
                    itemsImg.setImageResource(it.img)
                    titleTxt.text = it.title

                    setOnClickListener {
                        click.invoke(position)
                    }
                }
            }
        }
    }
}