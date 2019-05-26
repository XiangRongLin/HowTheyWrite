package com.kaiserpudding.howtheywrite.shared

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmationDialogFragment(private val listener: ConfirmationDialogListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Are you sure you want to delete selected items?")
                .setPositiveButton("Delete") { _, _ ->
                    listener.onDialogPositiveClick()
                }
                .setNegativeButton("Cancel") { _, _ ->
                    listener.onDialogNegativeClick()
                }
        return builder.create()
    }

    interface ConfirmationDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try {
//            listener = context as ConfirmationDialogListener
//        } catch (e: ClassCastException) {
//            throw ClassCastException("$context must implement ConfirmationDialogFragment")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }
}