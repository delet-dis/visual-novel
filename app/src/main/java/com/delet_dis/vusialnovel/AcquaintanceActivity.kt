package com.delet_dis.vusialnovel

import android.content.Intent
import android.content.SharedPreferences
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
        val sPref: SharedPreferences = getSharedPreferences(Constants.appSettings, MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sPref.edit()
        ed.putString(Constants.playerName, playerName.text.toString())
        ed.apply()
        comeToSceneActivity.putExtra(Constants.currentScene, "3")
        startActivity(comeToSceneActivity)
        finish()
      }
    }
  }

}