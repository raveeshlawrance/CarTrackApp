package com.cartrack.main.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cartrack.main.storage.CarTrackKeystore
import com.cartrack.main.utils.Constants

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {
    lateinit var carTrackKeystore : CarTrackKeystore
    lateinit var mTextViewScreenTitle: TextView
    lateinit var mImageButtonBack: ImageButton
    lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.layoutId())
        carTrackKeystore = CarTrackKeystore(applicationContext)
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Loading")
        mProgressDialog.setCancelable(false)
        mProgressDialog.isIndeterminate = true

    }

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutId()) as DB
    }

    fun setScreenTitle(resId: Int) {
        mTextViewScreenTitle.text = getString(resId)
    }

    fun setScreenTitle(title: String) {
        mTextViewScreenTitle.text = title
    }

    fun getBackButton(): ImageButton {
        return mImageButtonBack;
    }

    fun showProgressDialog() {
        if(!mProgressDialog.isShowing) {
            mProgressDialog.show()
        }
    }

    fun dismissProgressDialog() {
        if (mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }
    fun showAlert(title : String, body : String, alertClickListener: AlertClickListener?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(body)
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            alertClickListener?.onPositiveClickListener()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            alertClickListener?.onNegativeClickListener()
        }
        builder.show()


    }
    fun showAlert(title : String, body : String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(body)
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()


    }
    interface AlertClickListener {
        fun onPositiveClickListener();
        fun onNegativeClickListener();
    }

    @LayoutRes
    abstract fun layoutId() : Int
}