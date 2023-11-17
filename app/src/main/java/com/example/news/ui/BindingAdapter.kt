package com.example.news.ui

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.news.R

@BindingAdapter("app:url")
fun bindImageWithUrl(
    imageView: ImageView,
    url: String
) {
    Glide.with(imageView)
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .into(imageView)
}

@BindingAdapter("imageUrl")
fun imageWithUrl(
    imageView: ImageView,
    url: String
) {
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}

@BindingAdapter("launchUrl")
fun clickLaunchUrl(view: View, url: String) {
    view.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view.context.startActivity(intent)
    }
}

@BindingAdapter("imageId")
fun loadImageByIdDrawable(imageView: ImageView, image: Int) {
    imageView.setImageResource(image)
}

@BindingAdapter("backgroundColor")
fun changeCardViewBackground(cardView: CardView, color: Int) {
    cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, color))
}