package com.abelvolpi.sqliteapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


class PersonReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        const val DATABASE_VERSION = 1

        const val DATABASE_NAME = "PersonReader.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${PersonReaderContract.PersonEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${PersonReaderContract.PersonEntry.COLUMN_NAME} TEXT," +
                    "${PersonReaderContract.PersonEntry.COLUMN_EMAIL} TEXT," +
                    "${PersonReaderContract.PersonEntry.COLUMN_BIRTHDAY} TEXT," +
                    "${PersonReaderContract.PersonEntry.COLUMN_IMAGE_URI} TEXT)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${PersonReaderContract.PersonEntry.TABLE_NAME}"
    }
}