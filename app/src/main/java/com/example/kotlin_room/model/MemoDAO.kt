package com.example.kotlin_room.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.kotlin_room.model.MemoEntity


@Dao
interface MemoDAO {

    //Insert 할때 primaryKey가 같으면 덮어쓴다
    @Insert(onConflict = REPLACE)
    fun insert(memo: MemoEntity)


    // memo entity의 tableName 이 memo 이기 때문에 memo를 조회
    @Query("SELECT * FROM memo")
    fun getAll() : List<MemoEntity>

    @Delete
    fun delete(memo: MemoEntity)
}