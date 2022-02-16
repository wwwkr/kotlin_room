package com.example.kotlin_room.view


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_room.model.MemoDatabase
import com.example.kotlin_room.OnDeleteListener
import com.example.kotlin_room.RvAdapter
import com.example.kotlin_room.model.MemoEntity
import com.example.kotlin_room.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() , OnDeleteListener {

    lateinit var db : MemoDatabase
    var memoList = listOf<MemoEntity>()

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: RvAdapter

    /**
     * 2022/02/16
     * 데이터바인딩을 Activity에 적용한 예제임
     * ViewModel을 사용한 예제로 수정 예정
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MemoDatabase.getInstance(this)!!

        binding.btnAdd.setOnClickListener {
            val memo = MemoEntity(null, binding.etMemo.text.toString())
            insertMemo(memo)

            binding.etMemo.setText("")
        }


        getAllMemo()




    }

    //1. Insert Data
    //2. Get Data
    //3. Delete Data
    //4. Set RecyclerView


    fun insertMemo(memo: MemoEntity) {
        //1. MainThread vs WorkerThread(Background Thread)


//
//        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
//            override fun doInBackground(vararg params: Unit?) {
//                db.memoDAO().insert(memo)
//            }
//
//            override fun onPostExecute(result: Unit?) {
//                super.onPostExecute(result)
//                getAllMemo()
//            }
//
//        }
//
//        insertTask.execute()



        // Dispatchers.Main Main Thread를 사용 UI변경에 사용
        // Dispatchers.IO background에서 동작 네트워크 및 데이터베이스에 사용
        // Dispatchers.Default CPU 사용량이 많은 무거운 작업에서 주로 사용함

        CoroutineScope(Dispatchers.IO).launch {
            db.memoDAO().insert(memo)
            getAllMemo()
        }


    }

    fun getAllMemo(){


        //코루틴은 비동기로 실행되기 때문에 IO와 Main의 실행 순서를 지정하기 위해선 join()을 사용해야함

        CoroutineScope(Dispatchers.Default).launch {

            CoroutineScope(Dispatchers.IO).launch {
                Log.e("TAG", "getAllMemo IO")
                memoList = db.memoDAO().getAll()


            }.join()

            CoroutineScope(Dispatchers.Main).launch {
                Log.e("TAG", "getAllMemo Main")
                setRecyclerView(memoList)

            }

        }


//        val getTask = object : AsyncTask<Unit, Unit, Unit>(){
//            override fun doInBackground(vararg p0: Unit?) {
//                memoList = db.memoDAO().getAll()
//
//            }
//
//            override fun onPostExecute(result: Unit?) {
//                super.onPostExecute(result)
//                setRecyclerView(memoList)
//            }
//
//        }.execute()


    }

    fun deleteMemo(memo : MemoEntity){


        CoroutineScope(Dispatchers.IO).launch {
            db.memoDAO().delete(memo)
            getAllMemo()
        }


//        val deleteTask = object : AsyncTask<Unit, Unit, Unit>(){
//            override fun doInBackground(vararg param: Unit?) {
//
//                db.memoDAO().delete(memo)
//            }
//
//            override fun onPostExecute(result: Unit?) {
//                super.onPostExecute(result)
//
//                getAllMemo()
//            }
//        }
//
//        deleteTask.execute()

    }

    fun setRecyclerView(memoList: List<MemoEntity>) {

        binding.rvMemo.layoutManager = LinearLayoutManager(this)
        adapter = RvAdapter(this, memoList, this)
        binding.rvMemo.adapter = adapter


    }

    override fun onDeleteListener(memo: MemoEntity) {
        deleteMemo(memo)


    }




}