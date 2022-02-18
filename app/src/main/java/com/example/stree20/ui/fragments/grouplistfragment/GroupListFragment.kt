package com.example.stree20.ui.fragments.grouplistfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stree20.R
import com.example.stree20.databinding.FragmentGroupListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GroupListFragment : Fragment(R.layout.fragment_group_list) {

    private var _binding: FragmentGroupListBinding? = null
    private val binding get() = _binding!!

    val groupsAdapter = GroupsAdapter()
    private val viewModel by viewModels<GroupFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGroupListBinding.bind(view)

        binding.btnFabGroup.setOnClickListener {
            findNavController().navigate(
                GroupListFragmentDirections.actionGroupListFragmentToAddGroupFragment()
            )
        }

        subscribeToObservers()
        setupRecyclerView()

    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = groupsAdapter.streeItem[pos]
            viewModel.deleteStreeItem(item)
            Snackbar.make(requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel.insertStreeItem(item)
                }
                show()
            }
        }
    }

    private fun subscribeToObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.readStreeGroup.collect { state ->
                when (state) {
                    is GroupFragmentViewModel.State.Success -> {
                        groupsAdapter.streeItem = state.streeGroup
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvGroups.apply {
            adapter = groupsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}