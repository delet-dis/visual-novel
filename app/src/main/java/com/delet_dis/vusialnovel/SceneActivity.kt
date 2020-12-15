package com.delet_dis.vusialnovel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_scene.*
import org.json.JSONArray
import org.json.JSONObject


class SceneActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scene)

    val numberOfScene = Integer.parseInt(intent.getStringExtra("currentScene"))

    val jsonArray = JSONArray(this.assets.open("Scenes.json"))

    var processingScene: Scene

    for (i in 0 until jsonArray.length()) {
      val jsonObject: JSONObject = jsonArray.getJSONObject(i)
      val id = Integer.parseInt(jsonObject.getString("id"))
      if (id == numberOfScene) {
        val convertedElement: JsonElement =
          Gson().fromJson(jsonObject.toString(), JsonElement::class.java)
        processingScene = Gson().fromJson(convertedElement, Scene::class.java)

        textHeader.text = processingScene.header
      }
    }

//    textHeader.text = getString(
//      R.string.catWelcomeTextNamed,
//      intent.getStringExtra("playerName")
//    )

  }
}

