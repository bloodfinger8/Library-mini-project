package com.group.libraryapp.controller.feed

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FeedController(
    private val findFeedService: FindFeedService
) {

    @GetMapping("/feed")
    fun findFeed() {
    }
}
