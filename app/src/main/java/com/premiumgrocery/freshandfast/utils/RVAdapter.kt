package com.premiumgrocery.freshandfast.utils

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KFunction1

// T refers to the object type of your data list
// The adapter is not aware of the binding you're using. That is something you
//      define and store in bindFunc and itemBindingInflater.
// Inflater's function will look something like this:
//  { inflater, container, attach -> XxxBinding.inflate(inflater, container, attach) }
// Note that the binding from bindFunc requires casting.
class RVAdapter<T>(
    private val items: List<T>,
    private val itemBindingInflater: (LayoutInflater, ViewGroup, Boolean) -> ViewBinding,
    private val bindFunc: (T, ViewBinding ,RVAdapter<T>) -> Unit
): RecyclerView.Adapter<RVAdapter<T>.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        itemBindingInflater( LayoutInflater.from(parent.context), parent, false )
    )
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position], this)
    inner class VH(private val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
        item: T,
        adapter: RVAdapter<T>
    ) = bindFunc(item, binding, adapter) }

    companion object {
        fun spacerDecoration(margin: Int = 32, gridColumnCount: Int = 1) =
            object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) = with(outRect) {
                    val isLeft = parent.getChildAdapterPosition(view) % gridColumnCount == 0
                    val isRight = (parent.getChildAdapterPosition(view) + 1) % gridColumnCount == 0
                    val isTop = parent.getChildAdapterPosition(view) < gridColumnCount
                    val isBottom = parent.getChildAdapterPosition(view) > (parent.adapter?.itemCount ?: 0)
                    val halfMargin = margin / 2

                    top = if (isTop) margin else halfMargin
                    bottom = if (isBottom) margin else halfMargin
                    left = if (isLeft) margin else halfMargin
                    right = if (isRight) margin else halfMargin
                }
            }
    }
}