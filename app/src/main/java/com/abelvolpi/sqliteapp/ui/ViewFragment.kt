package com.abelvolpi.sqliteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abelvolpi.sqliteapp.data.Person
import com.abelvolpi.sqliteapp.data.PersonRepository
import com.abelvolpi.sqliteapp.databinding.FragmentViewBinding
import com.abelvolpi.sqliteapp.ext.setImageUsingGlide

class ViewFragment : Fragment() {

    private lateinit var personRepository: PersonRepository
    private var _binding: FragmentViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewBinding.inflate(inflater, container, false)
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
            setUpView(it)
        }
    }

    private fun setUpView(person: Person) {
        with(binding) {
            personNameTextView.text = person.name
            personEmailTextView.text = person.email
            personBirthdayTextView.text = person.birthDay
            personImageView.setImageUsingGlide(requireContext(), person.imageUri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}