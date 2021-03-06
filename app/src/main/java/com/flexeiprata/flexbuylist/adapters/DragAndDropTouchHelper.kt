package com.flexeiprata.flexbuylist.adapters

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.RecyclerView

abstract class DragAndDropTouchHelper : ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {
    //Целевой ViewHolder должен иметь интерфейс DragAndDropTouchHelper.ItemTouchHelperViewHolder


    interface ItemTouchHelperViewHolder {
        fun onItemSelected()
        fun onItemClear()
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        saveInDatabase()
        if (viewHolder is ItemTouchHelperViewHolder) {
            viewHolder.onItemClear()
        }
        super.clearView(recyclerView, viewHolder)
    }


    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder is ItemTouchHelperViewHolder) {
            viewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val startPosition = viewHolder.adapterPosition
        val targetPosition = target.adapterPosition
        swapList(startPosition, targetPosition)
        recyclerView.adapter?.notifyItemMoved(startPosition, targetPosition)

        return true
    }

    //изменение представляемого списка каждый раз, когда элемент меняет позицию
    abstract fun swapList(startPosition: Int, targetPosition: Int)

    //сохранение списка в базе данных после того, как элемент отпущен
    abstract fun saveInDatabase()

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //не реализован вследствие избыточности
    }



}