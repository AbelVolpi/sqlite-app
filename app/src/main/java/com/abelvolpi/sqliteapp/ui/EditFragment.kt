package com.abelvolpi.sqliteapp.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abelvolpi.sqliteapp.data.Person
import com.abelvolpi.sqliteapp.data.PersonRepository
import com.abelvolpi.sqliteapp.databinding.FragmentEditBinding
import com.abelvolpi.sqliteapp.ext.setImageUsingGlide

class EditFragment : Fragment() {

    private lateinit var launcherForGallery: ActivityResultLauncher<String>
    private lateinit var personRepository: PersonRepository
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }
    private var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initResultContracts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        personRepository = PersonRepository(requireContext())

        val myArguments = arguments?.getString("id")
        val person = myArguments?.let { id ->
            personRepository.readOneData(id)
        }

        person?.let {
            setUpEditTexts(it)
            setUpButtons(person.id)
        }

    }

    private fun setUpButtons(id: String) {
        with(binding) {
            cardViewPhoto.setOnClickListener {
                launcherForGallery.launch("image/*")
            }

            saveButton.setOnClickListener {
                personRepository.editPerson(
                    id,
                    personNameEditText.text.toString(),
                    personEmailEditText.text.toString(),
                    personBirthdayEditText.text.toString(),
                    imageUri.toString()
                )
                navController.popBackStack()
            }
        }
    }

    private fun setUpEditTexts(person: Person) {
        with(binding) {
            personNameEditText.setText(person.name)
            personEmailEditText.setText(person.email)
            personBirthdayEditText.setText(person.birthDay)
            if (person.imageUri != "null"){
                imagePhoto.setImageUsingGlide(requireContext(), person.imageUri)
                withoutImageTextView.visibility = View.INVISIBLE
                addPhotoImageView.visibility = View.INVISIBLE
            }
        }
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