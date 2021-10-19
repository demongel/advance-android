package com.shakespace.advanceandroid.chapter10.mvp.presenter

import com.shakespace.advanceandroid.chapter10.mvp.api.ipService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IpInfoPresenter(val view: IpInfoContract.View) : IpInfoContract.Presenter {

    override fun getIpInfo(ip: String) {
        val subscribe = ipService.getIp(ip)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) {
                    view.setIpInfo(it)
                }
            }, { error ->
                view.showError(error)
            }
            )
    }
}