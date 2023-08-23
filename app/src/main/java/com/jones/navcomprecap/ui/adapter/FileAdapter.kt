package com.jones.navcomprecap.ui.adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jones.navcomprecap.R
import com.jones.navcomprecap.core.constants.Constants
import com.jones.navcomprecap.databinding.FileLayoutBinding
import java.io.File

class FileAdapter(
    private var files: List<File>,
    private val onClick: (File) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: FileLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = FileLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    override fun getItemCount(): Int = files.size

    fun setFiles(files: List<File>) {
        this.files = files
//        files.forEach {
//            Log.d("file", it.name)
//        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val file = files[position]
        if (holder is FileViewHolder) {
            holder.binding.run {
                if (file.isDirectory) {
                    ivFile.setImageResource(R.drawable.ic_file)
                } else if (Regex(Constants.imageReg).containsMatchIn(file.path)) {
//                    val imageBitmap = BitmapFactory.decodeFile(file.absolutePath)
//                    ivFile.setImageBitmap(imageBitmap)

                    Glide
                        .with(binding.root)
                        .load(file)
                        .centerCrop()
//                        .override(100, 100)
                        .placeholder(R.drawable.ic_image)
                        .into(ivFile)

//                    ivFile.layoutParams.height = 100
//                    ivFile.layoutParams.width = 100
//                    ivFile.scaleType = android.widget.ImageView.ScaleType.CENTER_CROP

//                    for displaying the image in the image view, we can use Glide library.
//                    placeholder is used to display the icon while the image is loading.

                } else {
                    ivFile.setImageResource(R.drawable.ic_files)
                }

                tvFileName.text = file.name
                llFile.setOnClickListener {
                    onClick(file)
                }
            }
        }
    }

    class FileViewHolder(val binding: FileLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}