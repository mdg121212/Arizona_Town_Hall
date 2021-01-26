package com.mattg.aztownhall.utils

class User {
    lateinit var name: String
    lateinit var email: String
    private lateinit var userId: String
    var points: Int = 0
    var position: Int = 0

    constructor()

    constructor(name: String, email: String, userId: String) {
        this.name = name
        this.email = email
        this.userId = userId
    }

    constructor(name: String, points: Int, position: Int) {
        this.name = name
        this.points = points
        this.position = position
    }




}