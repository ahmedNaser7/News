package com.example.news.ui

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment

// for show the message of failure or response
fun Fragment.showMessage(
    message: String,
    posActionName: String? = null,
    posAction: DialogInterface.OnClickListener,
    negActionName: String? = null,
    negAction: DialogInterface.OnClickListener,
): AlertDialog {
    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setMessage(message)
    if (posActionName != null) {
        dialogBuilder.setPositiveButton(posActionName, posAction)
    }
    if (negActionName != null) {
        dialogBuilder.setNegativeButton(negActionName, negAction)
    }
    return dialogBuilder.show()
}

