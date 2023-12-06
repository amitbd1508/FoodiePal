package me.amitghosh.foodiepal.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.adapter.ScreenSlidePagerAdapter
import me.amitghosh.foodiepal.adapter.ViewPagerAdapter
import me.amitghosh.foodiepal.databinding.ActivityMainBinding
import me.amitghosh.foodiepal.fragments.BlogFragment
import me.amitghosh.foodiepal.fragments.MealPlannerFragment
import me.amitghosh.foodiepal.fragments.RecipeFragment
import me.amitghosh.foodiepal.transformar.ZoomOutPageTransformer


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var prevMenuItem: MenuItem? = null
    lateinit var recipeFragment: RecipeFragment
    lateinit var mealPlannerFragment: MealPlannerFragment
    lateinit var blogFragment: BlogFragment

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolbar)

        auth = Firebase.auth

        binding.viewpager.setPageTransformer(ZoomOutPageTransformer())

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_call -> {
                    binding.viewpager.currentItem = 0
                    true
                }
                R.id.action_chat -> {
                    binding.viewpager.currentItem = 1
                    true
                }
                R.id.action_contact -> {
                    binding.viewpager.currentItem = 2
                    true
                }
                else -> {
                    binding.viewpager.currentItem = 0
                    true
                }
            }
        }

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
                prevMenuItem?.setChecked(false) ?: binding.bottomNavigation.getMenu().getItem(0).setChecked(false)
                Log.d("page", "onPageSelected: $position")
                binding.bottomNavigation.getMenu().getItem(position).setChecked(true)
                prevMenuItem = binding.bottomNavigation.getMenu().getItem(position)
                Log.e("Selected_Page", position.toString())
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        });

        setupViewPager(binding.viewpager);
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


    private fun setupViewPager(viewPager: ViewPager2) {
        val adapter = ScreenSlidePagerAdapter(this)

        adapter.createFragment(1)
        adapter.createFragment(2)
        adapter.createFragment(3)
        viewPager.adapter = adapter
    }
}