package com.example.psyhealthapp.user.testing.movingObject

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.MovingObjectReactionTestInstructionFragmentBinding

class MovingObjectReactionTestInstructionFragment : Fragment(R.layout.moving_object_reaction_test_instruction_fragment) {

    private val viewBinding by viewBinding(MovingObjectReactionTestInstructionFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.button.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.movingObjectTestReaction)
        }
    }
}