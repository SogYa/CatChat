package com.example.realtimechat.screens.screenChat

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimechat.R
import com.example.realtimechat.databinding.FragmentChatBinding

class ChatFragment : Fragment(R.layout.fragment_chat) {
    private lateinit var messageEditText: EditText
    private lateinit var vm: ChatAVM
    private lateinit var loadingLayout: ConstraintLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: FragmentChatBinding

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.chatRecyclerView)
        loadingLayout = view.findViewById(R.id.loading)
        messageEditText = view.findViewById(R.id.editTextMessage)

        vm = ViewModelProvider(this).get(ChatAVM::class.java)
        vm.initRecyclerView(recyclerView) {
            loadingLayout.visibility = View.GONE
        }

        binding.buttonProfile.setOnClickListener {
            findNavController().navigate(R.id.action_chatFragment2_to_userProfileFragment2)
        }
        binding.chatInfoBar.setOnClickListener {
            findNavController().navigate(R.id.action_chatFragment2_to_chatInfoFragment)
        }
        binding.buttonSendMessage.setOnClickListener {
            vm.sendMessage(messageEditText.text.toString())
            messageEditText.setText("")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vm.activityStatus(true)
    }

    override fun onStop() {
        super.onStop()
        vm.activityStatus(false)
    }
}