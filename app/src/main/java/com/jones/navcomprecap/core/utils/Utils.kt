package com.jones.navcomprecap.core.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import com.jones.navcomprecap.core.constants.Constants

fun File.isImage(): Boolean {
    return Regex(Constants.imageReg).containsMatchIn(this.name)
}

fun <T> RecyclerView.Adapter<*>.update(
    oldList: List<T>,
    newList: List<T>,
    compare: (T, T) -> Boolean
) {
    val callback = object : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compare(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    val diff = DiffUtil.calculateDiff(callback)

    diff.dispatchUpdatesTo(this)
}