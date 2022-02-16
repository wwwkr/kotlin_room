package com.example.kotlin_room

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_room.model.MemoEntity
import com.example.kotlin_room.databinding.ItemRvMemoBinding

class RvAdapter(val context: Context,
                var list: List<MemoEntity>,
                var deleteListener : OnDeleteListener) : RecyclerView.Adapter<RvAdapter.VH>() {

    lateinit var binding : ItemRvMemoBinding

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {


        binding = ItemRvMemoBinding.inflate(LayoutInflater.from(parent.context))

//        val itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_memo, parent, false)

        return VH(binding.root)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val memo : MemoEntity = list[position]

        binding.tvMemo.text = memo.memo
        binding.root.setOnLongClickListener(object  : View.OnLongClickListener {
            override fun onLongClick(params: View?): Boolean {

                deleteListener.onDeleteListener(memo)

                return true
            }


        })


    }



    inner class VH(itemView : View) : RecyclerView.ViewHolder(itemView){

        val memo : TextView = binding.tvMemo
        val root : ConstraintLayout = binding.root.rootView as ConstraintLayout


    }


}