package com.example.psyhealthapp.user.testing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.TestsListFragmentBinding

class TestsListFragment : Fragment(R.layout.tests_list_fragment) {

    private val viewBinding by viewBinding(TestsListFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tappingTestButton.setOnClickListener {
            findNavController().navigate(R.id.action_tests_graph_to_tapping_test_graph)
        }

        viewBinding.reactionTestButton.setOnClickListener {
            findNavController().navigate(R.id.action_tests_graph_to_reaction_test_graph)
        }

        viewBinding.complexReactionTestButton.setOnClickListener {
            findNavController().navigate(R.id.action_tests_list_to_complex_test_reaction_graph)
        }

        viewBinding.movingObjectTestButton.setOnClickListener {
            findNavController().navigate(R.id.action_tests_list_to_moving_object_reaction_test_graph)
        }
    }
}