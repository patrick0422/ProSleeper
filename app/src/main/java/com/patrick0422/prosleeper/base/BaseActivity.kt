package com.patrick0422.prosleeper.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : AppCompatActivity() {
    val binding: B by lazy { DataBindingUtil.setContentView(this, layoutResId) }
    private var waitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@BaseActivity

        init()
    }

    abstract fun init()

    override fun onBackPressed() {
        if (System.currentTimeMillis() - waitTime >= 1500) {
            waitTime = System.currentTimeMillis()
            makeToast("한 번 더 눌러 종료합니다")
        } else finish()
    }

    fun makeToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}