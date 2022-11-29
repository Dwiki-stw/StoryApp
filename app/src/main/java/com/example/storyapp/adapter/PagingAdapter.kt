package com.example.storyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.CardStoryBinding
import com.example.storyapp.response.ListStoryItem

class PagingAdapter : PagingDataAdapter<ListStoryItem, PagingAdapter.MyViewHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder(private val binding: CardStoryBinding): RecyclerView.ViewHolder(binding.root) {
       fun bind(story: ListStoryItem){

           val desc = get50Character(story.description.toString())

           binding.apply {
               binding.username.text = story.name
               binding.description.text = desc

               Glide.with(itemView)
                   .load(story.photoUrl)
                   .into(imgStory)
           }
       }

        val areaClick = binding.card

        private fun get50Character(description: String) : String{

            return if(description.length > 50){
                description.substring(0..49) + "…"
            } else{
                description
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }

        holder.areaClick.setOnClickListener{
            onItemClickCallback.onItemClicked(data?.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    interface OnItemClickCallback{
        fun onItemClicked(id: String?)
    }

    companion object {
        const val TAG = "PagingAdapter"
        
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}