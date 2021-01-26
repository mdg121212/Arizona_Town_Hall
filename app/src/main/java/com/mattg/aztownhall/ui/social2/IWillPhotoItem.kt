package com.mattg.aztownhall.ui.social2

import com.google.firebase.storage.StorageReference

open class IWillPhotoItem {
     var image: StorageReference? = null
     var statement: String = ""
     var name: String = ""
     var position: Int = 0
     private var imageRef = image?.bucket

    constructor(){}

    constructor(image: StorageReference, statement: String, name: String){
         this.image = image
         this.statement = statement
         this.name = name
         imageRef
     }
}