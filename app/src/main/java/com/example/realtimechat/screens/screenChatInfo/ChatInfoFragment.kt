package com.example.realtimechat.screens.screenChatInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realtimechat.R
import com.example.realtimechat.databinding.FragmentChatInfoBinding

class ChatInfoFragment : Fragment(R.layout.fragment_chat_info) {
    private lateinit var adapter: UsersAdapter
    private lateinit var binding: FragmentChatInfoBinding
    private lateinit var vm: ChatInfoVM

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

        vm = ViewModelProvider(this).get(ChatInfoVM::class.java)

        val linearLayoutManager = LinearLayoutManager(context)
        binding.usersRecyclerView.layoutManager = linearLayoutManager

        adapter = UsersAdapter { user, _ ->
            val bundle = Bundle()
            bundle.putString("uid", user.getUid())
            findNavController().navigate(R.id.action_chatInfoFragment_to_userInfoFragment, bundle)
        }
        binding.usersRecyclerView.adapter = adapter

        vm.usersLiveData.observe(this.viewLifecycleOwner) {
            adapter.updateUsersList(it)
            binding.loadingLayoutUsers.visibility = GONE
        }
        binding.buttonGoBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}