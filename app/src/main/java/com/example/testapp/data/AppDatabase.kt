package com.example.testapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.testapp.data.DatabaseWorker.Companion.KEY_FILENAME
import com.example.testapp.data.entity.ChildPlannerDB
import com.example.testapp.data.entity.ItemCharacterDB
import com.example.testapp.data.entity.PlannerDB
import com.example.testapp.utils.CHARACTER_DATA_FILENAME
import com.example.testapp.utils.DATABASE_NAME

@Database(entities = [ItemCharacterDB::class, PlannerDB::class, ChildPlannerDB::class], version = 3, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun CharactersDao() : CharactersDao
    abstract fun PlannerDao() : PlannerDao
    abstract fun ChildPlannerDao() : ChildPlannerDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE planner (`id` INTEGER NOT NULL DEFAULT '', `body` TEXT, `date` INTEGER, " +
                        "PRIMARY KEY(`id`))")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE planner_child (`id` INTEGER NOT NULL DEFAULT '', `parentId` INTEGER, `body` TEXT, `date` INTEGER, " +
                        "PRIMARY KEY(`id`))")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<DatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME  to CHARACTER_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
        }
    }
}