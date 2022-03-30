package com.example.realtimechat.screens.screenPasswordRecovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.realtimechat.R
import com.example.realtimechat.databinding.FragmentPasswordRecoveryBinding

class PasswordRecoveryFragment : Fragment(R.layout.fragment_password_recovery) {
    private lateinit var binding: FragmentPasswordRecoveryBinding
    private lateinit var vm: PasswordRecoveryAVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordRecoveryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this).get(PasswordRecoveryAVM::class.java)
        binding.button.setOnClickListener{
            vm.passwordRecovery(binding.editTextEmailAddressRec.text.toString())
        }
        binding.buttonGoBack3.setOnClickListener{
            findNavController().popBackStack()
        }
    }


}