package com.mattg.arizonatownhall.viewmodels



import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mattg.arizonatownhall.ui.social2.IWillPhotoItem
import com.mattg.arizonatownhall.utils.User
import java.util.*


class SocialViewModel : ViewModel() {

    private var database: FirebaseFirestore? = null

    val text =  MutableLiveData<String>()

    init {
        text.value = "Test Value"
    }


    private val _photoList = MutableLiveData<List<IWillPhotoItem>>()

    val photoList : MutableLiveData<List<IWillPhotoItem>> = _photoList


    fun setText(input: String){
     text.value = input
       Log.d("TEXT.VALUE = " , "${text.value}")
    }

    fun populateSocialRecyclers(){
        database = FirebaseFirestore.getInstance()
        var docRef = database?.collection("users")?.orderBy("points",
            com.google.firebase.firestore.Query.Direction.DESCENDING)
        docRef?.addSnapshotListener{snapshots, e ->
            if(e != null){
                return@addSnapshotListener
            }
            val list = mutableListOf<User>()
            if(snapshots != null){
              val gotStatements =  database?.collectionGroup("IWillStatements")?.get()
                  ?.addOnSuccessListener {
                     val list2 = mutableListOf<IWillPhotoItem>()
                      var imageUrl = ""
                      val items = it.documents
                      for(item in items){
                          val name = item.get("UserName").toString()
                          val image = item.get("ImageName").toString()
                          var text = item.get("IWillText").toString()
                          try{
                              text =
                                  text.substring(0, 1).toLowerCase(Locale.ROOT) + text.substring(1)
                          } catch (e: Exception){

                          }

                          val storageReference = FirebaseStorage.getInstance().reference
                          val imageRef = storageReference.child("images").child(image)

                          if(imageRef != null) {
                              val itemToAdd = IWillPhotoItem(imageRef, text, name)
                              list2.add(itemToAdd)
                          } else {
                              Log.i("error", "storage ref was null")
                          }

                      }
                      photoList.value = list2
                  }


            }


        }


    }



}