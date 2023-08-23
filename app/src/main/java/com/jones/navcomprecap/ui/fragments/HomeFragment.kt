package com.jones.navcomprecap.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jones.navcomprecap.core.constants.Constants
import com.jones.navcomprecap.databinding.FragmentHomeBinding
import com.jones.navcomprecap.ui.adapter.FileAdapter
import com.jones.navcomprecap.ui.viewModel.HomeViewModel
import java.io.File
import kotlin.math.log

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var adapter: FileAdapter
    private lateinit var navController: NavController

    private val listOfImages: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = NavHostFragment.findNavController(this)
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

//        binding.run {
//            btnNext.setOnClickListener {
//                val navController = NavHostFragment.findNavController(this@HomeFragment)
//                val action = HomeFragmentDirections.actionHomeToNext("Greeting!!")
//                navController.navigate(action)
//            }
//        }

//        setFragmentResultListener("from_next_to_home") { _, res ->
//            val msg = res.getString("msg") ?: "Default string"
//            binding.tvHello.text = msg
//        }

        val path = if (args.path != "null") {
            args.path!!
        } else {
            Environment.getExternalStorageDirectory().path
        }


//        setupAdapter()
        val root = File(path)

        root.listFiles()?.let {
            adapter.setFiles(it.toList())
        }

        scan(File(Environment.getExternalStorageDirectory().path))
        listOfImages.forEach {
            Log.d("listOfImages", it)
        }
    }

//    fun scan(file: File) {
//        if (file.isDirectory) {
//            file.listFiles()?.forEach {
//                scan(it)
//            }
//        } else if (Regex(Constants.imageReg).containsMatchIn(file.path)) {
//            listOfImages.add(file.path)
//        }
//    }
//    own code

    private fun scan(files: File){
        if (files.isDirectory)
            files.listFiles()?.let {
                it.forEach { file ->
                    scan(file)
                }
            }
        else if(Regex(Constants.imageReg).containsMatchIn(files.path))
            listOfImages.add(files.path)
    }

    fun setupAdapter() {
        adapter = FileAdapter(
            emptyList()

        ) { file ->

            if (file.isDirectory) {
                val action = HomeFragmentDirections.actionHomeFragmentSelf(file.path)
                navController.navigate(action)
            } else if (Regex(Constants.imageReg).containsMatchIn(file.path)) {
                val action = HomeFragmentDirections.actionToImageViewer(file.path)
                navController.navigate(action)
            } else {
//                Snackbar.make(binding.root, "File not supported!", Snackbar.LENGTH_SHORT).show()

//                Toast.makeText(requireContext(), "File not supported!", Toast.LENGTH_SHORT).show()

                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.fromFile(file))
                    startActivity(intent)
                } catch (e: Exception) {
                    val snackbar =
                        Snackbar.make(
                            binding.root,
                            "File format is not supported!",
                            Snackbar.LENGTH_LONG
                        )

                    snackbar.show()
                }
            }
        }

        binding.rvFiles.adapter = adapter
        binding.rvFiles.layoutManager = LinearLayoutManager(requireContext())
    }
}
