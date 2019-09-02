package io.github.rosariopfernandes.rollapass

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.github.rosariopfernandes.rollapass.adapter.AccountAdapter
import io.github.rosariopfernandes.rollapass.adapter.RecyclerItemClickListener
import io.github.rosariopfernandes.rollapass.fragment.AccountDialogFragment
import io.github.rosariopfernandes.rollapass.model.Account
import io.github.rosariopfernandes.rollapass.model.User
import io.github.rosariopfernandes.rollapass.room.PassDatabase
import io.github.rosariopfernandes.rollapass.viewmodel.AccountsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    private val accountAdapter = AccountAdapter(null)
    private var accountsList = ArrayList<Account>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        fab.setOnClickListener {
            val intent = Intent(this, NewAccountActivity::class.java)
            startActivity(intent)
        }

        accountsRecyclerView.layoutManager = LinearLayoutManager(this)
        accountsRecyclerView.adapter = accountAdapter
        accountsRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if(dy > 0)
                    fab.hide()
                else
                    fab.show()
            }
        })
        accountsRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(accountsRecyclerView,
                object: RecyclerItemClickListener.OnItemClickListener{

                    override fun onItemClick(view: View, position: Int) {
                        val accountId = accountsList[position].accountId!!
                        val intent = Intent(this@MainActivity,
                                AccountActivity::class.java)
                        intent.putExtra("accountId", accountId)
                        startActivity(intent)
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        val accountId = accountsList[position].accountId!!
                        val accountDialog = AccountDialogFragment.newInstance(accountId)
                        accountDialog.show(supportFragmentManager, "Dialog")
                    }
                }))

        ViewModelProviders.of(this).get(AccountsViewModel::class.java)
                .accounts?.observe(this, Observer { accountList ->
            this.accountsList = accountList as ArrayList<Account>
            accountAdapter.accountsList = accountList
            accountAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val view = layoutInflater.inflate(R.layout.dialog_change_password, null)
                alert {
                    title = getString(R.string.title_activity_master_password)
                    customView = view
                    negativeButton(R.string.action_cancel, {

                    })
                    positiveButton(R.string.action_confirm, {
                        val txtCurrentPassword = view.findViewById<TextInputLayout>(
                                R.id.txtCurrentPassword).editText
                        val txtNewPassword = view.findViewById<TextInputLayout>(
                                R.id.txtNewPassword).editText
                        val txtConfirmPassword = view.findViewById<TextInputLayout>(
                                R.id.txtConfirmPassword).editText

                        val newPassword = txtNewPassword?.text.toString()

                        if (newPassword == txtConfirmPassword?.text.toString()) {
                            doAsync {
                                val database = PassDatabase.getInstance(this@MainActivity)
                                val user = User(1, newPassword)
                                database.userDao().updateUser(user)
                                uiThread {
                                    Snackbar.make(currentFocus,
                                            R.string.confirmation_password_changed,
                                            Snackbar.LENGTH_LONG).show()
                                }
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
