package com.abelvolpi.sqliteapp.data

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns

class PersonRepository(
    context: Context
) {
    private var dbHelper: PersonReaderDbHelper = PersonReaderDbHelper(context)

    fun addPerson(
        name: String,
        email: String,
        birthday: String
    ) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(PersonReaderContract.PersonEntry.COLUMN_NAME, name)
            put(PersonReaderContract.PersonEntry.COLUMN_EMAIL, email)
            put(PersonReaderContract.PersonEntry.COLUMN_BIRTHDAY, birthday)
        }
        db.insert(PersonReaderContract.PersonEntry.TABLE_NAME, null, values)
    }

    fun removePerson(
        id: String
    ) {
        val db = dbHelper.writableDatabase
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(id)
        db.delete(PersonReaderContract.PersonEntry.TABLE_NAME, selection, selectionArgs)
    }

    fun editPerson(
        id: String,
        name: String,
        email: String,
        birthday: String
    ) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(PersonReaderContract.PersonEntry.COLUMN_NAME, name)
            put(PersonReaderContract.PersonEntry.COLUMN_EMAIL, email)
            put(PersonReaderContract.PersonEntry.COLUMN_BIRTHDAY, birthday)
        }

        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(id)
        db.update(
            PersonReaderContract.PersonEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }

    fun readAllData(): List<Person> {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            PersonReaderContract.PersonEntry.COLUMN_NAME,
            PersonReaderContract.PersonEntry.COLUMN_EMAIL,
            PersonReaderContract.PersonEntry.COLUMN_BIRTHDAY
        )
        val cursor = db.query(
            PersonReaderContract.PersonEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val personList = mutableListOf<Person>()
        with(cursor) {
            while (moveToNext()) {
                val person = Person(
                    getString(getColumnIndexOrThrow(BaseColumns._ID)),
                    getString(getColumnIndexOrThrow(PersonReaderContract.PersonEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(PersonReaderContract.PersonEntry.COLUMN_EMAIL)),
                    getString(getColumnIndexOrThrow(PersonReaderContract.PersonEntry.COLUMN_BIRTHDAY))
                )
                personList.add(person)
            }
        }
        cursor.close()
        return personList
    }

    fun readOneData(id: String): Person {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            PersonReaderContract.PersonEntry.COLUMN_NAME,
            PersonReaderContract.PersonEntry.COLUMN_EMAIL,
            PersonReaderContract.PersonEntry.COLUMN_BIRTHDAY
        )
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id)

        val cursor = db.query(
            PersonReaderContract.PersonEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val personList = mutableListOf<Person>()
        with(cursor) {
            while (moveToNext()) {
                val person = Person(
                    getString(getColumnIndexOrThrow(BaseColumns._ID)),
                    getString(getColumnIndexOrThrow(PersonReaderContract.PersonEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(PersonReaderContract.PersonEntry.COLUMN_EMAIL)),
                    getString(getColumnIndexOrThrow(PersonReaderContract.PersonEntry.COLUMN_BIRTHDAY))
                )
                personList.add(person)
            }
        }
        cursor.close()
        return personList.first()
    }

}