package com.kaiserpudding.howtheywrite.database

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream

class ChineseDbHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "howTheyLearn_database_cn"
        const val DATABASE_VERSION = 2
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(
            "${context.packageName}.database_versions",
            Context.MODE_PRIVATE
    )

    private fun installDatabaseFromAssets() {
        val inputStream = context.assets.open("databases/$DATABASE_NAME")
        val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
        val outputStream = FileOutputStream(outputFile)

        inputStream.copyTo(outputStream)
        inputStream.close()

        outputStream.flush()
        outputStream.close()

    }

    private fun installedDatabaseIsOutdated(): Boolean {
        return preferences.getInt(DATABASE_NAME,0) < DATABASE_VERSION
    }

    private fun writeDatabaseVersionInPreferences() {
        preferences.edit().apply{
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

    @Synchronized
    private fun installOrUpdateIfNecessary() {
        if (installedDatabaseIsOutdated()) {
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getWritableDatabase()
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        //nothing
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //nothing
    }
}