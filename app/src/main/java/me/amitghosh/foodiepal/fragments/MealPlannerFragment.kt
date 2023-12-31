package me.amitghosh.foodiepal.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.set
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.adapter.MealPlannerAdapter
import me.amitghosh.foodiepal.databinding.FragmentMealPlannerBinding
import me.amitghosh.foodiepal.model.Meal
import me.amitghosh.foodiepal.model.Recipe
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [MealPlannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealPlannerFragment : Fragment() {
    lateinit var binding: FragmentMealPlannerBinding
    val mealPlans = ArrayList<Meal>()
    val initialDate = Calendar.getInstance()

    lateinit var adapter: MealPlannerAdapter

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
        binding = FragmentMealPlannerBinding.inflate(inflater, container, false)

        setUpMealPlanList()

        loadData()
        binding.fabCreateRecipe.setOnClickListener {
            showCreateMealPlanner()
        }


        return binding.root
    }

    private fun loadData() {
        mealPlans.clear()
        adapter.notifyDataSetChanged()
        db.collection("meal-planner")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val recipe = document.toObject(Meal::class.java)
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    mealPlans.add(recipe)
                }
                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    private fun setUpMealPlanList() {

        adapter = MealPlannerAdapter(mealPlans);

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun showCreateMealPlanner() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_meal_panner_dialog)

        val btnDay = dialog.findViewById(R.id.btnDay) as Button
        val etPlan = dialog.findViewById(R.id.etPlan) as EditText

        btnDay.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(), {
                        view, year, month, dayOfMonth ->
                    val date = Calendar.getInstance()
                    date.set(Calendar.YEAR, year)
                    date.set(Calendar.MONTH, month)
                    date.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val simpleDateFormat =
                        SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                    val formattedDate = simpleDateFormat.format(date.time)
                    btnDay.text = formattedDate


                }, initialDate.get(Calendar.YEAR), initialDate.get(Calendar.MONTH), initialDate.get(Calendar.DAY_OF_MONTH)
            )
// Show the dialog
            datePickerDialog.show()


        }

        val addBtn = dialog.findViewById(R.id.btnMealAdd) as Button
        addBtn.setOnClickListener {
            val meal = Meal(btnDay.text.toString(), etPlan.text.toString())

            db.collection("meal-plans")
                .add(meal)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                    Toast.makeText(context, "Meals Added", Toast.LENGTH_LONG).show()
                    mealPlans.add(meal)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()


                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    Toast.makeText(context, "Failed to add in database", Toast.LENGTH_LONG).show()
                    dialog.dismiss()

                }
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