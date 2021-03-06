package com.example.unsplashimagesearch.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.unsplashimagesearch.R
import com.example.unsplashimagesearch.data.UnSplashPhoto
import com.example.unsplashimagesearch.databinding.ItemUnsplashPhotoBinding

class UnSplashPhotoAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UnSplashPhoto, UnSplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val photo = getItem(position) as UnSplashPhoto
                    listener.onItemClick(photo)
                }
            }
        }

        fun bind(photo: UnSplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_broken_image)
                    .into(imageView)

                textViewUsername.text = photo.user.username
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: UnSplashPhoto)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnSplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnSplashPhoto, newItem: UnSplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnSplashPhoto,
                newItem: UnSplashPhoto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}