package com.shakespace.advanceandroid.chapter09annotation.dagger.binds

import javax.inject.Inject

class Worker @Inject constructor() : People() {
}