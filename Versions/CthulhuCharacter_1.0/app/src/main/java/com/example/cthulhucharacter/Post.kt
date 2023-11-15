package com.example.cthulhucharacter

/**
 * Created by PK on 12/24/2017.
 */

class Post {
    var postHeading: String? = null
    var postUrl: String? = null
    var postAuthor: String? = null
    var postTag: List<String>? = null

    constructor() : super() {}

    constructor(PostHeading: String, PostUrl: String, PostAuthor: String, tags: List<String>) : super() {
        this.postHeading = PostHeading
        this.postUrl = PostUrl
        this.postAuthor = PostAuthor
        this.postTag = tags
    }

}