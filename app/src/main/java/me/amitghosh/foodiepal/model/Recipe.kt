package me.amitghosh.foodiepal.model

import android.graphics.drawable.Drawable

data class Recipe(val recipe: String ="", val ingredients: String = "", val instructions: String = "", val image: Int = 3, val rating: Float = 0.0f, val cookingTime: Double = 0.0, val addedBy: String = "")