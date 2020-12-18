package com.delet_dis.vusialnovel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_scene.*
import org.json.JSONObject
import java.io.InputStream
import java.nio.charset.Charset


class SceneActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scene)

    val numberOfScene = Integer.parseInt(intent.getStringExtra(Constants.currentScene))

    val jsonArray = JSONObject(loadJSONFromAsset(applicationContext)).getJSONArray("scenes")

    val sPref: SharedPreferences = getSharedPreferences(Constants.appSettings, MODE_PRIVATE)
    val ed: SharedPreferences.Editor = sPref.edit()
    ed.putString(Constants.savedNumberOfScene, numberOfScene.toString())
    ed.apply()

    for (i in 0 until jsonArray.length()) {
      var processingScene: Scene
      val jsonObject: JSONObject = jsonArray.getJSONObject(i)
      val id = Integer.parseInt(jsonObject.getString("id"))
      if (id == numberOfScene) {
        val convertedElement: JsonElement =
          Gson().fromJson(jsonObject.toString(), JsonElement::class.java)
        processingScene = Gson().fromJson(convertedElement, Scene::class.java)

        textHeader.text = if (numberOfScene == 3) sPref.getString(Constants.playerName, "")?.let {
          processingScene.header.replace(
            "%s",
            it
          )
        } else processingScene.header

        backgroundImage.setImageResource(
          resources.getIdentifier(
            processingScene.backgroundPath,
            "drawable",
            packageName
          )
        )

        processingScene.arrayOfVariants.forEach {
          val nextId = it.nextId
          val btn = MaterialButton(this)

          btn.setBackgroundColor(resources.getColor(R.color.secondaryColor))
          btn.isAllCaps = false
          btn.setTextColor(resources.getColor(R.color.fontColor))
          btn.textSize = 17.0f
          btn.text = it.variantText
          btn.textAlignment = View.TEXT_ALIGNMENT_CENTER

          btn.setOnClickListener {
            val comeToNextActivity =
              Intent(
                this, if (nextId == 1) {
                  ed.clear()
                  ed.apply()
                  MainActivity::class.java
                } else SceneActivity::class.java
              )

            comeToNextActivity.putExtra(Constants.currentScene, nextId.toString())
            startActivity(comeToNextActivity)
            finish()
          }
          buttonsLayout.addView(btn)
        }
      }
    }
  }

  override fun onBackPressed() {}

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
      e.printStackTrace()
    }
    return json
  }
}

