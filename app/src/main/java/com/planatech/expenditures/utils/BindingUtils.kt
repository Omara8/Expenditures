package com.planatech.expenditures.utils

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.planatech.expenditures.R
import com.planatech.expenditures.dashboard.view.DashboardAdapter
import com.planatech.expenditures.utils.extensions.decodeDots

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context).load(imageUrl?.decodeDots()).apply(
        RequestOptions().placeholder(R.drawable.ic_placeholder_svg)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    ).into(imageView)
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ShapeableImageView, imageUrl: String?) {
    Glide.with(imageView.context).load(imageUrl?.decodeDots()).apply(
        RequestOptions().placeholder(R.drawable.ic_placeholder_svg)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    ).into(imageView)
}

@BindingAdapter("setAdapter")
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>?) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}

@BindingAdapter("setFirebaseAdapter")
fun RecyclerView.bindRecyclerViewFirebaseAdapter(adapter: FirebaseRecyclerPagingAdapter<*, DashboardAdapter.DashboardViewHolder>?) {
    this.run {
        this.adapter = adapter
    }
}

@BindingAdapter("textFromHtml")
fun MaterialTextView.textFromHtml(text: String?) {
    this.text = HtmlCompat.fromHtml(text ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
    this.movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter("stringFromFloat")
fun TextInputEditText.stringFromFloat(text: Float?) {
    this.setText(text.toString())
}

@BindingAdapter("balanceFromFloat")
fun MaterialTextView.balanceFromFloat(text: Float?) {
    this.text = "Balance " + text.toString()
}

@BindingAdapter("salaryFromFloat")
fun MaterialTextView.salaryFromFloat(text: Float?) {
    this.text = "Salary " + text.toString()
}

@BindingAdapter("amountFromFloat")
fun MaterialTextView.amountFromFloat(text: Float?) {
    this.text = "Amount " + text.toString()
}

@BindingAdapter("textFromString")
fun MaterialTextView.textFromString(text: String?) {
    this.text = text
}

@BindingAdapter("checkVisibility")
fun MaterialTextView.checkAuthorVisibility(text: String?) {
    if (text.isNullOrEmpty())
        this.visibility = View.GONE
}