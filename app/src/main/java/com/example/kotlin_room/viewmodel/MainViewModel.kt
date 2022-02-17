package com.example.kotlin_room.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.kotlin_room.OnDeleteListener
import com.example.kotlin_room.RvAdapter
import com.example.kotlin_room.model.MemoDatabase
import com.example.kotlin_room.model.MemoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var TAG = "MainViewModel"
    private var db : MemoDatabase = MemoDatabase.getInstance(application)!!


    var memo : LiveData<List<MemoEntity>>

    var newMemo : String? = null

    init {
        memo = getAllMemo()
    }


    fun getAllMemo(): LiveData<List<MemoEntity>> {

        Log.e(TAG, "getAllMemo")

        return db.memoDAO().getAll()
    }



    fun insertMemo(memo: String) {

        viewModelScope.launch(Dispatchers.IO){
            db.memoDAO().insert(MemoEntity(null, memo))


        }

        Log.e(TAG, "insertMemo")
    }

    fun deleteMemo(memo : MemoEntity){


        viewModelScope.launch(Dispatchers.IO){
            db.memoDAO().delete(memo)

        }


    }



}