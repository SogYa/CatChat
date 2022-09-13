package com.example.realtimechat.screens.screenUserProfile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.realtimechat.R
import com.example.realtimechat.data.datamanager.RxData
import com.example.realtimechat.data.model.User
import com.example.realtimechat.databinding.FragmentUserProfileBinding
import com.example.realtimechat.instruments.MyActivityResultContract

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {
    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var avm: UserProfileAVM
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
    private val cropActivityContract: MyActivityResultContract = MyActivityResultContract()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RxData.activityBehaviorRelay.accept(this.requireActivity())
        binding.imageViewAvatar.isEnabled = false
        binding.editTextUserName.isEnabled = false
        binding.loadingSignIn.visibility = VISIBLE

        avm = ViewModelProvider(this).get(UserProfileAVM::class.java)
        avm.checkUser()

        binding.buttonQuit.setOnClickListener {
            avm.logOutDialog {
                if (it) {
                    findNavController().navigate(R.id.action_userProfileFragment2_to_signInFragment,
                        bundleOf(), navOptions {
                            launchSingleTop = true
                            popUpTo(R.id.main_graph) {
                                inclusive = true
                            }
                        })
                }
            }
        }
        binding.buttonChangePassword.setOnClickListener {
            avm.passwordChange()
        }
        binding.imageViewAvatar.setOnClickListener {
            cropActivityResultLauncher.launch(null)
        }
        binding.buttonChangeUserInfo.setOnClickListener {
            binding.linearLayoutButtons.visibility = GONE
            binding.buttonSave.visibility = VISIBLE
            binding.buttonCancel.visibility = VISIBLE
            binding.imageViewAvatar.isEnabled = true
            binding.editTextUserName.isEnabled = true
        }
        binding.buttonCancel.setOnClickListener {
            avm.checkName(binding.editTextUserName.text.toString())
            binding.linearLayoutButtons.visibility = VISIBLE
            binding.buttonSave.visibility = GONE
            binding.buttonCancel.visibility = GONE
            binding.imageViewAvatar.isEnabled = false
            binding.editTextUserName.isEnabled = false
        }
        cropActivityResultLauncher = registerForActivityResult(cropActivityContract) {
            avm.sendImage(it, binding.imageViewAvatar)
        }
    }

    override fun onStart() {
        super.onStart()
        RxData.activityBehaviorRelay.accept(this.requireActivity())
    }

    override fun onResume() {
        super.onResume()
        RxData.activityBehaviorRelay.accept(this.requireActivity())
        avm.updateAvatar(binding.imageViewAvatar) { }
        avm.userInfo.observe(this) { user: User ->
            binding.editTextUserEmail.setText(user.email)
            binding.editTextUserName.setText(user.name)
            binding.loadingSignIn.visibility = GONE
        }
    }
}