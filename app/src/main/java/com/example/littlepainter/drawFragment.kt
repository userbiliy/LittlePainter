package com.example.littlepainter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.littlepainter.databinding.FragmentDrawBinding
import com.example.littlepainter.databinding.FragmentWelcomeBinding

class drawFragment : Fragment() {
    private lateinit var binding: FragmentDrawBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDrawBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
}