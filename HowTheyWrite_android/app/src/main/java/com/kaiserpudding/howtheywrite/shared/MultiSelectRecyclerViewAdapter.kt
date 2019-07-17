package com.kaiserpudding.howtheywrite.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

abstract class MultiSelectRecyclerViewAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private val mutableSelectedId: MutableSet<Int> = mutableSetOf()
    val selectedId: IntArray
        get() = mutableSelectedId.toIntArray()
    private val mutableInSelectionMode = MutableLiveData<Boolean>()
    val inSelectionMode: LiveData<Boolean>
        get() = mutableInSelectionMode
    private val mutableNumberOfSelected = MutableLiveData<Int>()
    val numberOfSelected: LiveData<Int>
        get() = mutableNumberOfSelected


    fun toggleSelectedThenNotify(id: Int) {
        if (selectedId.contains(id)) mutableSelectedId.remove(id)
        else mutableSelectedId.add(id)
        mutableInSelectionMode.postValue(selectedId.isNotEmpty())
        mutableNumberOfSelected.postValue(selectedId.size)
        notifyDataSetChanged()
    }

    fun clearSelectedThenNotify() {
        mutableSelectedId.clear()
        mutableInSelectionMode.postValue(false)
        notifyDataSetChanged()
    }
}