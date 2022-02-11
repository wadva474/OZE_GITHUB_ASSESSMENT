package com.example.github.base

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.github.R
import com.example.github.networkutils.LoadingStatus
import com.example.github.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment : Fragment() {


    protected val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }


    override fun onStart() {
        super.onStart()
        if (getViewModel().status.hasObservers()) return
        getViewModel().status.observe(viewLifecycleOwner) {
            when (it) {
                LoadingStatus.Success -> {
                    showSnackBar(getString(R.string.removed_from_favourites))
                    getViewModel().errorShown()
                }
                is LoadingStatus.Error -> {
                    mainActivity.showError(it.errorMessage)
                    getViewModel().errorShown()
                }
            }
        }
    }

    abstract fun getViewModel(): BaseViewModel

    fun showDialogWithAction(
        title: String? = null,
        body: String? = null,
        @StringRes positiveRes: Int = R.string.ok,
        positiveAction: (() -> Unit)? = null,
        @StringRes negativeRes: Int? = null,
        negativeAction: (() -> Unit)? = null,
        cancelOnTouchOutside: Boolean = true,
        autoDismiss: Boolean = true
    ) = mainActivity.showDialogWithAction(
        title,
        body,
        positiveRes,
        positiveAction,
        negativeRes,
        negativeAction,
        cancelOnTouchOutside,
        autoDismiss
    )

    protected fun showSnackBar(@StringRes stringRes: Int) = mainActivity.showSnackBar(getString(stringRes))

    protected fun showSnackBar(message: String) = mainActivity.showSnackBar(message)

    protected fun showToast(message: String) = mainActivity.showToast(message)

    protected fun showToast(@StringRes stringRes: Int) = mainActivity.showToast(getString(stringRes))

    // Return true if you handle the back press in your fragment
    open fun onBackPressed(): Boolean = false
}
