package com.example.stree20.utils.extensions

import android.content.DialogInterface
import android.widget.Toast
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.toast(content: Any = "", length: Int = Toast.LENGTH_SHORT) =
    requireContext().toast(content, length)

fun Fragment.alert(
    @StyleRes style: Int = 0,
    dialogBuilder: MaterialAlertDialogBuilder.() -> Unit
) {
    requireContext().alert(style , dialogBuilder)
}


fun MaterialAlertDialogBuilder.negativeButton(
    text: String = "No",
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() }
) {
    this.setNegativeButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}

fun MaterialAlertDialogBuilder.positiveButton(
    text: String = "Yes",
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() }
) {
    this.setPositiveButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}