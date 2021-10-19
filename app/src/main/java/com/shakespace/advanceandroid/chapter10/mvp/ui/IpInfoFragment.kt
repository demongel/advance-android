package com.shakespace.advanceandroid.chapter10.mvp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.chapter10.mvp.model.IpInfo
import com.shakespace.advanceandroid.chapter10.mvp.presenter.IpInfoContract
import com.shakespace.advanceandroid.chapter10.mvp.presenter.IpInfoPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_ip_info.*
import org.greenrobot.eventbus.EventBus


private const val TAG = "IpInfoFragment"

class IpInfoFragment() : Fragment(), IpInfoContract.View {

    lateinit var disposable: Disposable
    lateinit var ipInfoPresenter: IpInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ipInfoPresenter = IpInfoPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ip_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_get_ip.setOnClickListener {
            ipInfoPresenter.getIpInfo("39.155.184.147")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        if (this::disposable.isInitialized && disposable != null) {
            disposable.dispose()
        }
    }

    override fun setIpInfo(ipInfo: IpInfo?) {
        Log.e(TAG, "setIpInfo: ${ipInfo.toString()}")
    }

    override fun showLoading() {
        Log.e(TAG, "showLoading: ")
    }

    override fun hideLoading() {
        Log.e(TAG, "hideLoading: ")
    }

    override fun showError(e: Throwable) {
        Log.e(TAG, "showError: ${e.message}")
    }

    override val isActive: Boolean
        get() = isAdded


}