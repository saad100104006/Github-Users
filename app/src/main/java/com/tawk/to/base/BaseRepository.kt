package com.tawk.to.base

import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseRepository {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    open fun clearResources() {
        compositeDisposable.clear()
    }
}