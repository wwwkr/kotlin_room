package com.example.kotlin_room.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(MemoEntity::class), version = 1)
abstract class MemoDatabase : RoomDatabase() {

    abstract fun memoDAO() : MemoDAO

    //리소스를 많이 사용하기 때문에 싱글톤으로 구현
    companion object{
        var INSTANCE : MemoDatabase? = null

        fun getInstance(context : Context) : MemoDatabase?{
            if(INSTANCE == null) {
                synchronized(MemoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MemoDatabase::class.java, "memo.db"
                    )
                        .fallbackToDestructiveMigration()
                            //fallbackToDestructiveMigration 버전이 업데이트 되면 과거의 데이터는 삭제
                        .build()
                }
            }
            return INSTANCE

        }
    }

}