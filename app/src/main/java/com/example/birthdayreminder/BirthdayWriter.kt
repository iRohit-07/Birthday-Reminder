package com.example.birthdayreminder

import android.content.ContentValues
import android.content.Intent
import android.provider.ContactsContract
import java.util.Locale

class BirthdayWriter {

    // Builds an intent that opens the Contacts editor with name and birthday pre-filled
    fun createInsertIntent(name: String, year: Int, month: Int, day: Int): Intent {
        val dateText = String.format(Locale.US, "%04d-%02d-%02d", year, month, day)

        val birthdayRow = ContentValues().apply {
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
            )
            put(
                ContactsContract.CommonDataKinds.Event.TYPE,
                ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY
            )
            put(ContactsContract.CommonDataKinds.Event.START_DATE, dateText)
        }

        val data = ArrayList<ContentValues>()
        data.add(birthdayRow)

        return Intent(ContactsContract.Intents.Insert.ACTION).apply {
            type = ContactsContract.RawContacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME, name)
            putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data)
        }
    }
}