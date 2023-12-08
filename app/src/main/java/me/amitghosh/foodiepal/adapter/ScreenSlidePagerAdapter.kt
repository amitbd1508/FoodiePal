package me.amitghosh.foodiepal.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.amitghosh.foodiepal.fragments.BlogFragment
import me.amitghosh.foodiepal.fragments.MealPlannerFragment
import me.amitghosh.foodiepal.fragments.RecipeFragment

public class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val NUM_PAGES = 3

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when(position % NUM_PAGES) {
            0 -> RecipeFragment()
            1 -> MealPlannerFragment()
            2 -> BlogFragment()
            else -> RecipeFragment()
        }
    }
}

