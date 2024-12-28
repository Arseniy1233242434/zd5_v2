package com.example.cinema.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R

class UserAdapter(
    private val userViewModel: UserViewModel,
    private var users: MutableList<Client>,
    private val onEditClick: (Client) -> Unit,
    private val onDeleteClick: (Client) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pass: TextView = itemView.findViewById(R.id.pass)
        val email: TextView = itemView.findViewById(R.id.email)
        val data: TextView = itemView.findViewById(R.id.data)
        val role: Spinner = itemView.findViewById(R.id.role)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }
    private val roleItems = arrayOf("Работник", "Клиент")
    private lateinit var roleAdapter: ArrayAdapter<String>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var user = users[position]
        holder.pass.text = user.password
        holder.email.text = user.email
            roleAdapter = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, roleItems)
            roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.role.adapter = roleAdapter
        val selectedPosition = if (user.role == "Клиент") 1 else 0
        holder.role.setSelection(selectedPosition)
        holder.data.text = user.date
        holder.editButton.setOnClickListener {
            val updatedUser = user.copy(role = holder.role.selectedItem.toString(),password = holder.pass.text.toString(),
                email = holder.email.text.toString(), date = holder.data.text.toString())
            updateUserAtPosition(position, updatedUser)
            userViewModel.editUser(updatedUser)
        }

        holder.deleteButton.setOnClickListener { onDeleteClick(user) }
    }

    override fun getItemCount(): Int = users.size

    fun updateUserAtPosition(position: Int, updatedUser: Client) {
        users[position] = updatedUser
        notifyItemChanged(position)
    }
    fun updateUsers(newUsers: List<Client>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }
}