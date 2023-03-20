package com.premiumgrocery.freshandfast.utils

import android.app.AlertDialog
import android.content.Context

fun ConfirmAlert(
    context: Context,
    titleText: String = "",
    messageText: String = "Are you sure?",
    positiveText: String = "Confirm",
    negativeText: String = "Cancel",
    onConfirm: () -> Unit
): AlertDialog = AlertDialog.Builder(context)
    .setTitle(titleText)
    .setMessage(messageText)
    .setPositiveButton(positiveText) { _,_-> onConfirm() }
    .setNegativeButton(negativeText) { _,_-> }
    .show()