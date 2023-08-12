package com.abelvolpi.sqliteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abelvolpi.sqliteapp.data.Person
import com.abelvolpi.sqliteapp.data.PersonRepository
import com.abelvolpi.sqliteapp.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    private lateinit var personRepository: PersonRepository
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { findNavController() }

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
            setUpButton(person.id)
        }

    }

    private fun setUpButton(id: String) {
        with(binding) {
            saveButton.setOnClickListener {
                personRepository.editPerson(
                    id,
                    personNameEditText.text.toString(),
                    personEmailEditText.text.toString(),
                    personBirthdayEditText.text.toString()
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
        }
    }

}