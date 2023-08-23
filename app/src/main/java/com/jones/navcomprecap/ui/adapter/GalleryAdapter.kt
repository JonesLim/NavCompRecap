package com.jones.navcomprecap.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jones.navcomprecap.R
import com.jones.navcomprecap.databinding.ItemLayoutImageBinding
import java.io.File

class GalleryAdapter (
    private var files: List<File>,
    val onClick: (File) -> Unit
): RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>(){

    class ImageViewHolder(val binding: ItemLayoutImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutImageBinding.inflate(
            layoutInflater, parent, false
        )
        return ImageViewHolder(binding)
    }

    override fun getItemCount() = files.size


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val file = files[position]
        Glide.with(holder.itemView)
            .load(file)
            .placeholder(R.drawable.ic_image)
            .into(holder.binding.ivImage)

        holder.binding.ivImage.setOnClickListener {
            onClick(file)
        }
    }
}