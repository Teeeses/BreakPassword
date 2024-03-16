package com.example.breakpasswordnew.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import com.example.breakpasswordnew.model.Password

class PasswordView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    cellsNumber: Int
) : ViewGroup(context, attrs, defStyle) {

    var dividerSize: Int = 0

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        (0 until cellsNumber).forEach { _ ->
            addView(
                CellView(context).apply {
                    layoutParams =
                        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                }
            )
        }
    }

    fun setModel(password: Password) {
        children.forEachIndexed { index, view ->
            (view as CellView).setModel(password.cells[index])
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        var finalizeCellSize = 0
        if (childCount > 0) {
            val dividerNumber = childCount - 1
            dividerSize = ((measuredWidth / childCount) * 0.2f).toInt()
            finalizeCellSize = (measuredWidth - (dividerSize * dividerNumber)) / childCount

            for (child in children) {
                child.measure(
                    MeasureSpec.makeMeasureSpec(finalizeCellSize, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(finalizeCellSize, MeasureSpec.AT_MOST)
                )
            }
        }

        setMeasuredDimension(measuredWidth, finalizeCellSize)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var offsetLeft = 0
        for (child in children) {
            child.layout(offsetLeft, 0, offsetLeft + child.measuredWidth, child.measuredHeight)
            offsetLeft += child.measuredWidth + dividerSize
        }
    }
}