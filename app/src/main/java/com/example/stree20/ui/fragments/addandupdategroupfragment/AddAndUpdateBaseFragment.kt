package com.example.stree20.ui.fragments.addandupdategroupfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stree20.R
import com.example.stree20.databinding.FragmentAddAndUpdateBaseBinding

abstract class AddAndUpdateBaseFragment : Fragment(R.layout.fragment_add_and_update_base) {

    private var _binding : FragmentAddAndUpdateBaseBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddAndUpdateBaseBinding.bind(view)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}