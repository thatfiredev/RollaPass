package io.github.rosariopfernandes.rollapass

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.SeekBar
import io.github.rosariopfernandes.rollapass.model.Account
import io.github.rosariopfernandes.rollapass.room.AccountViewModelFactory
import io.github.rosariopfernandes.rollapass.room.PassDatabase
import io.github.rosariopfernandes.rollapass.viewmodel.AccountViewModel
import io.github.rosariopfernandes.rollapass.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_new_account.*
import kotlinx.android.synthetic.main.content_new_account.*
import org.jetbrains.anko.doAsync

class NewAccountActivity : AppCompatActivity() {

    private var characterNumber = 8

    private val characterList1 = listOf('A','B','C','D','E','F',
            'G','H','I','J','K','L','M','N','O','P','Q','R','S',
            'T','U','V','W','X','Y','Z','0','1','2','3','4','5',
            '6','7','8','9')
    private val characterList2 = listOf('a','b','c','d','e','f',
            'g','h','i','j','k','l','m','n','o','p','q','r','s',
            't','u','v','w','x','y','z',' ',' ',' ',' ',' ',' ',
            ' ',' ',' ',' ')
    private val characterList3 = listOf('!','@','#','$','%','^',
            '&','*','(',')','-','=','+','[',']','{','}',' ','|',
            ' ',';',':',' ','"','<','>','/','?','.',',',' ',' ',
            ' ',' ',' ',' ')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val accountId = intent?.extras?.getInt("accountId")
        accountId?.let {
            ViewModelProviders.of(this, AccountViewModelFactory(application, accountId))
                    .get(AccountViewModel::class.java)
                    .account?.observe(this, Observer { account ->
                account?.let {
                    txtWebsite.editText?.setText(account.userWebsite)
                    txtUsername.editText?.setText(account.userName)
                    txtPassword.editText?.setText(account.userPassword)
                    supportActionBar?.setTitle(R.string.title_activity_edit_account)
                }
            })
        }

        val database = PassDatabase.getInstance(applicationContext)

        showCharacterNumber()

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                characterNumber = (progress+2) * 4
                showCharacterNumber()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        fab.setOnClickListener { _ ->

            val accountWebsite = txtWebsite.editText!!.text.toString()
            val accountUsername = txtUsername.editText!!.text.toString()
            val accountPassword = txtPassword.editText!!.text.toString()

            val account = Account(accountId, accountUsername, accountPassword, accountWebsite)

            doAsync {
                database.accountDao().createAccount(account)
                runOnUiThread {
                    finish()
                }
            }
        }

        ViewModelProviders.of(this).get(WordViewModel::class.java)
                .wordLiveData?.observe(this, Observer { wordList ->
            val map = HashMap<Int, String>()
            if (wordList != null) {
                for (word in wordList) {
                    map[word.id!!] = word.word
                }
                btnGenerate.setOnClickListener {
                    txtPassword.editText?.setText(generatePassword(map))
                }
            }
        })

    }

    private fun diceRoll(): Int {
        val dice = Math.random() * 6 + 1
        return dice.toInt()
    }

    private fun getFiveRolls(): Int {
        return "${diceRoll()}${diceRoll()}${diceRoll()}${diceRoll()}${diceRoll()}".toInt()
    }

    private fun generatePassword(map: HashMap<Int, String>): String {
        var password = map[getFiveRolls()]!!
        while (password.length > characterNumber || password.length < characterNumber/2) {
            password = when (characterNumber) {
                8 -> "${map[getFiveRolls()]!!}${map[getFiveRolls()]!!}"
                12 -> "${map[getFiveRolls()]!!}${map[getFiveRolls()]!!}${map[getFiveRolls()]!!}"
                16 -> "${map[getFiveRolls()]!!}${map[getFiveRolls()]!!}" +
                        "${map[getFiveRolls()]!!}${map[getFiveRolls()]!!}"
                else -> "${map[getFiveRolls()]!!}${map[getFiveRolls()]!!}" +
                        "${map[getFiveRolls()]!!}${map[getFiveRolls()]!!}${map[getFiveRolls()]!!}"
            }
        }
        while(password.length < characterNumber)
            password += getRandomCharacter()
        return password
    }

    private fun showCharacterNumber() {
        txtCharacterNumber.text = getString(R.string.character_number, characterNumber)
    }

    private fun getRandomCharacter(): Char {
        val firstRoll = diceRoll()
        var secondRoll = diceRoll() - 1
        var thirdRoll = diceRoll() - 1

        var index = thirdRoll * 6 + secondRoll

        var randomChar = when (firstRoll) {
            1 -> characterList1[index]
            2 -> characterList1[index]
            3 -> characterList2[index]
            4 -> characterList2[index]
            5 -> characterList3[index]
            else -> characterList3[index]
        }

        while(randomChar == ' ') {
            secondRoll = diceRoll() - 1
            thirdRoll = diceRoll() - 1
            index = thirdRoll * 6 + secondRoll
            randomChar = characterList1[index]
        }

        return randomChar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home ->{
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
