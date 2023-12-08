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
import me.amitghosh.foodiepal.model.Recipe

class RecipeAdapter(private val recipes: List<Recipe>): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val recipeItem: CardView
        val name: TextView
        val image: ImageView

        init {
            recipeItem = view.findViewById(R.id.recipeItem)
            name = view.findViewById(R.id.name)
            image = view.findViewById(R.id.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false);
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = recipes[position].name
        holder.recipeItem.setOnClickListener {
            Log.d("Adapter", "Clicked $position")
        }
    }
}