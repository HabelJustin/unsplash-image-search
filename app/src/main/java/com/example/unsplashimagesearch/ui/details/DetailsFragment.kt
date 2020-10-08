package com.example.unsplashimagesearch.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.unsplashimagesearch.databinding.FragmentDetailsBinding
import com.example.unsplashimagesearch.R


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val navArgs by navArgs<DetailsFragmentArgs>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        val imgUri = navArgs.photo.urls.regular
        val imgDesc = navArgs.photo.description
        val username = navArgs.photo.user.username

        binding.apply {
            Glide.with(requireContext()).load(imgUri).centerCrop().transition(
                DrawableTransitionOptions.withCrossFade()

            ).error(R.drawable.ic_broken_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        textViewCreator.isVisible = true
                        textViewDescription.isVisible = true
                        progressBar.isVisible = false
                        return false
                    }
                })
                .into(imageView)

            textViewDescription.text = imgDesc
            textViewCreator.text = "Photo By $username"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}