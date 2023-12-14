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
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(inflater, container, false)

        setUpRecipeList();

        binding.fabCreateRecipe.setOnClickListener{
            showCreateRecipeDialog()
        }



        return binding.root;
    }

    private fun showCreateRecipeDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_recipe_dialog)

        val etRecipe = dialog.findViewById(R.id.etRecipeName) as EditText
        val etIngredients = dialog.findViewById(R.id.etIngredients) as EditText
        val etInstructions = dialog.findViewById(R.id.etInstructions) as EditText

        val addBtn = dialog.findViewById(R.id.btnAdd) as Button
        addBtn.setOnClickListener {
            val recipe = Recipe(etRecipe.text.toString(), etIngredients.text.toString(), etInstructions.text.toString())
            recipes.add(recipe)
            dialog.dismiss()
        }

        val cancelBtn = dialog.findViewById(R.id.btnCancel) as Button
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setUpRecipeList() {
        recipes.addAll( listOf<Recipe>(
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction"),
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction"),
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction"),
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction"),
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction"),
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction"),
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction"),
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction"),
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction"),
            Recipe("Crossan Egg and Chease", "Some description", "someinstruction")
        ))

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