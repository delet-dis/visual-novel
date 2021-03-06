package com.delet_dis.visualnovel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (SharedPrefs.getValue(applicationContext, Constants.savedNumberOfScene) !== null) {
      val comeToSceneActivity = Intent(this, SceneActivity::class.java)
      comeToSceneActivity.putExtra(
        Constants.currentScene,
        SharedPrefs.getValue(applicationContext, Constants.savedNumberOfScene)
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