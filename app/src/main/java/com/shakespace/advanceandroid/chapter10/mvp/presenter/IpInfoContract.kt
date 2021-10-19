package com.shakespace.advanceandroid.chapter10.mvp.presenter

import com.shakespace.advanceandroid.chapter10.mvp.model.IpInfo
import com.shakespace.advanceandroid.chapter10.mvp.ui.BaseView

interface IpInfoContract {
    interface Presenter {
        fun getIpInfo(ip: String)
    }

    interface View : BaseView<Presenter?> {
        fun setIpInfo(ipInfo: IpInfo?)
        fun showLoading()
        fun hideLoading()
        fun showError(e:Throwable)
        val isActive: Boolean
    }
}