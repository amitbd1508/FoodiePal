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

        binding.ivCallIcon.setOnClickListener {
            val phoneNumber = binding.tvPhone.text.toString()
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callIntent)
        }

        binding.ivTextIcon.setOnClickListener {
            val phoneNumber = binding.tvPhone.text.toString()
            val smsUri = Uri.parse("smsto:$phoneNumber")
            val smsIntent = Intent(Intent.ACTION_VIEW, smsUri)
            smsIntent.putExtra("sms_body", "Hello, thank you so much from FoodiePal.")
            startActivity(smsIntent)
        }

        binding.ivEmailIcon.setOnClickListener {
            val emailAddress = binding.tvEmail.text.toString()
            val subject = "Thank you"
            val body = "Hey, this is a thank you email from FoodiePal app"

            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:$emailAddress")
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, body)

            startActivity(Intent.createChooser(emailIntent, "Send Email"))
        }

        return binding.root
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