package com.example.psyhealthapp.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
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

    private lateinit var fieldNameEdit: EditText
    private lateinit var fieldAgeEdit: EditText
    private lateinit var fieldSexEdit: RadioGroup
    private lateinit var sexMan: RadioButton
    private lateinit var sexWoman: RadioButton
    private lateinit var btnSave: Button

    private lateinit var btnEdit: ImageButton
    private lateinit var btnStatistic: ImageButton
    private lateinit var btnSettings: ImageButton


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
        btnSave = view.findViewById(R.id.btnSave)
        btnSave.setOnClickListener(clickListener)
        btnSave.visibility = View.GONE

        btnEdit = view.findViewById(R.id.btnEdit)
        btnEdit.setOnClickListener(clickListener)
        btnStatistic = view.findViewById(R.id.btnStatistic)
        btnStatistic.setOnClickListener(clickListener)
        btnSettings = view.findViewById(R.id.btnSettings)
        btnSettings.setOnClickListener(clickListener)


    }

    private val wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT
    private val lParams = LinearLayout.LayoutParams(wrapContent, wrapContent)

    private val clickListener = View.OnClickListener { p0 ->
        when (p0) {
            btnEditImageProfile -> {
                profileImageLauncher.launch(imagePickIntent)
            }
            btnEdit -> {
                btnEdit.isClickable = false
                btnSave.visibility = View.VISIBLE

                val currentName = fieldName.text.toString()
                llBackName.removeView(fieldName)
                fieldNameEdit = EditText(activity)
                fieldNameEdit.setText(currentName)
                llBackName.addView(fieldNameEdit, lParams)

                val currentAge = fieldAge.text.toString()
                llBackAge.removeView(fieldAge)
                fieldAgeEdit = EditText(activity)
                fieldAgeEdit.setText(currentAge)
                fieldAgeEdit.inputType = InputType.TYPE_CLASS_NUMBER
                llBackAge.addView(fieldAgeEdit, lParams)

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
                when (currentSex) {
                    "мужской" -> sexMan.isChecked = true
                    "женский" -> sexWoman.isChecked = true
                }
                llBackSex.addView(fieldSexEdit, lParams)

            }
            btnSave -> {
                btnSave.visibility = View.GONE
                btnEdit.isClickable = true

                val savingName = fieldNameEdit.text.toString()
                llBackName.removeView(fieldNameEdit)
                fieldName.text = savingName
                llBackName.addView(fieldName, lParams)

                val savingAge = fieldAgeEdit.text.toString()
                llBackAge.removeView(fieldAgeEdit)
                fieldAge.text = savingAge
                llBackAge.addView(fieldAge, lParams)

                val savingSex = when (fieldSexEdit.checkedRadioButtonId) {
                    sexMan.id -> "мужской"
                    sexWoman.id -> "женский"
                    else -> "-"
                }
                llBackSex.removeView(fieldSexEdit)
                fieldSex.text = savingSex
                llBackSex.addView(fieldSex, lParams)
            }
            btnStatistic -> {

            }
            btnSettings -> {

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

