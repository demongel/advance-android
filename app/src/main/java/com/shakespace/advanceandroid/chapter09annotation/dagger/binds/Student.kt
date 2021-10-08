package com.shakespace.advanceandroid.chapter09annotation.dagger.binds

import javax.inject.Inject

class Student @Inject constructor() : People() {
}