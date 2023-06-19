package com.a71cities.hijab.ppm.ui.createBill.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse


class ProductCodeSpinnerAdapter(context: Context,val list: List<ProductsResponse.Data>) :
    ArrayAdapter<ProductsResponse.Data>(context, R.layout.product_code_spnr_lyt, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.product_code_spnr_lyt, parent, false)

        val codeTxt = view!!.findViewById<TextView>(R.id.productCodeTxt)
        val nameTxt = view.findViewById<TextView>(R.id.productNameTxt)
        val currentItem: ProductsResponse.Data? = getItem(position)

        codeTxt.text = "#${currentItem?.productCode}"
        nameTxt.text = currentItem?.productName
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.product_code_spnr_lyt, parent, false)

        val codeTxt = view!!.findViewById<TextView>(R.id.productCodeTxt)
        val nameTxt = view.findViewById<TextView>(R.id.productNameTxt)
        val currentItem: ProductsResponse.Data? = getItem(position)

        codeTxt.text = "#${currentItem?.productCode}"
        nameTxt.text = currentItem?.productName
        return view
    }

}