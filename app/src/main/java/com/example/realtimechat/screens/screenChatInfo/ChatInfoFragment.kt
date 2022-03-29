package com.example.realtimechat.screens.screenChatInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.realtimechat.R
import com.example.realtimechat.databinding.FragmentChatInfoBinding

class ChatInfoFragment : Fragment(R.layout.fragment_chat_info) {
    private lateinit var binding: FragmentChatInfoBinding
    private lateinit var vm: ChatInfoAVM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this).get(ChatInfoAVM::class.java)
        vm.initRecyclerView(binding.usersRecyclerView) {
            binding.loadingLayoutUsers.visibility = GONE
            if (it is Bundle) {
                findNavController().navigate(R.id.action_chatInfoFragment_to_userInfoFragment, it)
            } else if (it is Boolean) {
                binding.loadingLayoutUsers.visibility = GONE
            }
        }


        binding.buttonGoBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    override fun onPause() {
        super.onPause()
        vm.clearRecyclerView()
    }
}