package me.amitghosh.foodiepal.fragments

import BlogAdapter
import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.databinding.FragmentBlogBinding
import me.amitghosh.foodiepal.model.Blog
import me.amitghosh.foodiepal.model.Meal
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale


/**
 * A simple [Fragment] subclass.
 * Use the [BlogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlogFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentBlogBinding
    var blogs = ArrayList<Blog>();
    val initialDate = Calendar.getInstance()
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    lateinit var currentUserEmail: String
    lateinit var adapter: BlogAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlogBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        setUpRecipeList()
        loadData()

        binding.fabAddPost.setOnClickListener {
            showCreateMealPlanner()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun loadData() {
        db.collection("blogs")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val blog = document.toObject(Blog::class.java)
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    blogs.add(blog)
                }
                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    private fun showCreateMealPlanner() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_blog_dialog)

        val etTitle = dialog.findViewById(R.id.etTitle) as EditText
        val etContent = dialog.findViewById(R.id.etContent) as EditText


        val addBtn = dialog.findViewById(R.id.btnBlogAdd) as Button
        addBtn.setOnClickListener {
            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            val currentDateandTime = sdf.format(Date())
            val blog = Blog(etTitle.text.toString(), etContent.text.toString(), currentDateandTime)
            db.collection("blogs")
                .add(blog)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                    Toast.makeText(context, "Blog Added", Toast.LENGTH_LONG).show()
                    blogs.add(blog)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()

                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    Toast.makeText(context, "Failed to add in database", Toast.LENGTH_LONG).show()
                    dialog.dismiss()

                }
            dialog.dismiss()
        }

        val cancelBtn = dialog.findViewById(R.id.btnBlogCancel) as Button
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setUpRecipeList() {


        adapter = BlogAdapter(blogs, requireContext());

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
         * @return A new instance of fragment BlogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlogFragment().apply {
            }
    }

}