package com.example.storyapp.ui.activity.listStory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.CardStoryBinding
import com.example.storyapp.response.ListStoryItem

class ListStoryAdapter (private val listStories: List<ListStoryItem>): RecyclerView.Adapter<ListStoryAdapter.ViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStories[position])

        holder.areaClick.setOnClickListener{
            onItemClickCallback.onItemClicked(listStories[position].id)
        }
    }

    override fun getItemCount(): Int {
        return listStories.size
    }

    class ViewHolder(private val binding: CardStoryBinding) : RecyclerView.ViewHolder(binding.root) {

        val areaClick = binding.card

        fun bind(stories: ListStoryItem){
            val desc = get50Character(stories.description.toString())

            binding.apply {
                username.text = stories.name
                description.text = desc

                Glide.with(itemView)
                    .load(stories.photoUrl)
                    .into(imgStory)
            }
        }

        private fun get50Character(description: String) : String{

            if(description.length > 50){
                return description.substring(0..49) + "…"
            }
            else{
                return description
            }
        }

    }

    interface OnItemClickCallback{
        fun onItemClicked(id: String?)
    }
}