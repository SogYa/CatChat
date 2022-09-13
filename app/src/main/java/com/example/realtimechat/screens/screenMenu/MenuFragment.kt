package com.example.realtimechat.screens.screenMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realtimechat.R
import com.example.realtimechat.data.model.MenuItem
import com.example.realtimechat.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu), MenuAdapter.OnMenuItemClickListener {
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var binding: FragmentMenuBinding
    private lateinit var vm: MenuVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(this).get(MenuVM::class.java)
        menuAdapter = MenuAdapter(this)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.menuRecyclerview.layoutManager = linearLayoutManager
        binding.menuRecyclerview.adapter = menuAdapter
    }

    override fun onStart() {
        super.onStart()
        vm.getMenuLiveData().observe(this.viewLifecycleOwner) {
            menuAdapter.updateMenuList(it)
        }
    }

    override fun onClick(menuItem: MenuItem, position: Int) {
        when (menuItem.title) {
            "Общий чат" -> findNavController().navigate(R.id.action_menuFragment_to_chatFragment2)
        }
    }
}