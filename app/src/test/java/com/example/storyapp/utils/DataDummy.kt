package com.example.storyapp.utils


import com.example.storyapp.response.ListStoryItem


object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem>{
        val item: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..5){
            val story = ListStoryItem(
                "story- $i",
                "https://story-api.dicoding.dev/images/stories/photos-$i",
                i.toString(),
                i.toString(),
                "lorem ipsum $i",
                0.0,
                0.0
            )
            item.add(story)
        }
        return item
    }

}