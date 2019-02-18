package com.nerevar.notes.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nerevar.notes.R
import kotlinx.android.synthetic.main.item_note.view.*

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var notes = mutableListOf<NotesContract.View.NoteItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var listener: ((Int) -> Unit)? = null

    override fun getItemCount(): Int = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)

        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val title = notes[position].title

        holder.itemView.setOnClickListener { listener?.invoke(position) }
        holder.text.text = title
    }

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.text
    }


}