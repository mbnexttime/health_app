package com.example.psyhealthapp.user.statistics.reaction

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.EmptyContentFragment
import com.example.psyhealthapp.user.testing.results.MovingReactionTestResultList
import com.example.psyhealthapp.user.testing.results.ReactionTestResultList

class ReactionPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val reactionResultList: ReactionTestResultList,
    private val complexReactionTestResultList: ReactionTestResultList,
    private val movingReactionTestResultList: MovingReactionTestResultList
) : FragmentStatePagerAdapter(fragmentManager) {
    private val pages = listOf("простая", "сложная", "движ")

    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> if (reactionResultList.results.isEmpty()) EmptyContentFragment.newInstance(
                R.string.reaction_placeholder_text,
                R.drawable.ic_smile1
            ) else ReactionSubFragment.newInstance(
                reactionResultList
            )
            1 -> if (complexReactionTestResultList.results.isEmpty()) EmptyContentFragment.newInstance(
                R.string.reaction_placeholder_text,
                R.drawable.ic_smile2
            ) else ReactionSubFragment.newInstance(
                complexReactionTestResultList
            )
            else -> if (movingReactionTestResultList.results.isEmpty()) EmptyContentFragment.newInstance(
                R.string.reaction_placeholder_text,
                R.drawable.ic_smile3
            ) else MovingReactionSubFragment.newInstance(
                movingReactionTestResultList
            )
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return pages[position]
    }
}