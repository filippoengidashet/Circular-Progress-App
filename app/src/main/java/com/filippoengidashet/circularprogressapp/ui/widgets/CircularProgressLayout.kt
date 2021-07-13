package com.filippoengidashet.circularprogressapp.ui.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.filippoengidashet.circularprogressapp.R

/**
 * @author Filippo 13/07/2021
 */
class CircularProgressLayout : FrameLayout {

    private val arcBounds = RectF()

    private var arcSweepAngle: Float = 0f
    private var arcStrokeSize: Float = 0f
    private var arcInsetSize: Float = 0f
    private var circleSize: Float = 0f
    private var strokeSize: Float = 0f
    private var animationDelay: Long = DEFAULT_ANIM_DELAY

    private lateinit var paintArc: Paint
    private lateinit var paint: Paint

    private var animator: ValueAnimator? = null
    private var callback: Callback? = null

    companion object {

        private const val DEFAULT_ANIM_DELAY = 800L
    }

    constructor(context: Context) : super(context) {
        doInit(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        doInit(context)
    }

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        doInit(context)
    }

    private fun doInit(context: Context) {
        setWillNotDraw(false)

        arcStrokeSize = resources.getDimensionPixelSize(R.dimen.cpv_arc_stroke_width).toFloat()
        arcInsetSize = resources.getDimensionPixelSize(R.dimen.cpv_arc_inset_size)
            .toFloat() - (arcStrokeSize / 2)
        paintArc = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            isDither = true
            style = Paint.Style.STROKE
            color = Color.parseColor("#087C67")
            strokeCap = Paint.Cap.ROUND
            strokeWidth = arcStrokeSize
        }

        strokeSize = resources.getDimensionPixelSize(R.dimen.cpv_stroke_width).toFloat()
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            isDither = false
            style = Paint.Style.STROKE
            color = Color.GRAY
            strokeWidth = strokeSize
        }
    }

    fun updateAnimationDelay(_delay: Long) {
        animationDelay = _delay
    }

    fun animateRange(start: Float, end: Float, target: Float) {
        stopRangeAnimation()
        val targetAngle = target * 360f / (end - start)
        animator = ValueAnimator.ofFloat(start, targetAngle).apply {
            interpolator = LinearInterpolator()
            duration = animationDelay
            addUpdateListener { anim ->
                arcSweepAngle = anim.animatedValue as Float
                val progressValue = (target * arcSweepAngle / targetAngle).toInt()
                callback?.onUpdate(arcSweepAngle, progressValue)
                invalidate()
            }
        }.also {
            it.start()
        }
    }

    fun stopRangeAnimation() {
        animator?.takeIf { it.isRunning }?.cancel()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = measuredWidth
        val h = measuredHeight
        arcBounds.set(
            arcInsetSize, arcInsetSize,
            w.toFloat() - arcInsetSize, h.toFloat() - arcInsetSize
        )
        circleSize = (w * 1.0 / 2).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isInEditMode) return
        canvas.drawCircle(circleSize, circleSize, circleSize - (strokeSize / 2), paint)
        canvas.drawArc(arcBounds, -90f, arcSweepAngle, false, paintArc)
    }

    override fun onDetachedFromWindow() {
        stopRangeAnimation()
        super.onDetachedFromWindow()
    }

    fun setCallback(_callback: Callback) {
        callback = _callback
    }

    interface Callback {

        fun onUpdate(angle: Float, progress: Int)
    }
}