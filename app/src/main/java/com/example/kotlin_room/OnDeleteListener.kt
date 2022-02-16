package com.example.kotlin_room

import com.example.kotlin_room.model.MemoEntity

interface OnDeleteListener {

    fun onDeleteListener(memo : MemoEntity)
}