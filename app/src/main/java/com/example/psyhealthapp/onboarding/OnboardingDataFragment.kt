package com.example.psyhealthapp.onboarding

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.OnboardingHandler
import com.example.psyhealthapp.core.UserDataHolder
import com.example.psyhealthapp.core.UserDataType
import com.example.psyhealthapp.databinding.OnboardingDataViewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@AndroidEntryPoint
class OnboardingDataFragment : Fragment(R.layout.onboarding_data_view) {

    private lateinit var binding: OnboardingDataViewBinding

    @Inject
    lateinit var userDataHolder: UserDataHolder

    @Inject
    lateinit var onboardingHandler: OnboardingHandler

    private val type2Title: EnumMap<UserDataType, DataViewHolder> =
        EnumMap(UserDataType::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = OnboardingDataViewBinding.bind(view)

        initializeTypes()

        binding.onboardingDataContinueButton.setOnClickListener {
            if (!checkDataAndRaiseIncorrect()) {
                Toast.makeText(
                    requireContext(),
                    R.string.onboarding_data_error_toast,
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            userDataHolder.setUserData(
                UserDataType.AGE,
                binding.onboardingDataAgeEdit.text.toString().toInt()
            )
            userDataHolder.setUserData(
                UserDataType.EMAIL,
                binding.onboardingDataEmailEdit.text.toString()
            )
            userDataHolder.setUserData(
                UserDataType.NAME,
                binding.onboardingDataNameEdit.text.toString()
            )

            onboardingHandler.onOnboardingFinish()
        }
    }

    private fun initializeTypes() {
        type2Title[UserDataType.AGE] =
            DataViewHolder(
                binding.onboardingDataAgeEdit, binding.onboardingDataAgeTitle
            ) {
                it.isNotBlank()
            }
        type2Title[UserDataType.NAME] =
            DataViewHolder(binding.onboardingDataNameEdit, binding.onboardingDataNameTitle) {
                it.isNotBlank()
            }
        type2Title[UserDataType.EMAIL] =
            DataViewHolder(binding.onboardingDataEmailEdit, binding.onboardingDataEmailTitle) {
                it.isNotBlank()
            }

        type2Title.forEach { (_, value) ->
            value.edit.addTextChangedListener {
                value.title.setTextColor(requireActivity().getColor(R.color.dark_blue))
            }
        }
    }

    private fun checkDataAndRaiseIncorrect(): Boolean {
        cleanMarks()
        var flag = true
        type2Title.forEach { (_, value) ->
            if (!value.check(value.edit.text.toString())) {
                flag = false
                value.title.setTextColor(requireActivity().getColor(R.color.red))
                value.edit.text.clear()
            }
        }
        return flag
    }

    private fun cleanMarks() {
        type2Title.forEach { (_, value) ->
            value.title.setTextColor(requireActivity().getColor(R.color.dark_blue))
        }
    }
}