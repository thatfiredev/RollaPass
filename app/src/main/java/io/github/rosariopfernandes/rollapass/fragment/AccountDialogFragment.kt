package io.github.rosariopfernandes.rollapass.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import io.github.rosariopfernandes.rollapass.R
import io.github.rosariopfernandes.rollapass.model.Account
import io.github.rosariopfernandes.rollapass.room.PassDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AccountDialogFragment : BottomSheetDialogFragment(){

    companion object {
        fun newInstance(idAccount: Int): AccountDialogFragment{
            val fragment = AccountDialogFragment()

            val args = Bundle()
            args.putInt("idAccount", idAccount)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val database = PassDatabase.getInstance(activity!!)
        val bottomSheetDialog = BottomSheetDialog(activity!!)
        val idAccount = arguments!!.getInt("idAccount")

        val view = LayoutInflater.from(activity)
                .inflate(R.layout.fragment_account_dialog, null)
        val btnDelete = view.findViewById<TextView>(R.id.btnDeleteAccount)
        btnDelete.setOnClickListener {
            val account = Account()
            account.accountId = idAccount
            doAsync {

                database!!.accountDao().deleteAccount(account)
                uiThread {
                    Log.e("AccountDialogFragment", "Delted account was $idAccount")
                    bottomSheetDialog.dismiss()
                    Snackbar.make(activity!!.currentFocus, "Deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo", {
                                /*doAsync {
                                    database!!.accountDao().createAccount(account!!)
                                    //TODO: Undo delete
                                }*/
                            })
                            .show()
                }
                /*database!!.accountDao().getAccount(idAccount)
                        .observe(activity!!, Observer { account ->
                            doAsync {
                            }
                            uiThread {

                            }
                        })*/
            }
        }
        bottomSheetDialog.setContentView(view)
        return bottomSheetDialog
    }
}