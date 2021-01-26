package com.mattg.aztownhall.ui.social2


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.mattg.aztownhall.R

class DrawingView(context: Context, attrs: AttributeSet ) : View(context, attrs) {
    //initialize variables when drawing view is set
    private var mDrawPath:  CustomPath? = null
    private var mCanvasBitmap: Bitmap? = null
    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mBrushSize: Float = 150.toFloat()
    private var mColor = Color.WHITE
    private var canvas: Canvas? = null
    private var mPaths = ArrayList<CustomPath>()
    private var mIWillText = "I Will"
    private var mTextLocationX: Float? = null
    private var mTextLocationY: Float? = null
    private var mFont = R.font.anaheim
    private var mTextSize: Float = 300f



    init {
       setupDrawing()
    }

    private fun setupDrawing(){
        mDrawPaint = Paint()
        mDrawPath = CustomPath(mColor, mBrushSize)
        mDrawPaint?.apply {
            color = mColor
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        mCanvasPaint = Paint(Paint.DITHER_FLAG)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh) //inherits fun from view class
        //this will call once the view is displayed
        mCanvasBitmap =
            Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888) //many ways to create this bitmap
        //use bitmap as canvas
        if (!mDrawPath?.isEmpty!!) {
            mDrawPaint?.apply{
                strokeWidth = mDrawPath!!.brushThickness
                color = mDrawPaint!!.color
            }
            canvas = Canvas(mCanvasBitmap!!) //was just set so assert not null
        }
    }

    fun setTextSize(newSize: Float){
        mDrawPaint!!.apply {
            textSize = newSize
        }
        mTextSize = newSize
    }

    fun setTextColor(newColor: String){
        mColor = Color.parseColor(newColor)

    }




    //handle the screen input via touch
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y
        mTextLocationX = touchX
        mTextLocationY = touchY

        when(event?.action) {
            //always the same three things
            //PRESS ON SCREEN
            MotionEvent.ACTION_DOWN -> {
                mDrawPath?.color = mColor
                mDrawPath?.brushThickness = mBrushSize

                mDrawPath?.reset()
                //move path to where the touch event occured
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath?.moveTo(touchX, touchY)

                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath?.moveTo(touchX, touchY)
                    }
                }
            }
            //When finger lifted
            MotionEvent.ACTION_UP -> {
                mPaths.add(mDrawPath!!)
                mDrawPath = CustomPath(mColor, mBrushSize)

            }

            else -> return false
        }

        invalidate()
        return true

    }

     fun setFont(font: Int){
        mFont = font
    }


    //changed canvas? to canvas if this fails
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //top left corner is 0, 0
        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)
        //now draw the path
        for(path in mPaths){
            mDrawPaint?.apply {
                textAlign = Paint.Align.CENTER
                style = Paint.Style.FILL
                textSize = mTextSize
                color = mColor
                typeface = ResourcesCompat.getFont(context, mFont)
                strokeWidth = path.brushThickness

               canvas.drawText(mIWillText, mTextLocationX!!, mTextLocationY!!, mDrawPaint!!)

            }
        }

        if(!mDrawPath!!.isEmpty) {
         mDrawPaint?.apply {
             color = mColor
             strokeWidth = mDrawPath!!.brushThickness
         }
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }
        }
    internal class CustomPath(var color: Int, var brushThickness: Float) : Path()

}