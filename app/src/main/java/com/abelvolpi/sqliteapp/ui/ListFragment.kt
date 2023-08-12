package com.abelvolpi.sqliteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abelvolpi.sqliteapp.R
import com.abelvolpi.sqliteapp.data.Person
import com.abelvolpi.sqliteapp.data.PersonRepository
import com.abelvolpi.sqliteapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var personRepository: PersonRepository
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        personRepository = PersonRepository(requireContext())

        with(binding) {
            addButton.setOnClickListener {
                navController.navigate(R.id.action_ListFragment_to_CreateFragment)
            }

            personRecyclerView.apply {
                val list = personRepository.readAllData()

                adapter = PersonAdapter(
                    list as ArrayList<Person>,
                    ::removePerson,
                    ::viewPerson,
                    ::editPerson
                )
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            }
        }
    }

    private fun editPerson(id: String) {
        navController.navigate(
            ListFragmentDirections.actionListFragmentToEditFragment(id)
        )
    }

    private fun removePerson(id: String) {
        personRepository.removePerson(id)
    }

    private fun viewPerson(id: String) {
        navController.navigate(
            ListFragmentDirections.actionListFragmentToViewFragment(id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}