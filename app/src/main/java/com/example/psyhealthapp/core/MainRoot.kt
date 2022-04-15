package com.example.psyhealthapp.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.MainRootBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainRoot: Fragment(R.layout.main_root) {
    private lateinit var binding: MainRootBinding

    @Inject lateinit var userDataHolder: UserDataHolder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainRootBinding.bind(view)

        binding.root.text = "Привет ${userDataHolder.getUserDataString(UserDataType.NAME)}!"
    }
}