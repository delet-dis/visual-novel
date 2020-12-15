package com.delet_dis.vusialnovel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_acquaintance.*

class AcquaintanceActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_acquaintance)

    setPlayerName.setOnClickListener {
      if (playerName.text.toString().isNotEmpty()) {
        val comeToWelcomeActivity = Intent(this, SceneActivity::class.java)
        comeToWelcomeActivity.putExtra("playerName", playerName.text.toString())
        comeToWelcomeActivity.putExtra("currentScene", "3")
        startActivity(comeToWelcomeActivity)
      }
    }
  }

}