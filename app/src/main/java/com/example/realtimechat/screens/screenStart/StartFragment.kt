package com.example.realtimechat.screens.screenStart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.realtimechat.R
import com.example.realtimechat.databinding.FragmentStartBinding

class StartFragment : Fragment(R.layout.fragment_start) {

    private lateinit var binding: FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startVM = ViewModelProvider(this).get(StartVM::class.java)
        startVM.hintsData.observe(viewLifecycleOwner) { s: String? ->
            binding.textViewHint.text = s
        }
        startVM.navigationLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(it.destinationId, bundleOf(), navOptions {
                launchSingleTop = true
                popUpTo(R.id.main_graph) {
                    inclusive = true
                }
            })
        }
    }
}