package com.cpe.funconnect.funconnect

import android.content.Context
import android.graphics.*
import android.provider.Settings.Global.getFloat
import android.view.MotionEvent
//import android.support.test.runner.intent.IntentStubberRegistry.reset
import android.util.DisplayMetrics
//import java.awt.font.ShapeGraphicAttribute.STROKE
import android.util.AttributeSet
import android.view.View
import kotlin.math.max


class PaintView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var mX: Float = 0.toFloat()
    private var mY: Float = 0.toFloat()
    private var mPath: Path? = null
    private val mPaint: Paint
    private val paths = ArrayList<FingerPath>()
    private var currentColor: Int = 0
    private var backGroundColor: Int = DEFAULT_BG_COLOR
    private var strokeWidth: Float = 0.toFloat()
    private var emboss: Boolean = false
    private var blur: Boolean = false
    private val mEmboss: MaskFilter
    private val mBlur: MaskFilter
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private val mBitmapPaint = Paint(Paint.DITHER_FLAG)

    init {
        mPaint = Paint()
        mPaint.setAntiAlias(true)
        mPaint.setDither(true)
        mPaint.setColor(DEFAULT_COLOR)
        mPaint.setStyle(Paint.Style.STROKE)
        mPaint.setStrokeJoin(Paint.Join.ROUND)
        mPaint.setStrokeCap(Paint.Cap.ROUND)
        mPaint.setXfermode(null)
        mPaint.setAlpha(0xff)

        mEmboss = EmbossMaskFilter(floatArrayOf(1f, 1f, 1f), 0.4f, 6f, 3.5f)
        mBlur = BlurMaskFilter(5f, BlurMaskFilter.Blur.NORMAL)
    }

    fun init(metrics: DisplayMetrics) {
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

        currentColor = DEFAULT_COLOR
        strokeWidth = BRUSH_SIZE
    }

    fun normal() {
        emboss = false
        blur = false
    }

    fun emboss() {
        emboss = true
        blur = false
    }

    fun blur() {
        emboss = false
        blur = true
    }

    fun clear() {
        backGroundColor = DEFAULT_BG_COLOR
        paths.clear()
        normal()
        invalidate()
    }

    protected override fun onDraw(canvas: Canvas) {
        canvas.save()
        mCanvas!!.drawColor(backGroundColor)

        for (fp in paths) {
            mPaint.setColor(fp.color)
            mPaint.setStrokeWidth(fp.strokeWidth)
            mPaint.setMaskFilter(null)

            if (fp.emboss)
                mPaint.setMaskFilter(mEmboss)
            else if (fp.blur)
                mPaint.setMaskFilter(mBlur)

            mCanvas!!.drawPath(fp.path, mPaint)

        }

        canvas.drawBitmap(mBitmap, 0.toFloat(), 0.toFloat(), mBitmapPaint)
        canvas.restore()
    }

    private fun touchStart(x: Float, y: Float) {
        mPath = Path()
        val tmp = mPath
        if(tmp is Path){
            val fp = FingerPath(currentColor, emboss, blur, strokeWidth, tmp)
            paths.add(fp)
        }



        mPath!!.reset()
        mPath!!.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        mPath!!.lineTo(mX, mY)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }

        return true
    }

    companion object {

        var BRUSH_SIZE = 20.toFloat()
        val DEFAULT_COLOR = Color.RED
        val DEFAULT_BG_COLOR = Color.WHITE
        private val TOUCH_TOLERANCE = 4f
    }
}