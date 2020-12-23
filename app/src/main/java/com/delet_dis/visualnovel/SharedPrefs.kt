package com.delet_dis.visualnovel

import android.content.Context
import android.content.SharedPreferences

object SharedPrefs {

  private fun getSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(Constants.appSettings, Context.MODE_PRIVATE)
  }

  fun getValue(context: Context, setting: String): String? {
    return getSharedPreferences(context).getString(setting, null)
  }

  fun clearValues(context: Context) {
    val editor = getSharedPreferences(context).edit()
    editor.clear()
    editor.apply()
  }

  fun setValue(context: Context, setting: String, newValue: String?) {
    val editor = getSharedPreferences(context).edit()
    editor.putString(setting, newValue)
    editor.apply()
  }
}