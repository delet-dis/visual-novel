package com.delet_dis.visualnovel

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import org.json.JSONObject
import java.io.InputStream
import java.nio.charset.Charset

object JSONHelper {

  fun parseJSON(context: Context, separatorName: String): List<Scene> {
    val jsonArray = JSONObject(loadJSONFromAsset(context) ?: "").getJSONArray(separatorName)
    return Gson().fromJson(
      jsonArray.toString(),
      Array<Scene>::class.java
    ).toList()
  }

  private fun loadJSONFromAsset(mContext: Context): String? {
    var json: String? = null
    try {
      val inputStream: InputStream = mContext.assets.open(Constants.scenesFilename)
      val size: Int = inputStream.available()
      val buffer = ByteArray(size)
      inputStream.read(buffer)
      inputStream.close()
      json = String(buffer, Charset.forName("UTF-8"))
    } catch (e: Exception) {
      Toast.makeText(mContext, "JSON parsing error", Toast.LENGTH_SHORT).show()
    }
    return json
  }

}