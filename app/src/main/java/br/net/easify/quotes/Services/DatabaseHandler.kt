package br.net.easify.quotes.Services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.net.easify.quotes.Interfaces.LoginDao
import br.net.easify.quotes.Model.Login

@Database(entities = [Login::class], version = 1)
abstract class DatabaseHandler : RoomDatabase() {

    abstract fun LoginDao(): LoginDao

    companion object {
        @Volatile private var instance: DatabaseHandler? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            DatabaseHandler::class.java, "quotes.db")
            .build()
    }
}