package com.example.psyhealthapp.user.statistics.reaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.databinding.StatReactionBinding
import com.example.psyhealthapp.user.testing.results.ReactionTestResult
import com.example.psyhealthapp.user.testing.results.ReactionTestResultList

class Reaction : Fragment(R.layout.stat_reaction) {
    private val viewBinding by viewBinding(StatReactionBinding::bind)
    private lateinit var reactionPagerAdapter: ReactionPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val reactionTestResultList =
                it.getParcelable<ReactionTestResultList>(reactionTestResultListName)
            val complexReactionTestResultList = it.getParcelable<ReactionTestResultList>(
                complexReactionTestResultListName
            )
            reactionTestResultList?.let {
                complexReactionTestResultList?.let {
                    reactionPagerAdapter = ReactionPagerAdapter(
                        childFragmentManager,
                        reactionTestResultList,
                        complexReactionTestResultList
                    )
                    viewBinding.reactionPager.apply {
                        adapter = reactionPagerAdapter
                        swipeable = false
                    }
                }
            }
        }
    }

    companion object {
        private const val reactionTestResultListName = "reactionTestResultList"
        private const val complexReactionTestResultListName = "complexReactionTestResultListName"

        fun newInstance(
            reactionTestResultList: ReactionTestResultList,
            complexReactionTestResultList: ReactionTestResultList
        ): Reaction {
            val arguments = Bundle()
            arguments.putParcelable(reactionTestResultListName, reactionTestResultList)
            arguments.putParcelable(
                complexReactionTestResultListName,
                complexReactionTestResultList
            )
            val reaction = Reaction()
            reaction.arguments = arguments
            return reaction
        }
    }
}