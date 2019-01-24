package com.cpe.funconnect.funconnect.model

import android.content.Context
import android.graphics.*
import android.os.SystemClock
import android.view.MotionEvent
import android.util.DisplayMetrics
import android.util.AttributeSet
import android.view.View

/**
 * Class taken from gitHub, it has only been adapted to fit with the data used
 */
class PaintView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var mX: Float = 0.toFloat()
    private var mY: Float = 0.toFloat()
    private lateinit var signature: Signature
    private var mPath: Path? = null
    private val mPaint: Paint
    private val paths = ArrayList<FingerPath>()
    private var currentColor: Int = 0
    private var backGroundColor: Int = DEFAULT_BG_COLOR
    private var strokeWidth: Float = 0.toFloat()
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private var time: Long = 0
    private var firstTouch: Boolean = true
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

    }

    /**
     * Init the complete view but also all the variables
     */
    fun init(metrics: DisplayMetrics) {
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        signature = Signature()

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)

        currentColor = DEFAULT_COLOR
        strokeWidth = BRUSH_SIZE
    }

    /**
     * Used to get the signature object which is applied to the user
     */
    fun getCoord(): Signature {
        return this.signature
    }

    /**
     * Clears the view, can be applied from the UI in case of wrong entry
     */
    fun clear() {
        backGroundColor = DEFAULT_BG_COLOR
        paths.clear()
        signature = Signature();
        firstTouch = true
        invalidate()
    }

    protected override fun onDraw(canvas: Canvas) {
        canvas.save()
        mCanvas!!.drawColor(backGroundColor)

        for (fp in paths) {
            mPaint.setColor(fp.color)
            mPaint.setStrokeWidth(fp.strokeWidth)
            mPaint.setMaskFilter(null)


            mCanvas!!.drawPath(fp.path, mPaint)

        }

        canvas.drawBitmap(mBitmap, 0.toFloat(), 0.toFloat(), mBitmapPaint)
        canvas.restore()
    }

    private fun touchStart(x: Float, y: Float) {
        mPath = Path()
        val tmp = mPath
        if (tmp is Path) {
            val fp = FingerPath(currentColor, strokeWidth, tmp)
            paths.add(fp)
        }



        mPath!!.reset()
        mPath!!.moveTo(x, y)
        mX = x
        mY = y

        if (firstTouch) {
            time = SystemClock.uptimeMillis()
            firstTouch = false
        }
        signature.addCoord(x.toInt(), y.toInt(), (SystemClock.uptimeMillis() - time).toInt())
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
            signature.addCoord(x.toInt(), y.toInt(), (SystemClock.uptimeMillis() - time).toInt())
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

    /**
     * Properties used for the drawing
     */
    companion object {

        var BRUSH_SIZE = 10.toFloat()
        val DEFAULT_COLOR = Color.BLACK
        val DEFAULT_BG_COLOR = Color.WHITE
        private val TOUCH_TOLERANCE = 4f
    }
}