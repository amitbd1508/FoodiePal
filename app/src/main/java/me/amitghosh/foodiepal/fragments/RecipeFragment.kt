package me.amitghosh.foodiepal.fragments

import android.app.Dialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.adapter.RecipeAdapter
import me.amitghosh.foodiepal.databinding.FragmentRecipeBinding
import me.amitghosh.foodiepal.model.Recipe

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentRecipeBinding
    val recipes = ArrayList<Recipe>()
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    lateinit var currentUserEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        currentUserEmail = auth.currentUser?.email.toString()

        setUpRecipeList();
        loadData();

        binding.fabCreateRecipe.setOnClickListener{
            showCreateRecipeDialog()
        }



        return binding.root;
    }

    private fun loadData() {
        db.collection("recipe")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val recipe = document.toObject(Recipe::class.java)
                    Log.d(TAG, "${document.id} => ${document.data}")
                    recipes.add(recipe)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }


    private fun showCreateRecipeDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_recipe_dialog)

        val etRecipe = dialog.findViewById(R.id.etRecipeName) as EditText
        val etIngredients = dialog.findViewById(R.id.etIngredients) as EditText
        val etInstructions = dialog.findViewById(R.id.etInstructions) as EditText
        val etCookingTime = dialog.findViewById(R.id.etCookingTime) as EditText
        val rating = dialog.findViewById(R.id.userRating) as RatingBar

        val addBtn = dialog.findViewById(R.id.btnAdd) as Button
        addBtn.setOnClickListener {
            val recipe = Recipe(etRecipe.text.toString(), etIngredients.text.toString(), etInstructions.text.toString(),android.R.drawable.btn_star, rating.rating, etCookingTime.text.toString().toDouble(), currentUserEmail)
            recipes.add(recipe)
            db.collection("recipe")
                .add(recipe)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                    Toast.makeText(context, "Recipe Added", Toast.LENGTH_LONG).show()
                    dialog.dismiss()


                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(context, "Failed to add in database", Toast.LENGTH_LONG).show()
                    dialog.dismiss()

                }
        }

        val cancelBtn = dialog.findViewById(R.id.btnCancel) as Button
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setUpRecipeList() {
        val adapter: RecipeAdapter = RecipeAdapter(recipes);

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecipeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecipeFragment().apply {
            }
    }
}