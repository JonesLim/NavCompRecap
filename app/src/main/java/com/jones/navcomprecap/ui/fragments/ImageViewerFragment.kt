package com.jones.navcomprecap.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jones.navcomprecap.R
import com.jones.navcomprecap.databinding.FragmentImageViewerBinding
import java.io.File

class ImageViewerFragment : Fragment() {

    private lateinit var binding: FragmentImageViewerBinding

    private val args: ImageViewerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val file = File(args.path)

        Glide
            .with(this)
            .load(file)
            .placeholder(R.drawable.ic_image)
            .into(binding.ivImageViewer)
    }
}