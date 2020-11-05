package com.example.nota

import android.app.Activity
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class UpdateNote : AppCompatActivity() {

    private lateinit var editWordView: EditText
    private lateinit var editnumberView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)
        editWordView = findViewById(R.id.edit_word)
        editnumberView = findViewById(R.id.edit_number)

        val intent = intent
        editWordView.setText( intent.getStringExtra(EXTRA_REPLY) )
        editnumberView.setText( intent.getStringExtra(EXTRA1_REPLY) )
        val date= intent.getStringExtra(EXTRA_Date)
        val id = intent.getIntExtra( EXTRA_ID , -1)
        Toast.makeText(this@UpdateNote, "$id", Toast.LENGTH_SHORT).show()
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()

            val titulo = editWordView.text.toString()
            val texto = editnumberView.text.toString()
            if( id != -1 ) {
                replyIntent.putExtra(EXTRA_ID, id)
            }
            replyIntent.putExtra(EXTRA_REPLY, titulo)
            replyIntent.putExtra(EXTRA1_REPLY, texto)
            replyIntent.putExtra(EXTRA_Date, date)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val EXTRA1_REPLY = "com.example.android.wordlistsql.REPLY1"
        const val EXTRA_ID = "com.example.android.wordlistsql.ID"
        const val EXTRA_Date = "com.example.android.wordlistsql.Date"
    }
}