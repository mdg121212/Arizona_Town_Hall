package com.mattg.aztownhall.ui.social2


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.share.Sharer
import com.facebook.share.widget.ShareDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mattg.aztownhall.R
import com.mattg.aztownhall.databinding.FragmentSocialBinding
import com.mattg.aztownhall.utils.BaseFragment
import com.mattg.aztownhall.viewmodels.SocialViewModel
import kotlinx.android.synthetic.main.fragment_social.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "SocialFragment"
private const val GALLERY_PICTURE_REQUEST_CODE = 1
private const val VIDEO_REQUEST_CODE = 2
private const val TAKE_PICTURE_REQUEST_CODE = 3
class SocialFragment : BaseFragment() {

    private lateinit var socialViewModel: SocialViewModel
    private lateinit var callbackManager: CallbackManager
    private lateinit var shareDialog: ShareDialog
    private lateinit var currentPhotoPath: String
    private lateinit var clicklistenerBackground: BackRoundClickListener
    //For accessing firebase
    private var mFirebaseDatabaseInstance: FirebaseFirestore? = null

    private var userId: String? = null
    private var userName: String? = null
    private lateinit var rv: RecyclerView
    private lateinit var rvPhotos: RecyclerView
    private lateinit var cameraView: ImageView
    private var iWillText = ""
    private var imageBmp: Bitmap? = null
    private lateinit var binding: FragmentSocialBinding
    private var uriOfSelfie = ""
    private lateinit var storageRef: StorageReference
    private var uriFromGallery = ""
    private var videoCaptureUri = ""
    private var uriFromGradient = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize facebook SDK.
        FacebookSdk.sdkInitialize(requireActivity().applicationContext)
        callbackManager = CallbackManager.Factory.create()
        shareDialog = ShareDialog(this)
        shareDialog.registerCallback(callbackManager, callBack)

        mFirebaseDatabaseInstance = FirebaseFirestore.getInstance()
        //get current user to read from the right records
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        userId = user?.uid //if not null, get the id ONE TIME
        userName = user?.displayName

        storageRef = FirebaseStorage.getInstance().reference

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        binding = FragmentSocialBinding.inflate(inflater, container, false)
        socialViewModel =
            ViewModelProvider(this).get(SocialViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args =
            arguments?.let { //getting the nav args from bundle
                SocialFragmentArgs.fromBundle(it)
            }
             uriOfSelfie = args?.uri.toString()


        if (uriOfSelfie != "default") {
            Toast.makeText(
                requireContext(),
                "Scroll down and click the share button to post your picture and I will statement to social media!",
                Toast.LENGTH_LONG
            ).show()
            btn_share_social.visibility = View.VISIBLE

            imageBmp = BitmapFactory.decodeFile(uriOfSelfie)

            iv_borderselfie.visibility = View.VISIBLE

            Glide.with(requireContext())
                .load(imageBmp)
                .into(iv_borderselfie)

            val needToRotate = args?.needToRotate.toString()

            if(needToRotate == "yes"){
               iv_borderselfie.adjustViewBounds = true
                iv_borderselfie.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
        }


        cameraView = view.findViewById(R.id.iv_borderselfie)

        socialViewModel.populateSocialRecyclers()

        socialViewModel.photoList.observe(viewLifecycleOwner, {
            createPhotoRecycler(it)
            progressBar2.visibility = View.INVISIBLE
        })

        val cameraButton = binding.btnTakePhoto

        cameraButton.setOnClickListener {
           if (!checkPermissions()){
               requestPermissions()
           }
            if (et_iwill_statement.text.toString().isNotEmpty()) {

                if (checkIWillText(et_iwill_statement.text.toString())) {

                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

                    with(sharedPref?.edit()) {
                        this!!.putString("iwill", et_iwill_statement.text.toString())
                        apply()
                        Snackbar.make(requireView(), "Your I will statement has been saved, and will be included with your image if/when you share it!", Snackbar.LENGTH_INDEFINITE).show()
                    }
                    if(checkPermissions()) {
                        AlertDialog.Builder(requireContext()).setTitle("Photo Source")
                            .setPositiveButton("Take new photo") { _, _ ->
                                takePicture()

                            }.setNegativeButton("Choose From Gallery") { _, _ ->
                                openGalleryGetImage()
                            }
                            .setNeutralButton("Choose Simple Background") { _, _ ->
                                val string = et_iwill_statement.text.toString()
                                socialViewModel.setText(string)
                                val backgroundList = listOf(
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_one, null)!!, R.drawable.gradient_one),
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_two, null)!!, R.drawable.gradient_two),
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_three, null)!!, R.drawable.gradient_three),
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_four, null)!!, R.drawable.gradient_four),
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_five, null)!!, R.drawable.gradient_five),
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_six, null)!!, R.drawable.gradient_six),
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_seven, null)!!, R.drawable.gradient_seven),
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_eight, null)!!, R.drawable.gradient_eight),
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_nine, null)!!, R.drawable.gradient_nine),
                                    BackgroundItem(ResourcesCompat.getDrawable(resources, R.drawable.gradient_ten, null)!!, R.drawable.gradient_ten)
                                )
                                val brDialog = Dialog(requireContext())
                                    brDialog.apply {
                                    clicklistenerBackground = BackRoundClickListener {
                                        when(it.backgroundImage){
                                            ResourcesCompat.getDrawable(resources, R.drawable.gradient_one, null) ->{
                                                uriFromGradient = Uri.parse("android.resource://com.mattg.arizonatownhall/drawable/gradient_one")
                                                    .toString()

                                            }
                                            ResourcesCompat.getDrawable(resources, R.drawable.gradient_two, null) ->{
                                                uriFromGradient = Uri.parse("android.resource://com.mattg.arizonatownhall/drawable/gradient_two")
                                                    .toString()
                                                startEditAction(string)
                                            }
                                            ResourcesCompat.getDrawable(resources, R.drawable.gradient_three, null) ->{
                                                uriFromGradient = Uri.parse("android.resource://com.mattg.arizonatownhall/drawable/gradient_three")
                                                    .toString()
                                                startEditAction(string)
                                            }
                                            ResourcesCompat.getDrawable(resources, R.drawable.gradient_four, null) -> {
                                                uriFromGradient = Uri.parse("android.resource://com.mattg.arizonatownhall/drawable/gradient_four")
                                                    .toString()
                                                startEditAction(string)
                                            }
                                        }
                                        when(it.imageId){
                                            R.drawable.gradient_one -> {
                                                uriFromGradient = "R.drawable.gradient_one"
                                                startEditAction(string)
                                               dismiss()
                                            }
                                            R.drawable.gradient_two -> {
                                                uriFromGradient = "R.drawable.gradient_two"
                                                startEditAction(string)
                                                dismiss()
                                            }
                                            R.drawable.gradient_three -> {
                                                uriFromGradient = "R.drawable.gradient_three"
                                                startEditAction(string)
                                                dismiss()
                                            }
                                            R.drawable.gradient_four -> {
                                                uriFromGradient = "R.drawable.gradient_four"
                                                startEditAction(string)
                                                dismiss()
                                            }
                                            R.drawable.gradient_five -> {
//
                                                uriFromGradient = "R.drawable.gradient_five"
                                                startEditAction(string)
                                                dismiss()
                                            }
                                            R.drawable.gradient_six -> {
//
                                                uriFromGradient = "R.drawable.gradient_six"
                                                startEditAction(string)
                                                dismiss()
                                            }
                                            R.drawable.gradient_seven -> {
//
                                                uriFromGradient = "R.drawable.gradient_seven"
                                                startEditAction(string)
                                                dismiss()
                                            }
                                            R.drawable.gradient_eight -> {
//
                                                uriFromGradient = "R.drawable.gradient_eight"
                                                startEditAction(string)
                                                dismiss()
                                            }
                                            R.drawable.gradient_nine -> {
//
                                                uriFromGradient = "R.drawable.gradient_nine"
                                                startEditAction(string)
                                                dismiss()
                                            }
                                            R.drawable.gradient_ten -> {
//
                                                uriFromGradient = "R.drawable.gradient_ten"
                                                startEditAction(string)
                                                dismiss()
                                            }
                                        }
                                    }
                                    setContentView(R.layout.simple_background)
                                    setTitle("Simple Backgrounds")
                                    val recycler = findViewById<RecyclerView>(R.id.rv_simplebackgrounds)
                                    val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                                    recycler.adapter = BackgroundAdapter(requireContext(), backgroundList, clicklistenerBackground)
                                    recycler.layoutManager = layoutManager
                                        setCancelable(true)

                                }

                                brDialog.show()


                            }.show()


                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please enter an I will statement.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter an I will statement",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        val button = binding.btnShareSocial

        button.setOnClickListener {

            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val default = ""
            val text = sharedPref?.getString("iwill", default)

            if (text != null) {
                postToFaceBook(text, imageBmp, uriOfSelfie)
            }

            val file = Uri.fromFile(File(uriOfSelfie))
            val selfieRef = storageRef.child("images/${file.lastPathSegment}")
            val uploadTask: UploadTask = selfieRef.putFile(file)
            val filePathToSave = file.lastPathSegment
            val nameToSave = userName
            val nameForDocument = sharedPref?.getString("iwill", default)

            val imageReference = hashMapOf(
                "IWillText" to "$nameForDocument",
                "ImageName" to "$filePathToSave",
                "UserName" to "$nameToSave"
            )
            mFirebaseDatabaseInstance?.collection("users")?.document("$userId")
                ?.collection("IWillStatements")?.document("$nameForDocument")
                ?.set(imageReference)
                ?.addOnSuccessListener {

                }?.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Error uploading image: ${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            uploadTask.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Failed to upload file: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }.addOnSuccessListener {
                findNavController().navigate(R.id.action_nav_social_to_self)
            }
        }

    }

    private fun startEditAction(string: String)  {
        val action =
            SocialFragmentDirections.actionNavSocialToStickerFragment()
        action.iwill = string
        action.uri = uriFromGradient
       findNavController().navigate(action)
    }

    private fun openGalleryGetImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_PICTURE_REQUEST_CODE)
    }




    private fun checkIWillText(input: String): Boolean {
        //array of strings not to be allowed in statements
        val filterWords = resources.getStringArray(R.array.nsfw).toList()

        val rx = Regex("\\b(?:${filterWords.joinToString("|")})\\b")
        return if (rx.containsMatchIn(input)) {
            Toast.makeText(
                requireContext(),
                "Please try a statement with less profane language.",
                Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            iWillText = if (input.startsWith("i will", true)) {
                input.removePrefix("i will")
            } else {
                input

            }

            true
        }
    }

    private fun createPhotoRecycler(list: List<IWillPhotoItem>) {
        rvPhotos = rv_photoItems
        rvPhotos.adapter = IWillAdapter(requireContext(), list)
        val layoutManager2 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvPhotos.layoutManager = layoutManager2
    }


    private val callBack = object : FacebookCallback<Sharer.Result> {
        override fun onSuccess(result: Sharer.Result?) {
            Log.d(TAG, "Share success: ${result?.postId}")
        }

        override fun onCancel() {
            Log.d(TAG, "Share cancelled")
        }

        override fun onError(error: FacebookException?) {
            Log.d(TAG, "Error: ${error?.message}")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_PICTURE_REQUEST_CODE) {
            val uri = data?.data!!
            uriFromGallery = uri.toString()
            val action = SocialFragmentDirections.actionNavSocialToStickerFragment()
            action.iwill = iWillText
            action.uri = uriFromGallery
            findNavController().navigate(action)
        }
        if(requestCode == VIDEO_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val videoUri = data!!.data
            videoCaptureUri = videoUri.toString()
            Toast.makeText(requireContext(), "Video Capture Successful URI = $videoUri, Class Variable Uri = $videoCaptureUri", Toast.LENGTH_SHORT).show()

        }
        if(requestCode == TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val action = SocialFragmentDirections.actionNavSocialToStickerFragment()
            action.iwill = iWillText
            action.uri = currentPhotoPath
            findNavController().navigate(action)
        }



    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            val photoFile: File? = try{
                createFileForImage()
            } catch (ex: IOException) {
                Toast.makeText(requireContext(), "Error saving picture", Toast.LENGTH_SHORT).show()
                null
            }
            //continue only if the File was created
            photoFile?.also {
                val photoUri: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.mattg.arizonatownhall.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(takePictureIntent, TAKE_PICTURE_REQUEST_CODE)
            }
        }
    }


    private fun createFileForImage() : File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDirectory: File? = requireContext().getExternalFilesDir(null)
        return File.createTempFile(
            "JPEG_${timeStamp}_", //prefix
            ".jpg", //suffix
            storageDirectory
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

}