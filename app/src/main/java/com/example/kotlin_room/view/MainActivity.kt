package com.example.kotlin_room.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_room.model.MemoDatabase
import com.example.kotlin_room.OnDeleteListener
import com.example.kotlin_room.R
import com.example.kotlin_room.RvAdapter
import com.example.kotlin_room.model.MemoEntity
import com.example.kotlin_room.databinding.ActivityMainBinding
import com.example.kotlin_room.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() , OnDeleteListener {

    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: RvAdapter
    lateinit var viewModel : MainViewModel

    /**
     * 2022/02/16
     * 데이터바인딩을 Activity에 적용한 예제임
     * ViewModel을 사용한 예제로 수정 예정
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        viewModel = MainViewModel(application)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        db = MemoDatabase.getInstance(this)!!

        getAllMemo()

    }


    fun getAllMemo(){

        //코루틴은 비동기로 실행되기 때문에 IO와 Main의 실행 순서를 지정하기 위해선 join()을 사용해야함


        viewModel.getAllMemo().observe(this, Observer {
            setRecyclerView(it)
        })

    }

    fun deleteMemo(memo : MemoEntity){

        viewModel.deleteMemo(memo)

    }

    fun setRecyclerView(memoList: List<MemoEntity>) {

        adapter = RvAdapter( memoList, this)
        binding.rvMemo.adapter = adapter


    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)

    }




}