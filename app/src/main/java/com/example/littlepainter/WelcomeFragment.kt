package com.example.littlepainter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.littlepainter.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding:FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.setOnClickListener {
//            val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment("aaa","bbb",user)
//            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
//            binding.root.findNavController()
        }
    }
}
