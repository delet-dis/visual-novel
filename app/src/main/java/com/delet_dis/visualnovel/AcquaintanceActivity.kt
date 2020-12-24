package com.delet_dis.visualnovel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_acquaintance.*

class AcquaintanceActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_acquaintance)

    answerVariant.setOnClickListener {
      if (playerName.text.toString().isNotEmpty()) {
        val comeToSceneActivity = Intent(this, SceneActivity::class.java)

        SharedPrefs.setValue(applicationContext, Constants.playerName, playerName.text.toString())

        comeToSceneActivity.putExtra(Constants.currentScene, "3")
        startActivity(comeToSceneActivity)
        finish()
      }
    }
  }

}