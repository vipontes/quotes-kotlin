package br.net.easify.quotes.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.net.easify.quotes.Database.DAO.ITokenDao
import br.net.easify.quotes.Database.Entities.Token
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import br.net.easify.quotes.Database.DAO.IUsuarioDao
import br.net.easify.quotes.Database.Entities.UsuarioLogado

@Database(entities = [Token::class, UsuarioLogado::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tokenDao(): ITokenDao
    abstract fun usuarioDao(): IUsuarioDao

    companion object {
        var INSTANCE: AppDatabase? = null
        val DB_NAME = "quotes.sqlite"

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}