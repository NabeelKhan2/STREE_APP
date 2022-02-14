package com.example.stree20.ui.fragments.settingfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.stree20.R
import com.example.stree20.databinding.FragmentSettingBinding

class SettingFragment : Fragment(R.layout.fragment_setting) {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}