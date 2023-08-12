package com.abelvolpi.sqliteapp.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.abelvolpi.sqliteapp.data.PersonRepository
import com.abelvolpi.sqliteapp.databinding.FragmentCreateBinding
import com.abelvolpi.sqliteapp.ext.setImageUsingGlide

class CreateFragment : Fragment() {

    private lateinit var personRepository: PersonRepository
    private lateinit var launcherForGallery: ActivityResultLauncher<String>
    private var imageUri: Uri? = null
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initResultContracts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        personRepository = PersonRepository(requireContext())

        with(binding){
            cardViewPhoto.setOnClickListener {
                launcherForGallery.launch("image/*")
            }
            saveButton.setOnClickListener {
                savePerson()
            }
        }
    }

    private fun savePerson() {
        with(binding){
            val name = personNameEditText.text.toString()
            val email = personEmailEditText.text.toString()
            val birthday = personBirthdayEditText.text.toString()

            personRepository.addPerson(
                name,
                email,
                birthday,
                imageUri.toString()
            )

            navController.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initResultContracts() {
        launcherForGallery = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                with(binding) {
                    withoutImageTextView.visibility = View.INVISIBLE
                    addPhotoImageView.visibility = View.INVISIBLE

                    imagePhoto.setImageUsingGlide(requireContext(),uri.toString())
                    imageUri = uri
                }
            }
        }
    }

}