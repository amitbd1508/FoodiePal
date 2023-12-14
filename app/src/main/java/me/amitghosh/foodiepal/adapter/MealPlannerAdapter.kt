package me.amitghosh.foodiepal.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.databinding.ItemRecipeBinding
import me.amitghosh.foodiepal.model.Meal
import me.amitghosh.foodiepal.model.Recipe

class MealPlannerAdapter(private val mealPlans: List<Meal>): RecyclerView.Adapter<MealPlannerAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val mealPlannerItem: CardView
        val tvDay: TextView
        val tvMeal: TextView

        init {
            mealPlannerItem = view.findViewById(R.id.mealPlannerItem)
            tvDay = view.findViewById(R.id.tvDay)
            tvMeal = view.findViewById(R.id.tvMeal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_planner_item, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mealPlans.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDay.text = mealPlans[position].day
        holder.tvMeal.text = mealPlans[position].plan
        holder.mealPlannerItem.setOnClickListener {
            Log.d("Adapter", "Clicked $position")
        }
    }
}