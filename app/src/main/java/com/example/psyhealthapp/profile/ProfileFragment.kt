package com.example.psyhealthapp.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.style.DynamicDrawableSpan
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.psyhealthapp.settings.SettingsFragment
import androidx.navigation.fragment.findNavController
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.UserDataHolder
import com.example.psyhealthapp.core.UserDataType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private lateinit var imageProfile: ImageView
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
    private lateinit var sexOther: RadioButton
    private lateinit var btnSave: Button

    private lateinit var btnEdit: ImageButton
    private lateinit var btnStatistic: ImageButton
    private lateinit var btnSettings: ImageButton

    @Inject
    lateinit var userDataHolder : UserDataHolder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageProfile = view.findViewById(R.id.imageProfile)
        imageProfile.setOnClickListener(clickListener)
        startWorkWithProfileImage()

        llBackName = view.findViewById(R.id.llBackName)
        llBackAge = view.findViewById(R.id.llBackAge)
        llBackSex = view.findViewById(R.id.llBackSex)
        fieldName = view.findViewById(R.id.fieldName)
        fieldAge = view.findViewById(R.id.fieldAge)
        fieldSex = view.findViewById(R.id.fieldSex)
        initInfo()

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

    private fun initInfo() {
        fieldName.text = userDataHolder.getUserDataString(UserDataType.NAME)
            ?: getString(R.string.profile_no_info)
        fieldAge.text = userDataHolder.getUserDataInt(UserDataType.AGE)?.toString()
            ?: getString(R.string.profile_no_info)
        fieldSex.text = userDataHolder.getUserDataString(UserDataType.SEX)
            ?: getString(R.string.profile_no_info)
        val uriImage = userDataHolder.getUserDataString(UserDataType.URI)
        if (uriImage != null) {
            imageProfile.setImageURI(Uri.parse(uriImage))
        }
    }


    private val clickListener = View.OnClickListener { p0 ->
        when (p0) {
            imageProfile -> {
                profileImageLauncher.launch(imagePickIntent)
            }
            btnEdit -> {
                btnEdit.isClickable = false
                btnSave.visibility = View.VISIBLE

                fieldNameEdit = EditText(activity)
                createEditView(fieldName, fieldNameEdit, llBackName)
                fieldAgeEdit = EditText(activity)
                fieldAgeEdit.inputType = TYPE_CLASS_NUMBER
                createEditView(fieldAge, fieldAgeEdit, llBackAge)
                createEditSex()
            }
            btnSave -> {
                btnSave.visibility = View.GONE
                btnEdit.isClickable = true

                saveEditView(fieldName, fieldNameEdit, llBackName)
                saveEditView(fieldAge, fieldAgeEdit, llBackAge)
                saveEditSex()
            }
            btnStatistic -> {

            }
            btnSettings -> {
                findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
            }
        }
    }

    private val wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT
    private val lParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
    private val maxLines = 1
    private val editTextSize = 22F

    @SuppressLint("ResourceAsColor")
    private fun createEditView(field: TextView, fieldEdit: EditText, llBack: LinearLayout) {
        fieldEdit.maxLines = maxLines
        fieldEdit.setPadding(0,0,0,0)
        fieldEdit.textSize = editTextSize
        val current = field.text.toString()
        llBack.removeView(field)
        fieldEdit.setText(current)
        llBack.addView(fieldEdit, lParams)
    }

    private fun createEditSex() {
        val currentSex = fieldSex.text.toString()
        llBackSex.removeView(fieldSex)
        sexMan = RadioButton(activity)
        sexWoman = RadioButton(activity)
        sexOther = RadioButton(activity)
        setRadioImageText(sexMan, R.drawable.sex_man)
        setRadioImageText(sexWoman, R.drawable.sex_woman)
        setRadioImageText(sexOther, R.drawable.sex_other)

        fieldSexEdit = RadioGroup(activity)
        fieldSexEdit.orientation = LinearLayout.HORIZONTAL
        fieldSexEdit.addView(sexMan)
        fieldSexEdit.addView(sexWoman)
        fieldSexEdit.addView(sexOther)
        when (currentSex) {
            getString(R.string.profile_sex_man) -> sexMan.isChecked = true
            getString(R.string.profile_sex_woman) -> sexWoman.isChecked = true
            getString(R.string.profile_sex_other) -> sexOther.isChecked = true
        }
        llBackSex.addView(fieldSexEdit, lParams)
    }

    private fun saveEditView(field: TextView, fieldEdit: EditText, llBack: LinearLayout) {
        val saving = fieldEdit.text.toString()
        when (field) {
            fieldName -> userDataHolder.setUserData(UserDataType.NAME, saving)
            fieldAge -> {
                if (saving != getString(R.string.profile_no_info))
                    userDataHolder.setUserData(UserDataType.AGE, saving.toInt())
            }
        }
        llBack.removeView(fieldEdit)
        field.text = saving
        llBack.addView(field, lParams)
    }

    private fun saveEditSex() {
        val savingSex = when (fieldSexEdit.checkedRadioButtonId) {
            sexMan.id -> getString(R.string.profile_sex_man)
            sexWoman.id -> getString(R.string.profile_sex_woman)
            sexOther.id -> getString(R.string.profile_sex_other)
            else -> getString(R.string.profile_no_info)
        }
        userDataHolder.setUserData(UserDataType.SEX, savingSex)
        llBackSex.removeView(fieldSexEdit)
        fieldSex.text = savingSex
        llBackSex.addView(fieldSex, lParams)
    }

    private val sizeFixForManIcon = 30
    private val sizeFixForOtherIcons = 20

    private fun setRadioImageText(radioButton: RadioButton, drawableId: Int){
        val spannable = "_   ".toSpannable()
        object : DynamicDrawableSpan(){
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun getDrawable(): Drawable {
                val drawable: Drawable = context!!.resources.getDrawable(drawableId, null)
                val sizeFix = when (drawableId) {
                    R.drawable.sex_man -> sizeFixForManIcon
                    else -> sizeFixForOtherIcons
                }
                drawable.setBounds(
                    0,
                    0,
                    drawable.intrinsicWidth / sizeFix,
                    drawable.intrinsicHeight / sizeFix
                )
                return drawable
            }
        }.also { spannable[0..1] = it }
        radioButton.text = spannable
    }

    private fun startWorkWithProfileImage() {
        imagePickIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        imagePickIntent.type = "image/*"
        profileImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imData = result.data
                val selectedImage = imData?.data
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    requireActivity().contentResolver.takePersistableUriPermission(
                        selectedImage!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                imageProfile.setImageURI(null)
                imageProfile.setImageURI(selectedImage)
                if (selectedImage != null) {
                    userDataHolder.setUserData(UserDataType.URI, selectedImage.toString())
                }
            }
        }
    }

}

