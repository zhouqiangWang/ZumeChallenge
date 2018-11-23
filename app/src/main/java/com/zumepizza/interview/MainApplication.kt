package com.zumepizza.interview

import android.app.Application
import android.util.Log
import timber.log.Timber

import timber.log.Timber.DebugTree
import java.lang.AssertionError

/**
 * Created by Joe Wang on 2018/11/22.
 */
class MainApplication: Application() {
  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    } else {
      Timber.plant(CrashReportingTree)
    }
  }
}

private object CrashReportingTree: Timber.Tree() {
  override fun isLoggable(tag: String?, priority: Int): Boolean {
    return priority >= Log.INFO
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    when(priority) {
        Log.ERROR -> Log.e(tag, message, t)
        Log.ASSERT -> throw AssertionError("No instances.")
        Log.WARN -> Log.w(tag, message, t)
        Log.DEBUG -> Log.d(tag, message, t)
        Log.INFO -> Log.i(tag, message, t)
        Log.VERBOSE -> Log.v(tag, message, t)
      }
  }
}