package com.jones.navcomprecap.ui.fragments

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.jones.navcomprecap.core.constants.Constants
import com.jones.navcomprecap.core.utils.isImage
import com.jones.navcomprecap.databinding.FragmentGalleryBinding
import com.jones.navcomprecap.ui.adapter.GalleryAdapter
import java.io.File

class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: GalleryAdapter
    val listOfImages: MutableList<File> = mutableListOf()

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = NavHostFragment.findNavController(this)

        super.onViewCreated(view, savedInstanceState)

        listOfImages.clear()
        scan(File(Environment.getExternalStorageDirectory().path))
        setupAdapter(listOfImages)
    }

    private fun setupAdapter(files: List<File>) {
        adapter = GalleryAdapter(files) {file ->
            val action = HomeFragmentDirections.actionToImageViewer(file.path)
            navController.navigate(action)
        }
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager = layoutManager
    }

    private fun scan(file: File) {
        if (file.isImage()) {
            listOfImages.add(file)
            return
        }

        if (file.isDirectory) {
            file.listFiles()?.toList()?.let { files ->
                files.forEach { scan(it) }
            }
        }
    }

}