package com.example.kotlin_room.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
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


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var viewModel : MainViewModel

    /**
     * 2022/02/16
     * 데이터바인딩을 Activity에 적용한 예제임
     * ViewModel을 사용한 예제로 수정 예정
     *
     * 2022/02/18
     * 수정하였으나 아직 미흡...
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(MainViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        getAllMemo()

    }


    fun getAllMemo(){

        viewModel.getAllMemo().observe(this, Observer {

            viewModel.setRecyclerView(it , binding.rvMemo)
        })

    }



}