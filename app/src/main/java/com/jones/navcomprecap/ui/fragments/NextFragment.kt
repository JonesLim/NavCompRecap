package com.jones.navcomprecap.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.jones.navcomprecap.databinding.FragmentNextBinding

class NextFragment : Fragment() {

    private val navArgs: NextFragmentArgs by navArgs()
    private lateinit var binding: FragmentNextBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentNextBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            tvText.text = navArgs.msg
            btnBack.setOnClickListener{
                val bundle = Bundle()
                bundle.putString("msg","Greeting to home")
                setFragmentResult("from_next_to_home",bundle)
                NavHostFragment.findNavController(this@NextFragment).popBackStack()
            }
        }
    }

}