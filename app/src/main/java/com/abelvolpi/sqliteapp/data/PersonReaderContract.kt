package com.abelvolpi.sqliteapp.data

import android.provider.BaseColumns

object PersonReaderContract {
    object PersonEntry : BaseColumns {
        const val TABLE_NAME = "people"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_BIRTHDAY = "birthday"
        const val COLUMN_IMAGE_URI = "image_uri"
    }
}