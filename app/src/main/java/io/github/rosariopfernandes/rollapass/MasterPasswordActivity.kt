package io.github.rosariopfernandes.rollapass

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.github.rosariopfernandes.rollapass.model.User
import io.github.rosariopfernandes.rollapass.room.PassDatabase
import io.github.rosariopfernandes.rollapass.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_master_password.*
import kotlinx.android.synthetic.main.content_master_password.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MasterPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_password)
        setSupportActionBar(toolbar)

        ViewModelProviders.of(this).get(UserViewModel::class.java)
                .liveData?.observe(this, Observer { user ->
            if (user == null) {
                showMasterPasswordDialog()
            } else {
                btnNext.setOnClickListener { view ->

                    val pwd = txtMasterPassword.editText?.text.toString()

                    if(pwd == user.masterPassword){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Snackbar.make(view, R.string.error_wrong_password,
                                Snackbar.LENGTH_SHORT).show()
                    }

                }

            }
        })

    }

    private fun showMasterPasswordDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_change_password, null)
        val txtCurrentPassword = view.findViewById<TextInputLayout>(
                R.id.txtCurrentPassword)
        txtCurrentPassword?.visibility = View.GONE
        alert {
            title = getString(R.string.title_activity_master_password)
            isCancelable = false
            customView = view
            negativeButton(R.string.action_cancel, {

            })
            positiveButton(R.string.action_confirm, {
                val txtNewPassword = view.findViewById<TextInputLayout>(
                        R.id.txtNewPassword).editText
                val txtConfirmPassword = view.findViewById<TextInputLayout>(
                        R.id.txtConfirmPassword).editText

                val newPassword = txtNewPassword?.text.toString()

                if (newPassword == txtConfirmPassword?.text.toString()) {
                    doAsync {
                        val database = PassDatabase.getInstance(this@MasterPasswordActivity)
                        val user = User(1, newPassword)
                        database.userDao().createUser(user)
                        uiThread {
                            Snackbar.make(currentFocus,
                                    R.string.confirmation_password_saved,
                                    Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            })

        }.show()
    }

}
