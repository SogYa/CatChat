package com.example.realtimechat.screens.screenRegistration

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.realtimechat.R
import com.example.realtimechat.databinding.FragmentRegistrationBinding
import com.example.realtimechat.instruments.MyActivityResultContract

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var vm: RegistrationAVM
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var avatarImage: ImageView
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
    private val cropActivityContract: MyActivityResultContract = MyActivityResultContract()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this).get(RegistrationAVM::class.java)
        avatarImage = view.findViewById(R.id.imageSignInAvatar)
        binding.buttonSignIn.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            vm.registration(
                binding.editTextName.text.toString(),
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            ) { o: Any? ->
                binding.loading.visibility = View.GONE
                if (o == "Success")
                    findNavController().navigate(R.id.action_registrationFragment_to_chatFragment2,
                        bundleOf(), navOptions {
                            launchSingleTop = true
                            popUpTo(R.id.main_graph) {
                                inclusive = true
                            }
                        })
            }
        }
        binding.imageSignInAvatar.setOnClickListener {
            cropActivityResultLauncher.launch(null)
        }
        binding.buttonGoBack2.setOnClickListener {
            findNavController().popBackStack()
        }
        cropActivityResultLauncher = registerForActivityResult(cropActivityContract) {
            if (it != null)
                vm.sendImage(it, binding.imageSignInAvatar)
        }
    }
}