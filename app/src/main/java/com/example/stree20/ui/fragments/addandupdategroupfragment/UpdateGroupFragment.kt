package com.example.stree20.ui.fragments.addandupdategroupfragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stree20.R
import com.example.stree20.data.local.StreeItem
import com.example.stree20.utils.extensions.toast

class UpdateGroupFragment : FragmentGroupManager() {

    private val args by navArgs<UpdateGroupFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            args.streeItem.run {
                etGroupName.setText(groupName)
                etSmsSource.setText(source)
                spText.setText(channel)
            }

            btnCreateGroup.run {
                text = context.getString(R.string.updateGroup)
                setOnClickListener {
                    updateItem()
                }
            }
        }
    }

    private fun updateItem() {

        binding.apply {
            val groupName = etGroupName.text.toString()
            val smsSource = etSmsSource.text.toString()
            val channelName = spText.text.toString()

            if (inputCheck(groupName, smsSource, channelName)) {
                val updatedItem =
                    StreeItem(
                        groupName,
                        smsSource,
                        channelName,
                        args.streeItem.id
                    )
                viewModel.updateStreeItem(updatedItem)

                toast(getString(R.string.updated_group))
                findNavController().navigateUp()

            } else {
                toast(getString(R.string.fill_out))
            }
        }
    }

    private fun inputCheck(itemName: String, itemAmount: String, itemPrice: String): Boolean {
        return !(itemName.isEmpty() || itemAmount.isEmpty() || itemPrice.isEmpty())
    }


}