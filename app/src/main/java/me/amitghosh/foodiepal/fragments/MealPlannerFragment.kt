package me.amitghosh.foodiepal.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.adapter.MealPlannerAdapter
import me.amitghosh.foodiepal.adapter.RecipeAdapter
import me.amitghosh.foodiepal.databinding.FragmentMealPlannerBinding
import me.amitghosh.foodiepal.databinding.FragmentRecipeBinding
import me.amitghosh.foodiepal.model.Meal
import me.amitghosh.foodiepal.model.Recipe

/**
 * A simple [Fragment] subclass.
 * Use the [MealPlannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealPlannerFragment : Fragment() {
    lateinit var binding: FragmentMealPlannerBinding
    val mealPlans = ArrayList<Meal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMealPlannerBinding.inflate(inflater, container, false)

        setUpMealPlanList()
        binding.fabCreateRecipe.setOnClickListener {
            showCreateMealPlanner()
        }


        return binding.root
    }

    private fun setUpMealPlanList() {

        val adapter: MealPlannerAdapter = MealPlannerAdapter(mealPlans);

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun showCreateMealPlanner() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_meal_panner_dialog)

        val etDay = dialog.findViewById(R.id.etDay) as EditText
        val etPlan = dialog.findViewById(R.id.etPlan) as EditText

        val addBtn = dialog.findViewById(R.id.btnMealAdd) as Button
        addBtn.setOnClickListener {
            val meal = Meal(etDay.text.toString(), etPlan.text.toString())
            mealPlans.add(meal)
            dialog.dismiss()
        }

        val cancelBtn = dialog.findViewById(R.id.btnMealCancel) as Button
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MealPlannerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MealPlannerFragment().apply {
            }
    }
}