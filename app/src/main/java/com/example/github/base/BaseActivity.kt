package com.example.github.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.example.github.R
import com.example.github.extensions.hideKeyBoard
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity(), LoadingCallback{

    private var toast: Toast? = null


    override fun showLoading() {
        showLoading(R.string.default_loading_message)
    }

    override fun showLoading(resId: Int) {
        showLoading(getString(resId))
    }

    override fun showError(resId: Int) {
        showError(getString(resId))
    }

    override fun showError(message: String?) {
        hideKeyBoard()
        dismissLoading()
        MaterialDialog(this).show {
            message(text = message)
            positiveButton(R.string.ok)
            cancelOnTouchOutside(false)
        }
    }

    fun showSnackBar(message: String) {
        hideKeyBoard()
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun showDialogWithAction(
        title: String? = null,
        body: String? = null,
        positiveRes: Int,
        positiveAction: (() -> Unit)?,
        negativeRes: Int?,
        negativeAction: (() -> Unit)?,
        cancelOnTouchOutside: Boolean,
        autoDismiss: Boolean
    ) {
        MaterialDialog(this).show {
            if (title != null) title(text = title)
            if (body != null) message(text = body)
            if (negativeRes != null) negativeButton(negativeRes) { negativeAction?.invoke() }
            positiveButton(positiveRes) { positiveAction?.invoke() }
            cancelOnTouchOutside(cancelOnTouchOutside)
            if (!autoDismiss) noAutoDismiss()
        }
    }

}
