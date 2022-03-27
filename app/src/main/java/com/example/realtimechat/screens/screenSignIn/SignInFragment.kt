package com.example.realtimechat.screens.screenSignIn

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
import com.example.realtimechat.databinding.FragmentLogInBinding

class SignInFragment : Fragment(R.layout.fragment_log_in) {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var vm: SignInVM

    //    private var isInclusive:Boole an
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm = ViewModelProvider(this).get(SignInVM::class.java)
        binding.buttonGoToRegistration.setOnClickListener {

        }
        binding.buttonGoToPasswordRecovery.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_passwordRecoveryFragment)
        }

        binding.imageCatSignIn.setOnClickListener {
            vm.onCatClick()
        }
        //Sample for future navigation with using LiveData
//        vm.navigationLiveData.observe(viewLifecycleOwner) {
//            findNavController().navigate(it)
//        }
        binding.buttonGoToRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_registrationFragment)
        }
        binding.buttonLogIn.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            vm.logIn(
                binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()
            ) { aBoolean: Boolean? ->
                if (!aBoolean!!) {
                    binding.loading.visibility = View.GONE
                } else
                    findNavController().navigate(R.id.action_signInFragment_to_chatFragment2,
                        bundleOf(), navOptions {
                            launchSingleTop = true
                            popUpTo(R.id.main_graph) {
                                inclusive = true
                            }
                        })
            }
        }
    }
}