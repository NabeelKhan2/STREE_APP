package com.example.stree20.ui.fragments.grouplistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stree20.R
import com.example.stree20.databinding.FragmentGroupListBinding

class GroupListFragment : Fragment(R.layout.fragment_group_list) {

    private var _binding: FragmentGroupListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGroupListBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}