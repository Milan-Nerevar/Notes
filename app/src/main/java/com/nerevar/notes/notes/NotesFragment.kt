package com.nerevar.notes.notes

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nerevar.notes.R
import com.nerevar.notes.detail.DetailFragment
import kotlinx.android.synthetic.main.fragment_notes.*
import org.koin.android.viewmodel.ext.android.viewModel

class NotesFragment : Fragment(), NotesContract.View {

    private val vm: NotesViewModel by viewModel()

    private val adapter by lazy { NotesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        vm.notes.observe(this, Observer {
            adapter.notes = it.toMutableList()
            swipeRefreshLayout.isRefreshing = false
        })

        vm.navigateToCreateDetail.observe(this, Observer { navigateToCreateNote() })
        vm.navigateToNoteDetail.observe(this, Observer { navigateToNoteDetail(it) })

        vm.loading.observe(this, Observer {
            recyclerView.isVisible = !it
            loading.isVisible = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_notes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_create_note -> navigateToCreateNote().let { true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        adapter.listener = { vm.showNote(it) }

        swipeRefreshLayout.setOnRefreshListener { vm.loadNotes() }

        vm.loadNotes()
    }

    override fun navigateToCreateNote() {
        NavHostFragment.findNavController(this).navigate(R.id.action_notesFragment_to_detailFragment, null)
    }

    override fun navigateToNoteDetail(id: Long) {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_notesFragment_to_detailFragment, DetailFragment.createArguments(id))
    }

}