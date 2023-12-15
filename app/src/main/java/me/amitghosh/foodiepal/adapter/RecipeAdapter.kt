package me.amitghosh.foodiepal.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.databinding.ItemRecipeBinding
import me.amitghosh.foodiepal.model.Recipe

class RecipeAdapter(private val recipes: List<Recipe>): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val recipeItem: View
        val tvRecipe: TextView
        val tvIngredents: TextView
        val tvInstructions: TextView
        val tvCookingTime: TextView
        val recipeImage: ImageView
        val userRating: RatingBar

        init {
            recipeItem = view.findViewById(R.id.recipeItem)
            tvRecipe = view.findViewById(R.id.recipeName)
            tvIngredents = view.findViewById(R.id.ingredients)
            tvInstructions = view.findViewById(R.id.instructions)
            tvCookingTime = view.findViewById(R.id.cookingTime)
            recipeImage = view.findViewById(R.id.recipeImage)
            userRating = view.findViewById(R.id.userRating)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvRecipe.text = recipes[position].recipe
        holder.tvIngredents.text = recipes[position].ingredients
        holder.tvInstructions.text = recipes[position].instructions
        holder.tvCookingTime.text = "${recipes[position].cookingTime} Hour"
        holder.userRating.rating = recipes[position].rating
        holder.recipeItem.setOnClickListener {
            Log.d("Adapter", "Clicked $position")
        }
    }
}