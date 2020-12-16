package com.delet_dis.vusialnovel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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

    val numberOfScene = Integer.parseInt(intent.getStringExtra("currentScene"))

    val jsonArray = JSONObject(loadJSONFromAsset(applicationContext)).getJSONArray("scenes")

    var processingScene: Scene

    for (i in 0 until jsonArray.length()) {
      val jsonObject: JSONObject = jsonArray.getJSONObject(i)
      val id = Integer.parseInt(jsonObject.getString("id"))
      if (id == numberOfScene) {

        val convertedElement: JsonElement =
          Gson().fromJson(jsonObject.toString(), JsonElement::class.java)
        processingScene = Gson().fromJson(convertedElement, Scene::class.java)

        textHeader.text = processingScene.header

        textHeader.text = if (numberOfScene == 3) intent.getStringExtra("playerName")?.let {
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
          val btn = Button(this)
          btn.setBackgroundColor(resources.getColor(R.color.secondaryColor))
          btn.isAllCaps = false
          btn.setTextColor(resources.getColor(R.color.fontColor))
          btn.textSize = 17.0f
          btn.text = it.variantText
          btn.textAlignment = View.TEXT_ALIGNMENT_CENTER
          btn.setOnClickListener {
            val comeToNextActivity =
              Intent(this, if (nextId == 1) MainActivity::class.java else SceneActivity::class.java)
            comeToNextActivity.putExtra("currentScene", nextId.toString())
            startActivity(comeToNextActivity)
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
      val `is`: InputStream = mContext.assets.open("Scenes.json")
      val size: Int = `is`.available()
      val buffer = ByteArray(size)
      `is`.read(buffer)
      `is`.close()
      json = String(buffer, Charset.forName("UTF-8"))
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return json
  }
}

