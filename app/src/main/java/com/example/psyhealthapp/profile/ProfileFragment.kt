package com.example.psyhealthapp.profile

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.psyhealthapp.R


class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private lateinit var imageProfile: ImageView
    private lateinit var btnEditImageProfile: Button
    private lateinit var profileImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var imagePickIntent: Intent

    private lateinit var llBackName: LinearLayout
    private lateinit var llBackAge: LinearLayout
    private lateinit var llBackSex: LinearLayout
    private lateinit var fieldName: TextView
    private lateinit var fieldAge: TextView
    private lateinit var fieldSex: TextView

    private lateinit var btnSave: Button
    private lateinit var btnEdit: Button

    private lateinit var fieldNameEdit: EditText
    private lateinit var fieldSexEdit: RadioGroup
    private lateinit var sexMan: RadioButton
    private lateinit var sexWoman: RadioButton



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageProfile = view.findViewById(R.id.imageProfile)
        btnEditImageProfile = view.findViewById(R.id.btnEditImageProfile)
        btnEditImageProfile.setOnClickListener(clickListener)
        pickProfileImage()

        llBackName = view.findViewById(R.id.llBackName)
        llBackAge = view.findViewById(R.id.llBackAge)
        llBackSex = view.findViewById(R.id.llBackSex)
        fieldName = view.findViewById(R.id.fieldName)
        fieldAge = view.findViewById(R.id.fieldAge)
        fieldSex = view.findViewById(R.id.fieldSex)

        btnEdit = view.findViewById(R.id.btnEdit)
        btnEdit.setOnClickListener(clickListener)
        btnSave = view.findViewById(R.id.btnSave)
        btnSave.setOnClickListener(clickListener)
        btnSave.visibility = View.GONE

    }

    private val wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT
    private val lParams = LinearLayout.LayoutParams(wrapContent, wrapContent)

    private val clickListener = View.OnClickListener { p0 ->
        when (p0) {
            btnEditImageProfile -> {
                profileImageLauncher.launch(imagePickIntent)
            }
            btnEdit -> {
                btnSave.visibility = View.VISIBLE

                val currentName = fieldName.text.toString()
                llBackName.removeView(fieldName)
                fieldNameEdit = EditText(activity)
                fieldNameEdit.setText(currentName)
                llBackName.addView(fieldNameEdit, lParams)

                llBackAge.removeView(fieldAge)

                val currentSex = fieldSex.text.toString()
                llBackSex.removeView(fieldSex)
                sexMan = RadioButton(activity)
                sexWoman = RadioButton(activity)
                sexMan.text = "м"
                sexWoman.text = "ж"
                fieldSexEdit = RadioGroup(activity)
                fieldSexEdit.orientation = LinearLayout.HORIZONTAL
                fieldSexEdit.addView(sexMan)
                fieldSexEdit.addView(sexWoman)
                llBackSex.addView(fieldSexEdit, lParams)
                when (currentSex) {
                    "мужской" -> sexMan.isChecked = true
                    "женский" -> sexWoman.isChecked = true
                }

            }
            btnSave -> {
                btnSave.visibility = View.GONE

                val savingName = fieldNameEdit.text.toString()
                llBackName.removeView(fieldNameEdit)
                fieldName.text = savingName
                llBackName.addView(fieldName, lParams)

                val savingSex = when (fieldSexEdit.checkedRadioButtonId) {
                    sexMan.id -> "мужской"
                    sexWoman.id -> "женский"
                    else -> "-"
                }
                llBackSex.removeView(fieldSexEdit)
                fieldSex.text = savingSex
                llBackSex.addView(fieldSex, lParams)

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

}