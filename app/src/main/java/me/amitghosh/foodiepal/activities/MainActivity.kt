package me.amitghosh.foodiepal.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.adapter.ScreenSlidePagerAdapter
import me.amitghosh.foodiepal.adapter.ViewPagerAdapter
import me.amitghosh.foodiepal.databinding.ActivityMainBinding
import me.amitghosh.foodiepal.fragments.AboutMeFragment
import me.amitghosh.foodiepal.fragments.BlogFragment
import me.amitghosh.foodiepal.fragments.ContactFragment
import me.amitghosh.foodiepal.fragments.MealPlannerFragment
import me.amitghosh.foodiepal.fragments.RecipeFragment
import me.amitghosh.foodiepal.transformar.ZoomOutPageTransformer
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var prevMenuItem: MenuItem? = null

    private lateinit var auth: FirebaseAuth

    lateinit var fragments: List<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)

        auth = Firebase.auth
        fragments = listOf(
            RecipeFragment(),
            MealPlannerFragment(),
            BlogFragment(),
            ContactFragment(),
            AboutMeFragment()
        )

        setupBottomNavigation();

        setupViewPager();

        val adapter = ScreenSlidePagerAdapter(fragments, supportFragmentManager, lifecycle)

        binding.viewpager.adapter = adapter

        setupTabLayout()
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewpager) {
                tab, position ->
            when(position) {
                0-> tab.text = "Recipe"
                1-> tab.text = "Meal Planner"
                2-> tab.text = "Blog"
                3-> tab.text = "Contact"
                4-> tab.text = "About Me"
            }
        }.attach()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_recipe -> {
                    binding.viewpager.currentItem = 0
                    true
                }
                R.id.action_meal -> {
                    binding.viewpager.currentItem = 1
                    true
                }
                R.id.action_blog -> {
                    binding.viewpager.currentItem = 2
                    true
                }
                else -> {
                    binding.viewpager.currentItem = 0
                    true
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.viewpager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle
            // the Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            binding.viewpager.currentItem = binding.viewpager.currentItem - 1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.new_game -> {
                true
            }
            R.id.help -> {
                auth.signOut()
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setupViewPager() {

        binding.viewpager.setPageTransformer(ZoomOutPageTransformer())

        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                prevMenuItem?.setChecked(false) ?: binding.bottomNavigation.menu.getItem(0).setChecked(false)
                try{
                    Log.d("page", "onPageSelected: $position")
                    binding.bottomNavigation.getMenu().getItem(position).setChecked(true)
                    prevMenuItem = binding.bottomNavigation.getMenu().getItem(position)
                    Log.e("Selected_Page", position.toString())
                }catch (ex: Exception) {
                    Log.d("MainActivity", "Array out of bounds")
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        });


    }
}