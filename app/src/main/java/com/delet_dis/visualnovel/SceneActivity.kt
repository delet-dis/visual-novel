package com.delet_dis.visualnovel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_scene.*


class SceneActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scene)

    val numberOfScene = Integer.parseInt(intent.getStringExtra(Constants.currentScene) ?: "0")

    SharedPrefs.setValue(applicationContext, Constants.savedNumberOfScene, numberOfScene.toString())

    val scenes = JSONHelper.parseJSON(applicationContext, Constants.separateFlag)

    scenes.forEach { Scene ->
      if (Scene.id == numberOfScene) {

        textHeader.text =
          if (numberOfScene == 3) {
            SharedPrefs.getValue(applicationContext, Constants.playerName)
              ?.let {
                Scene.header.replace(
                  "%s",
                  it
                )
              }
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
  }

  override fun onBackPressed() = Unit
}

