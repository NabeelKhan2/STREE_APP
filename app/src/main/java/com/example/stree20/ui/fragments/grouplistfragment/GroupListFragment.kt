package com.example.stree20.ui.fragments.grouplistfragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.stree20.R
import com.example.stree20.databinding.FragmentGroupListBinding
import com.example.stree20.utils.extensions.toast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GroupListFragment : Fragment(R.layout.fragment_group_list) {

    private var _binding: FragmentGroupListBinding? = null
    private val binding get() = _binding!!

    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
            if (!permission) {
                toast(getString(R.string.validate_permission_msg))
            }
        }

    val groupsAdapter = GroupsAdapter()
    private val viewModel by viewModels<GroupFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGroupListBinding.bind(view)

        binding.apply {
            btnFabGroup.setOnClickListener {
                findNavController().navigate(
                    GroupListFragmentDirections.actionGroupListFragmentToAddGroupFragment()
                )
            }

            toolbar.also { toolbar ->
                toolbar.inflateMenu(R.menu.app_bar_menu)
                toolbar.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.miSetting -> findNavController().navigate(
                            R.id.action_groupListFragment_to_settingFragment
                        )
                    }
                    true
                }
            }
        }

        updateOrRequestPermissions()
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
            Snackbar.make(
                requireView(),
                getString(R.string.successfully_deleted),
                Snackbar.LENGTH_LONG
            ).apply {
                setAction(getString(R.string.undo)) {
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
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }

    private fun updateOrRequestPermissions() {
        val hasSmsPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED

        if (hasSmsPermission.not()) {
            permissionsLauncher.launch(Manifest.permission.RECEIVE_SMS)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}