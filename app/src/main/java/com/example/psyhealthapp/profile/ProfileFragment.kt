package com.example.psyhealthapp.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R


class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private lateinit var imageProfile: ImageView
    private lateinit var btnEditImageProfile: Button

    private lateinit var profileImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var imagePickIntent: Intent

    private val clickListener = View.OnClickListener { p0 ->
        when (p0) {
            btnEditImageProfile -> {
                profileImageLauncher.launch(imagePickIntent)
            }


        }
    }

    private fun pickProfileImage() {
        imagePickIntent = Intent(Intent.ACTION_PICK)
        imagePickIntent.type = "image/*"
        profileImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imData: Intent? = result.data
                val selectedIm = imData?.data
                imageProfile.setImageURI(null)
                imageProfile.setImageURI(selectedIm)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageProfile = view.findViewById(R.id.imageProfile)
        btnEditImageProfile = view.findViewById(R.id.btnEditImageProfile)
        btnEditImageProfile.setOnClickListener(clickListener)
        pickProfileImage()

    }



}