package com.example.stree20.utils.extensions

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.annotation.StyleRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.toast(content: Any = "", length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, content.toString(), length).show()
}


fun Context.alert(
    @StyleRes style: Int = 0,
    dialogBuilder: MaterialAlertDialogBuilder.() -> Unit
) {
    MaterialAlertDialogBuilder(this, style)
        .apply {
            setCancelable(false)
            dialogBuilder()
            create()
            show()
        }
}


fun MaterialAlertDialogBuilder.neutralButton(
    text: String = "OK",
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() }
) {
    this.setNeutralButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}