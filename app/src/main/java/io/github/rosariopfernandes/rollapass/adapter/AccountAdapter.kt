package io.github.rosariopfernandes.rollapass.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.github.rosariopfernandes.rollapass.R
import io.github.rosariopfernandes.rollapass.model.Account

class AccountAdapter(var accountsList:List<Account>?) :
        RecyclerView.Adapter<AccountAdapter.AccountsViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_account, parent,
                false)
        return AccountsViewHolder(v)
    }

    override fun getItemCount(): Int {
        return accountsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) {
        accountsList?.let { list ->
            val account = list[position]
            holder.txtUsername?.text = account.userName
            holder.txtWebsite?.text = account.userWebsite
        }

    }

    inner class AccountsViewHolder(v:View) : RecyclerView.ViewHolder(v) {
        var txtWebsite: TextView? = null
        var txtUsername: TextView? = null

        init {
            txtWebsite = v.findViewById(R.id.txtWebsite)
            txtUsername = v.findViewById(R.id.txtUsername)
        }
    }
}