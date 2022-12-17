package com.example.storyapp.utils


import com.example.storyapp.response.*


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

    fun generateDummyLoginResponse(): ResponseLogin {
        return ResponseLogin(
            LoginResult(
                "Setw",
                "user-TLqjKMpiMgI1YSOn",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVRMcWpLTXBpTWdJMVlTT24iLCJpYXQiOjE2NzA4NTE0NDl9.-52D5c7mdbvqD0NcHSc5srusYwFaopvq5EDO8L8M37Y"
            ),
            false,
            "success"
        )
    }

    fun generateDummyRegisterResponse(): ResponseRegister {
        return ResponseRegister(false, "User created")
    }

    fun generateDummyLocationResponse(): ResponseListStory{
        return ResponseListStory(
            generateDummyStoryResponse(),
            false,
            "success"
        )
    }

    fun generateDummyDetailResponse(): ResponseDetailStory{
        return ResponseDetailStory(
            false,
            "Stories fetched successfully",
            Story(
                "https://story-api.dicoding.dev/images/stories/photos-",
                "2022-12-13T06:45:21.154Z",
                "setw",
                "lorem ipsum",
                0.0,
                "story-s8QhZ9XycqrYGZV2",
                0.0
            )
        )
    }

}