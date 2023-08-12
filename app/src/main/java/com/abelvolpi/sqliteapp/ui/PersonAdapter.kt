package com.abelvolpi.sqliteapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abelvolpi.sqliteapp.data.Person
import com.abelvolpi.sqliteapp.databinding.PersonItemBinding

class PersonAdapter(
    private val personList: ArrayList<Person>,
    private val removePerson: (String) -> Unit,
    private val viewPerson: (String) -> Unit,
    private val editPerson: (String) -> Unit
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: PersonItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person, position: Int) {
            with(binding) {
                personName.text = person.name
                personEmail.text = person.email
                personBirthday.text = person.birthDay
                cardLayout.setOnClickListener {
                   viewPerson.invoke(person.id)
                }
                editPersonButton.setOnClickListener {
                    editPerson.invoke(person.id)
                }
                deletePersonButton.setOnClickListener {
                    removePerson.invoke(person.id)
                    removeItem(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PersonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            personList[position],
            position
        )
    }

    override fun getItemCount() = personList.size

    private fun removeItem(campaignPosition: Int) {
        personList.removeAt(campaignPosition)
        notifyItemRemoved(campaignPosition)
        notifyItemRangeChanged(campaignPosition, personList.size)
    }

}