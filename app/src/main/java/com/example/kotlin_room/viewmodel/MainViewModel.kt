package com.example.kotlin_room.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_room.OnDeleteListener
import com.example.kotlin_room.RvAdapter
import com.example.kotlin_room.model.MemoDatabase
import com.example.kotlin_room.model.MemoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) , OnDeleteListener{

    var TAG = "MainViewModel"

    private var db : MemoDatabase = MemoDatabase.getInstance(application)!!

    var newMemo : String? = null

    lateinit var adapter: RvAdapter

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

    fun setRecyclerView(memoList: List<MemoEntity> , rvMemo : RecyclerView)  {

        adapter = RvAdapter( memoList, this)
        rvMemo.adapter = adapter

    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)
    }


}