package io.github.rosariopfernandes.rollapass

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_master_password.*
import kotlinx.android.synthetic.main.content_master_password.*

class MasterPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_password)
        setSupportActionBar(toolbar)

        btnNext.setOnClickListener {
            val pwd = txtMasterPassword.text.toString()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
