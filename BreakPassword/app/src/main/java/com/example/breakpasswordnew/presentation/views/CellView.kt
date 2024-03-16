package com.example.breakpasswordnew.presentation.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.breakpasswordnew.R
import com.example.breakpasswordnew.model.Cell
import com.example.breakpasswordnew.model.PlaceType
import java.lang.IllegalStateException

class CellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var cell: Cell? = null

    private val strokeSize = context.resources.getDimension(R.dimen.cell_stroke_size)
    private val bold: Typeface =
        Typeface.createFromAsset(context.assets, "fonts/level_personal.ttf")

    private val strokePaint: Paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        this.strokeWidth = strokeSize
    }

    private val nonePaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.noneCell)
        style = Paint.Style.FILL
    }

    private val itHasPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.itHasCell)
        style = Paint.Style.FILL
    }

    private val onPlacePaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.onPlaceCell)
        style = Paint.Style.FILL
    }

    private var center = 0f
    private var radius = 0f
    private var radiusStroke = 0f

    private val paintBackground: Paint
        get() {
            return when {
                cell == null -> nonePaint
                cell!!.placeType == PlaceType.NONE -> nonePaint
                cell!!.placeType == PlaceType.WRONG -> nonePaint
                cell!!.placeType == PlaceType.IT_HAS -> itHasPaint
                cell!!.placeType == PlaceType.ON_PLACE -> onPlacePaint
                else -> throw IllegalStateException("Неверный тип")
            }
        }

    private val paintText: Paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        typeface = bold
        textAlign = Paint.Align.CENTER
    }

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        isFocusable = true
    }

    fun setModel(cell: Cell) {
        this.cell = cell
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        paintText.apply {
            textSize = measuredWidth / 2f
        }
        center = measuredWidth / 2f
        radius = measuredWidth / 2f
        radiusStroke = radius - strokeSize / 2
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (cell != null) {
            canvas?.drawCircle(center, center, radius, paintBackground)
            if (cell!!.isFocused) {
                canvas?.drawCircle(center, center, radiusStroke, strokePaint)
            }
            canvas?.drawText(
                cell!!.symbolText,
                measuredWidth / 2f,
                (measuredHeight / 2f - ((paintText.descent() + paintText.ascent()) / 2)),
                paintText
            )
        }
    }
}