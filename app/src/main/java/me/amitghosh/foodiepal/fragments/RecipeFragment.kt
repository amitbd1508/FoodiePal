package me.amitghosh.foodiepal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.adapter.RecipeAdapter
import me.amitghosh.foodiepal.databinding.FragmentRecipeBinding
import me.amitghosh.foodiepal.model.Recipe

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(inflater, container, false)

        setUpRecipeList();

        return binding.root;
    }

    private fun setUpRecipeList() {
        val recipes: List<Recipe> = listOf<Recipe>(
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
        )

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
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}