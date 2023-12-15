package me.amitghosh.foodiepal.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import me.amitghosh.foodiepal.R
import me.amitghosh.foodiepal.databinding.FragmentContactBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment() {

    lateinit var binding: FragmentContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.phoneSection.setOnClickListener{
            call(binding.tvPhone.text.toString())
        }

        binding.emailSection.setOnClickListener{
            email(binding.tvemail.text.toString());
        }
        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun email(recipientEmail: String) {

        // Create an Intent with the ACTION_SENDTO action and the email Uri
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$recipientEmail")
            putExtra(Intent.EXTRA_SUBJECT, "Subject of the email")
            putExtra(Intent.EXTRA_TEXT, "Body of the email")
        }

        // Check if there's an app that can handle this Intent
        if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
            // Start the email client activity
            startActivity(emailIntent)
        } else {
            // Handle the case where no app can handle the email Intent
            // (e.g., display a message to the user)
            Toast.makeText(requireContext(), "No app available to handle the email.", Toast.LENGTH_LONG).show()

        }
    }

    fun call(phoneNumber: String) {

        // Create an Intent with the ACTION_DIAL action and the phone number Uri
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))

        // Check if there's an app that can handle this Intent
        if (dialIntent.resolveActivity(requireActivity().packageManager) != null) {
            // Start the dialer activity
            startActivity(dialIntent)
        } else {
            // Handle the case where no app can handle the dial Intent
            // (e.g., display a message to the user)
            Toast.makeText(requireContext(), "No app available to handle the call.", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}