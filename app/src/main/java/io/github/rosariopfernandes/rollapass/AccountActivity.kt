package io.github.rosariopfernandes.rollapass

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.rosariopfernandes.rollapass.model.Account
import io.github.rosariopfernandes.rollapass.room.AccountViewModelFactory
import io.github.rosariopfernandes.rollapass.room.PassDatabase
import io.github.rosariopfernandes.rollapass.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.content_account.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class AccountActivity : AppCompatActivity() {
    var account: Account? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val accountId = intent.extras!!.getInt("accountId")
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        ViewModelProviders.of(this, AccountViewModelFactory(application, accountId))
                .get(AccountViewModel::class.java)
                .account?.observe(this, Observer { account ->
            //toolbar.title = account?.userWebsite
            this.account = account
            account?.let {
                txtWebsite.editText?.setText(account.userWebsite)
                txtUsername.editText?.setText(account.userName)
                txtPassword.editText?.setText(account.userPassword)
                btnCopyUsername.setOnClickListener {
                    val clip = ClipData.newPlainText("username", account.userName)
                    clipboard.setPrimaryClip(clip)
                }
                btnCopyPassword.setOnClickListener {
                    val clip = ClipData.newPlainText("password", account.userPassword)
                    clipboard.setPrimaryClip(clip)
                }
                btnBrowse.setOnClickListener {
                    val webpage = Uri.parse("http://${account.userWebsite}")
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_account, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this@AccountActivity,
                        NewAccountActivity::class.java)
                intent.putExtra("accountId", account?.accountId)
                startActivity(intent)
                true
            }
            R.id.action_delete -> {
                alert{
                    title = getString(R.string.title_delete)
                    positiveButton(R.string.action_cancel, {
                    })
                    negativeButton(R.string.action_delete, {
                        val database = PassDatabase.getInstance(this@AccountActivity)
                        doAsync {
                            database?.accountDao()?.deleteAccount(account!!)
                            uiThread {
                                finish()
                            }
                        }
                    })
                }.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
