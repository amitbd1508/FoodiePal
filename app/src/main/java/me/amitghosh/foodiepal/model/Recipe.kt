package me.amitghosh.foodiepal.model

import android.graphics.drawable.Drawable

data class Recipe(val recipe: String, val ingredients: String, val instructions: String, val image: Int, val rating: Float, val cookingTime: Double, val addedBy: String)