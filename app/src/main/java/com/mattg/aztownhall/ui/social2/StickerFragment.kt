package com.mattg.aztownhall.ui.social2

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.mattg.aztownhall.R
import com.mattg.aztownhall.stickerView.BubbleTextView
import com.mattg.aztownhall.stickerView.Sticker
import com.mattg.aztownhall.stickerView.StickerView
import com.mattg.aztownhall.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_sticker.*
import kotlinx.android.synthetic.main.sticker_dialog.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

open class StickerFragment : BaseFragment() {

    private var pictureUri = ""
    private var iWillText = ""
    private var wasRotated = false
    private var timesRotated = 0
    private var textColorVar: Int = Color.WHITE
    private var textColorString: String? = null
    private lateinit var stickerList: List<Sticker>
    private lateinit var clickListener: StickerClickListener
    private lateinit var mColorMatrix: ColorMatrixColorFilter
    private var drawableCanvas: DrawingView? = null

    //当前处于编辑状态的贴纸
    private var mCurrentView: StickerView? = null

    //当前处于编辑状态的气泡
    private val mCurrentEditTextView: BubbleTextView? = null

    //存储贴纸列表
    private val mViews: ArrayList<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments?.let {
            StickerFragmentArgs.fromBundle(it)
        }
        args?.iwill.let {
            iWillText = it.toString()
        }

        args?.uri?.let {
            pictureUri = it

        }
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.GONE

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        drawableCanvas = drawable_canvas
        return inflater.inflate(R.layout.fragment_sticker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (pictureUri != "") {
            when (pictureUri) {
                "R.drawable.gradient_one" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_one)
                }
                "R.drawable.gradient_two" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_two)
                }
                "R.drawable.gradient_three" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_three)
                }
                "R.drawable.gradient_four" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_four)
                }
                "R.drawable.gradient_five" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_five)
                }
                "R.drawable.gradient_six" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_six)
                }
                "R.drawable.gradient_seven" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_seven)
                }
                "R.drawable.gradient_eight" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_eight)
                }
                "R.drawable.gradient_nine" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_nine)
                }
                "R.drawable.gradient_ten" -> {
                    iv_picture_for_sticker.setImageResource(R.drawable.gradient_ten)
                }
                else -> Glide.with(this)
                    .load(pictureUri)
                    .into(iv_picture_for_sticker)
            }

        }

        //list of images to feed sticker recycler view
        stickerList = listOf(
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.skinnyquial, null)!!,
                R.drawable.skinnyquial
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.bird, null)!!,
                R.drawable.bird
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.gila, null)!!,
                R.drawable.gila
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.hedgehog, null)!!,
                R.drawable.hedgehog
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.claphands, null)!!,
                R.drawable.claphands
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.foldedhands, null)!!,
                R.drawable.foldedhands
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.peacehand, null)!!,
                R.drawable.peacehand
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.bicepsemoji, null)!!,
                R.drawable.bicepsemoji
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.trophy, null)!!,
                R.drawable.trophy
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.onehundred, null)!!,
                R.drawable.onehundred
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.popcorkemoji, null)!!,
                R.drawable.popcorkemoji
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.fireemoji, null)!!,
                R.drawable.fireemoji
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.collisionemoji, null)!!,
                R.drawable.collisionemoji
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.directhitemoji, null)!!,
                R.drawable.directhitemoji
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.twohearts, null)!!,
                R.drawable.twohearts
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.heart, null)!!,
                R.drawable.heart
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.heartwithlines, null)!!,
                R.drawable.heartwithlines
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.sunemoji, null)!!,
                R.drawable.sunemoji
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.sunglassesemoji, null)!!,
                R.drawable.sunglassesemoji
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.emoji_blush, null)!!,
                R.drawable.emoji_blush
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.emoji_grin, null)!!,
                R.drawable.emoji_grin
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.emoji_in_love, null)!!,
                R.drawable.emoji_in_love
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.emoji_angry, null)!!,
                R.drawable.emoji_angry
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.home_wheel_graphic, null)!!,
                R.drawable.home_wheel_graphic
            ),
            Sticker(
                ResourcesCompat.getDrawable(resources, R.drawable.townhallsmall, null)!!,
                R.drawable.townhallsmall
            )
        )


        val message = Snackbar.make(
            view,
            "Use the Tt button to customize text, the + button to add stickers, and the save button to save!",
            Snackbar.LENGTH_LONG
        )
        message.show()
        ib_rotate.setOnClickListener {
            rotatePhotoNinety()
        }
        ib_text.setOnClickListener {
            showTextDialog()
        }

        ib_sticker.setOnClickListener {
            startStickerDialog()
        }

        ib_save_picture.setOnClickListener {
            //fl_drawing_view_container
            mCurrentView?.setInEdit(false)
            if (timesRotated > 0) {
                drawable_canvas.resize(
                    iv_picture_for_sticker.measuredWidth,
                    iv_picture_for_sticker.measuredHeight
                )
                drawable_canvas.setBackgroundColor(Color.TRANSPARENT)
                fl_drawing_view_container.setBackgroundColor(Color.TRANSPARENT)
                fl_drawing_view_container.resize(
                    iv_picture_for_sticker.measuredWidth,
                    iv_picture_for_sticker.measuredHeight
                )
                cl_sticker_fragment.setBackgroundColor(Color.TRANSPARENT)
                iv_picture_for_sticker.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            //   val imageSaved = BitmapAsyncTask(getBitmapFromView(fl_drawing_view_container))
            //   imageSaved.execute()
            val imageSaved = BitmapBackgroundTask(getBitmapFromView(fl_drawing_view_container))
            val imageSavedString = imageSaved.getString()

            //   val imageSavedString = imageSaved.get()
            val actionLoadImage = StickerFragmentDirections.actionStickerFragmentToNavSocial()
            actionLoadImage.uri = imageSavedString
            actionLoadImage.iwill = iWillText
            findNavController().navigate(actionLoadImage)
            timesRotated = 0
        }
    }


    private fun showTextDialog() {
        val textDialog = Dialog(requireContext())
        textDialog.apply {
            setContentView(R.layout.dialog_text_font)
            val sizeText = findViewById<TextView>(R.id.tv_fontsize_indicator)
            //set up seekbar to control textDraw size
            val seekBar = findViewById<SeekBar>(R.id.seekBar_font_size)

            val step = 1
            val max = 420
            val min = 180

            seekBar.max = ((max - min) / step)

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                    val value = min + (progress * step)
                    setFontSize(value.toFloat())
                    if (value in 180..260) {
                        sizeText.text = getString(R.string.small_size_text)
                    }
                    if (value in 261..340) {
                        sizeText.text = getString(R.string.medium_size_text)
                    }
                    if (value in 341..420) {
                        sizeText.text = getString(R.string.large_size_text)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }
            })


            val textColorButton = findViewById<Button>(R.id.btn_text_color)
            val tvAkromin = findViewById<TextView>(R.id.tv_font_akronim).apply {
                setOnClickListener {
                    fontButtonClick(R.font.akronim)
                }
            }
            val tvAllura = findViewById<TextView>(R.id.tv_font_allura).apply {
                setOnClickListener {
                    fontButtonClick(R.font.allura)
                }
            }
            val tvCinzel = findViewById<TextView>(R.id.tv_font_cinzel).apply {
                setOnClickListener {
                    fontButtonClick(R.font.cinzel_decorative)
                }
            }
            val tvLobster = findViewById<TextView>(R.id.tv_font_lobster).apply {
                setOnClickListener {
                    fontButtonClick(R.font.lobster_two_italic)
                }
            }
            val tvMonofett = findViewById<TextView>(R.id.tv_font_monofett).apply {
                setOnClickListener {
                    fontButtonClick(R.font.monofett)
                }
            }
            val tvRockSalt = findViewById<TextView>(R.id.tv_font_rock_salt).apply {
                setOnClickListener {
                    fontButtonClick(R.font.rock_salt)
                }
            }
            val tvMrDafoe = findViewById<TextView>(R.id.tv_font_mr_dafoe).apply {
                setOnClickListener {
                    fontButtonClick(R.font.mr_dafoe)
                }
            }
            val tvCursive = findViewById<TextView>(R.id.tv_font_cursive).apply {
                setOnClickListener {
                    fontButtonClick(R.font.alex_brush)
                }
            }
            val tvAguafina = findViewById<TextView>(R.id.tv_font_aguafina).apply {
                setOnClickListener {
                    fontButtonClick(R.font.aguafina_script)
                }
            }
            val textViews = listOf<TextView>(
                tvAkromin, tvAllura,
                tvCinzel, tvLobster, tvMonofett, tvAguafina,
                tvRockSalt, tvMrDafoe, tvCursive
            )

            textColorButton.setOnClickListener {
                MaterialColorPickerDialog
                    .Builder(requireActivity())                // Pass Activity Instance
                    .setColorShape(ColorShape.SQAURE)    // Default ColorShape.CIRCLE
                    .setColorSwatch(ColorSwatch._300)    // Default ColorSwatch._500
                    .setDefaultColor(R.color.primaryColor)    // Pass Default Color
                    .setColorListener { color, colorHex ->
                        textColorVar = color
                        textColorString = colorHex

                        val matrixColor = Color.parseColor(colorHex)
                        val red = Color.red(matrixColor)
                        val green = Color.green(matrixColor)
                        val blue = Color.blue(matrixColor)
                        val array = floatArrayOf(
                            0f, 0f, 0f, 0f, red.toFloat(),
                            0f, 0f, 0f, 0f, green.toFloat(),
                            0f, 0f, 0f, 0f, blue.toFloat(),
                            0f, 0f, 0f, 1f, 0f
                        )
                        val matrix = ColorMatrixColorFilter(array)
                        mColorMatrix = matrix
                        for (item in textViews) {
                            item.setTextColor(color)
                        }

                        setFontColor(colorHex)

                    }.show()
            }

            this.findViewById<Button>(R.id.btn_text_chooser_done).setOnClickListener {
                textDialog.dismiss()
                val textEditedMessage = Snackbar.make(
                    requireView(),
                    "Now click on the image to add 'I will' text!",
                    Snackbar.LENGTH_SHORT
                )
                textEditedMessage.animationMode = Snackbar.ANIMATION_MODE_FADE
                textEditedMessage.show()
            }
        }
        textDialog.show()

    }

    private fun setFontSize(size: Float) {
        drawable_canvas.setTextSize(size)
    }

    private fun setFontColor(color: String) {
        drawable_canvas.setTextColor(color)
    }

    private fun fontButtonClick(fontInt: Int) {
        Toast.makeText(requireContext(), "Font selected!", Toast.LENGTH_SHORT).show()
        drawable_canvas.setFont(fontInt)
    }


    @SuppressLint("SetTextI18n")
    private fun startStickerDialog() {

        val stickerDialog = Dialog(requireContext())
        stickerDialog.apply {

            setContentView(R.layout.sticker_dialog)
            clickListener = StickerClickListener { sticker ->
                addStickerView(sticker.id)
                stickerDialog.dismiss()
            }

            val stickerRecycler = stickerDialog.rv_stickers
            val layoutManagerStickers =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            stickerRecycler.apply {
                adapter = StickerAdapter(requireContext(), stickerList, clickListener)
                layoutManager = layoutManagerStickers
            }

        }
        stickerDialog.show()
    }


    private fun addStickerView(imageId: Int) {
        val stickerView = StickerView(requireContext())
        mViews?.add(stickerView)

        stickerView.setImageResource(imageId)

        stickerView.setOperationListener(object : StickerView.OperationListener {
            override fun onDeleteClick() {
                mViews?.remove(stickerView)
                fl_drawing_view_container.removeView(stickerView)
            }

            override fun onEdit(stickerView: StickerView) {
                mCurrentEditTextView?.setInEdit(false)
                mCurrentView?.setInEdit(false)
                mCurrentView = stickerView
                mCurrentView?.setInEdit(true)
            }

            override fun onTop(stickerView: StickerView?) {
                val position: Int? = mViews?.indexOf(stickerView)
                if (position == mViews?.size?.minus(1)) {
                    return
                }
                val stickerTemp: StickerView = position?.let { mViews?.removeAt(it) } as StickerView
                mViews?.add(mViews.size, stickerTemp)
            }
        })
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        fl_drawing_view_container.addView(stickerView, lp)
        mViews?.add(stickerView)
        setCurrentEdit(stickerView)
    }

    /**
     * 设置当前处于编辑模式的贴纸
     */
    private fun setCurrentEdit(stickerView: StickerView) {
        if (mCurrentView != null) {
            mCurrentView!!.setInEdit(false)
        }
        mCurrentEditTextView?.setInEdit(false)
        mCurrentView = stickerView
        stickerView.setInEdit(true)
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap: Bitmap = if (wasRotated) {
            Bitmap.createBitmap(
                iv_picture_for_sticker.measuredWidth,
                iv_picture_for_sticker.measuredHeight,
                Bitmap.Config.ARGB_8888
            )

        } else {
            Bitmap.createBitmap(
                iv_picture_for_sticker.measuredWidth,
                iv_picture_for_sticker.measuredHeight,
                Bitmap.Config.ARGB_8888
            )

        }
        //bind canvas that is on view
        val canvas = Canvas(returnedBitmap)
        //get background as well
        val bgDrawable = iv_picture_for_sticker.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.TRANSPARENT)
        }
        //finalize
        view.draw(canvas)
        return returnedBitmap
    }

    private fun rotatePhotoNinety() {
        iv_picture_for_sticker.rotation += -90f
        iv_picture_for_sticker.scaleType = ImageView.ScaleType.MATRIX
        iv_picture_for_sticker.fitsSystemWindows = true
        timesRotated++
        wasRotated = true
    }


//

    //
    fun saveFileCoroutine(mBitmap: Bitmap): String {
        val result1: String
        mBitmap.setHasAlpha(true)
        val bytes = ByteArrayOutputStream() //pass to compress method First step for saving files
        mBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)

        val fileSave = File(
            context?.getExternalFilesDir(null)?.absoluteFile.toString()
                    + File.separator
                    + "AzTownHall_"
                    + System.currentTimeMillis() / 1000
                    + ".png"
        )  //each file needs unique name, use the current time by millisecond

        runBlocking {
            val fos = FileOutputStream(fileSave)
            fos.write(bytes.toByteArray())
            fos.close()  //have to close these streams
        }


        result1 = fileSave.absolutePath
        return result1
    }

    private fun FrameLayout.resize(width: Int, height: Int) {
        this.layoutParams.height = height
        this.layoutParams.width = width
    }

    private fun DrawingView.resize(width: Int, height: Int) {
        this.layoutParams.height = height
        this.layoutParams.width = width
    }


    private inner class BitmapBackgroundTask(val mBitmap: Bitmap?) {

        fun getString(): String {
            var returnResult = ""
            if (mBitmap != null) {
                try {
                    lifecycleScope.launch {
                        returnResult = saveFileCoroutine(mBitmap)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            return returnResult
        }

    }
}


