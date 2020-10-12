package com.example.unsplashimagesearch.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
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
import com.example.unsplashimagesearch.MainActivity
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

        // set appbar title
        (requireActivity() as MainActivity).supportActionBar?.title = username

        binding.apply {
            Glide.with(requireContext())
                .load(imgUri)
                .centerCrop()
                .transition(
                DrawableTransitionOptions.withCrossFade()
            ).error(R.drawable.ic_broken_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
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
                        textViewDescription.isVisible = imgDesc != null
                        progressBar.isVisible = false
                        return false
                    }
                })
                .into(imageView)

            textViewDescription.text = imgDesc
            textViewCreator.text = "Photo By $username"

            val uri = Uri.parse(navArgs.photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            textViewCreator.apply {
                text = "Photo by $username"
                setOnClickListener{ startActivity(intent) }
                paint.isUnderlineText = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}