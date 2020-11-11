package com.mattg.arizonatownhall.viewmodels



import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mattg.arizonatownhall.ui.social2.IWillPhotoItem
import com.mattg.arizonatownhall.utils.User
import java.lang.Exception


class SocialViewModel : ViewModel() {

    private var database: FirebaseFirestore? = null

    val text =  MutableLiveData<String>()

    init {
        text.value = "Test Value"
    }

    private val _leaders = MutableLiveData<List<User>>()

    val leaders: MutableLiveData<List<User>> = _leaders

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
                              text = text.substring(0,1).toLowerCase() + text.substring(1)
                          } catch (e: Exception){

                          }

                            val storageReference = FirebaseStorage.getInstance().reference
                          val imageRef = storageReference.child("images").child("$image")

                          if(imageRef != null) {
                              val itemToAdd = IWillPhotoItem(imageRef, text, name)
                              list2.add(itemToAdd)
                          } else{
                              Log.i("error", "storage ref was null")
                          }

                      }
                      photoList.value = list2
                  }

                var position = 1
                var numberOfUsersLoaded = 0
                val data = snapshots.documents
                for(doc in data){
                    val points = doc.get("points").toString()
                    val name = doc.get("name").toString()
                    if(points.isNotEmpty()) {
                        val newPoints = points.toInt()
                        val addUser = User(name, newPoints, position)
                        list.add(addUser)
                        position++
                        numberOfUsersLoaded++
                        if(numberOfUsersLoaded == 5){
                            break
                        }
                    }
                    }
            }
            leaders.value = list


        }


    }



}