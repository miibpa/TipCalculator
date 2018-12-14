package com.example.tipcalculator.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.EditText
import com.example.tipcalculator.R

class SaveDialogFrafment: DialogFragment() {
    interface Callback {
        fun onSaveTip(name: String)
    }

    private var saveTipCallback: Callback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        saveTipCallback = context as? Callback
    }

    override fun onDetach() {
        super.onDetach()
        saveTipCallback = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val saveDialog = context?.let{

            val editText = EditText(it)
            editText.id = viewId
            editText.hint = it.getString(R.string.save_hint)

            AlertDialog.Builder(it)
                .setView(editText)
                .setNegativeButton(android.R.string.cancel,null)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    onSave(editText)
                }
                .create()
        }
        return saveDialog!!
    }

    private fun onSave(editText: EditText){
        val text = editText.text.toString()
        if(text.isNotEmpty()){
            saveTipCallback?.onSaveTip(text)
        }

    }

    companion object {
        val viewId = View.generateViewId()
    }
}