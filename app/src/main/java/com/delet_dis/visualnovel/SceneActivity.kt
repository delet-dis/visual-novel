package com.delet_dis.visualnovel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_scene.*
import java.io.InputStream
import java.nio.charset.Charset


class SceneActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scene)

    val numberOfScene = Integer.parseInt(intent.getStringExtra(Constants.currentScene) ?: "0")

//    val jsonArray =
//      JSONObject(loadJSONFromAsset(applicationContext) ?: "{}").getJSONArray(Constants.separateFlag)

    SharedPrefs.setValue(applicationContext, Constants.savedNumberOfScene, numberOfScene.toString())

    val scenes = JSONHelper.parseJSON(applicationContext, Constants.separateFlag)

    scenes.forEach { Scene ->
      if(Scene.id==numberOfScene){

        textHeader.text =
          if (numberOfScene == 3) SharedPrefs.getValue(applicationContext, Constants.playerName)
            ?.let {
              Scene.header.replace(
                "%s",
                it
              )
            } else Scene.header

        backgroundImage.setImageResource(
          resources.getIdentifier(
            Scene.backgroundPath,
            "drawable",
            packageName
          )
        )

        Scene.arrayOfVariants.forEach { processingVariant ->
          val nextId = processingVariant.nextId

          val button: MaterialButton =
            layoutInflater.inflate(
              R.layout.scene_button,
              sceneConstraintLayout,
              false
            ) as MaterialButton
          button.text = processingVariant.variantText
          button.setOnClickListener {
            val comeToNextActivity =
              Intent(
                this, if (nextId == 1) {
                  SharedPrefs.clearValues(applicationContext)
                  MainActivity::class.java
                } else SceneActivity::class.java
              )

            comeToNextActivity.putExtra(Constants.currentScene, nextId.toString())
            startActivity(comeToNextActivity)
            finish()
          }
          buttonsLayout.addView(button)
        }
      }
    }

//    SharedPrefs.setValue(applicationContext, Constants.savedNumberOfScene, numberOfScene.toString())

//    for (i in 0 until jsonArray.length()) {
//      var processingScene: Scene
//      val jsonObject: JSONObject = jsonArray.getJSONObject(i)
//      val id = Integer.parseInt(jsonObject.getString("id"))
//      if (id == numberOfScene) {
//        val convertedElement: JsonElement =
//          Gson().fromJson(jsonObject.toString(), JsonElement::class.java)
//        processingScene = Gson().fromJson(convertedElement, Scene::class.java)
//
//
//        textHeader.text =
//          if (numberOfScene == 3) SharedPrefs.getValue(applicationContext, Constants.playerName)
//            ?.let {
//              processingScene.header.replace(
//                "%s",
//                it
//              )
//            } else processingScene.header
//
//        backgroundImage.setImageResource(
//          resources.getIdentifier(
//            processingScene.backgroundPath,
//            "drawable",
//            packageName
//          )
//        )
//
//        processingScene.arrayOfVariants.forEach {
//          val nextId = it.nextId
//
//          val button: MaterialButton =
//            layoutInflater.inflate(
//              R.layout.scene_button,
//              sceneConstraintLayout,
//              false
//            ) as MaterialButton
//          button.text = it.variantText
//          button.setOnClickListener {
//            val comeToNextActivity =
//              Intent(
//                this, if (nextId == 1) {
//                  SharedPrefs.clearValues(applicationContext)
//                  MainActivity::class.java
//                } else SceneActivity::class.java
//              )
//
//            comeToNextActivity.putExtra(Constants.currentScene, nextId.toString())
//            startActivity(comeToNextActivity)
//            finish()
//          }
//          buttonsLayout.addView(button)
//        }
//      }
//    }
  }

  override fun onBackPressed() = Unit

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
      Toast.makeText(mContext, getString(R.string.jsonParsingError), Toast.LENGTH_SHORT).show()
    }
    return json
  }
}

