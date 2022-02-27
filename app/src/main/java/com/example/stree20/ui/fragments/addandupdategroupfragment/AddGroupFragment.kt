package com.example.stree20.ui.fragments.addandupdategroupfragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.stree20.R
import com.example.stree20.utils.extensions.alert
import com.example.stree20.utils.extensions.negativeButton
import com.example.stree20.utils.extensions.positiveButton

class AddGroupFragment : FragmentGroupManager() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = viewModel.getToken()

        if (token == null) {
            binding.spText.apply {
                setOnClickListener {
                    alert {
                        setTitle(context.getString(R.string.authentication))
                        setMessage(context.getString(R.string.integeration_missing))
                        positiveButton(context.getString(R.string.setup)) {
                            findNavController().navigate(
                                AddGroupFragmentDirections.actionAddGroupFragmentToSettingFragment()
                            )
                        }
                        negativeButton(getString(R.string.cancel)) {}
                    }
                }
            }
        }

        binding.apply {

            btnCreateGroup.setOnClickListener {
                viewModel.insertStreeItem(
                    etGroupName.text.toString(),
                    etSmsSource.text.toString(),
                    spText.text.toString()
                )
            }
        }
    }
}