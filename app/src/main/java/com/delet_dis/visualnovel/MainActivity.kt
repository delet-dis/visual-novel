package com.delet_dis.visualnovel

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val sPref: SharedPreferences = getSharedPreferences(Constants.appSettings, MODE_PRIVATE)

    if (sPref.contains(Constants.savedNumberOfScene)) {
      val comeToSceneActivity = Intent(this, SceneActivity::class.java)
      comeToSceneActivity.putExtra(
        Constants.currentScene,
        sPref.getString(Constants.savedNumberOfScene, "")
      )
      startActivity(comeToSceneActivity)
      finish()
    }

    startButton.setOnClickListener {
      val comeToNameInputActivityIntent = Intent(this, AcquaintanceActivity::class.java)
      startActivity(comeToNameInputActivityIntent)
    }
  }

}