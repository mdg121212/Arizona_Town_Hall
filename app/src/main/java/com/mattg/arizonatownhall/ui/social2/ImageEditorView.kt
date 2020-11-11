package com.mattg.arizonatownhall.ui.social2

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.mattg.arizonatownhall.R
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoEditorView
import kotlinx.android.synthetic.main.fragment_image_editor_view.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [ImageEditorView.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageEditorView : Fragment() {

    private lateinit var mPhotoEditorView: PhotoEditorView
    private lateinit var mPhotoEditor: PhotoEditor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_editor_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPhotoEditorView = view.photoEditorView
        mPhotoEditor = PhotoEditor.Builder(requireContext(), mPhotoEditorView)
            .setPinchTextScalable(true)
            .setDefaultTextTypeface(ResourcesCompat.getFont(requireContext(),R.font.akronim))
            .build()
        mPhotoEditor.apply {
            setBrushDrawingMode(false)
            addText("TEST TEXT", Color.WHITE)
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageEditorView().apply {
                arguments = Bundle().apply {

                }
            }
    }
}