package com.example.realtimechat.screens.screenUserInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.realtimechat.R
import com.example.realtimechat.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment(R.layout.fragment_user_info) {
    private lateinit var vm: UserInfoVM
    private lateinit var binding: FragmentUserInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this).get(UserInfoVM::class.java)

        binding.buttonGoBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        vm.readUser(requireArguments().get("uid").toString(),binding.imageViewUserIAvatar){
            binding.loadingLayout.visibility=GONE
        }
        binding.loadingLayout.visibility=GONE
        vm.userInfo.observe(this){
            binding.textViewUserInfoEmail.text = it.email
            binding.textViewUserInfoName.text = it.name
            binding.textViewUserOnline.text = it.status

        }
    }
}