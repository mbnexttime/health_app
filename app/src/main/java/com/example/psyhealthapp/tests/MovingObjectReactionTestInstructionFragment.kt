package com.example.psyhealthapp.tests

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.MovingObjectReactionTestInstructionBinding

class MovingObjectReactionTestInstructionFragment : Fragment(R.layout.moving_object_reaction_test_instruction) {

    private val viewBinding by viewBinding(MovingObjectReactionTestInstructionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.button.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.movingObjectTestReaction)
        }
    }
}