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
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.planatech.expenditures.R
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

@BindingAdapter("textFromHtml")
fun MaterialTextView.textFromHtml(text: String?) {
    this.text = HtmlCompat.fromHtml(text ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
    this.movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter("textFromFloat")
fun MaterialTextView.textFromFloat(text: Float?){
    this.text = "Balance "+ text.toString()
}

@BindingAdapter("textFromString")
fun MaterialTextView.textFromString(text: String?){
    this.text = text
}

@BindingAdapter("checkAuthorVisibility")
fun MaterialTextView.checkAuthorVisibility(text: String?) {
    if (text.isNullOrEmpty())
        this.visibility = View.GONE
}
