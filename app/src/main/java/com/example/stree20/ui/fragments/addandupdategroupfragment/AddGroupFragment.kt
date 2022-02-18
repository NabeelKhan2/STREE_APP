package com.example.stree20.ui.fragments.addandupdategroupfragment

import android.os.Bundle
import android.view.View

class AddGroupFragment : AddAndUpdateBaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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