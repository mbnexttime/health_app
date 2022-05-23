package com.example.psyhealthapp.user.statistics.reaction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.databinding.StatReactionBinding
import com.example.psyhealthapp.user.testing.results.MovingReactionTestResultList
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

            val movingReactionTestResultList = it.getParcelable<MovingReactionTestResultList>(
                movingReactionTestResultListName
            )

            reactionTestResultList?.let {
                complexReactionTestResultList?.let {
                    movingReactionTestResultList?.let {
                        reactionPagerAdapter = ReactionPagerAdapter(
                            childFragmentManager,
                            reactionTestResultList,
                            complexReactionTestResultList,
                            movingReactionTestResultList
                        )
                        viewBinding.reactionPager.apply {
                            adapter = reactionPagerAdapter
                            swipeable = false
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val reactionTestResultListName = "reactionTestResultList"
        private const val complexReactionTestResultListName = "complexReactionTestResultListName"
        private const val movingReactionTestResultListName = "movingReactionTextResultListName"

        fun newInstance(
            reactionTestResultList: ReactionTestResultList,
            complexReactionTestResultList: ReactionTestResultList,
            movingReactionTestResultList: MovingReactionTestResultList
        ): Reaction {
            val arguments = Bundle()
            arguments.putParcelable(reactionTestResultListName, reactionTestResultList)
            arguments.putParcelable(
                complexReactionTestResultListName,
                complexReactionTestResultList
            )
            arguments.putParcelable(
                movingReactionTestResultListName,
                movingReactionTestResultList
            )
            val reaction = Reaction()
            reaction.arguments = arguments
            return reaction
        }
    }
}