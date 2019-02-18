package com.nerevar.notes.detail

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.jakewharton.rxbinding2.widget.textChanges
import com.nerevar.notes.R
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class DetailFragment : Fragment(), DetailContract.View {

    private val vm: DetailViewModel by viewModel {
        val id = arguments?.getLong(ARG_ID)

        return@viewModel if (id == null) {
            parametersOf(-1L)
        } else {
            parametersOf(id)
        }
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        vm.content.observe(this, Observer {
            text.setText(it)

            disposable = text.textChanges()
                .skip(1)
                .map { value -> value.toString() }
                .debounce(800, TimeUnit.MILLISECONDS)
                .subscribe{ value -> vm.createOrUpdateNote(value) }
        })

        vm.navigateUp.observe(this, Observer { navigateUp() })

        vm.loading.observe(this, Observer {
            loading.isVisible = it
            content.isVisible = !it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_delete_note -> vm.deleteNote().let { true }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.loadNote()
    }

    override fun onDestroyView() {
        disposable?.dispose()
        super.onDestroyView()
    }

    override fun navigateUp() {
        NavHostFragment.findNavController(this).navigateUp()
    }

    companion object {
        const val ARG_ID = "arg_id"

        fun createArguments(id: Long): Bundle = bundleOf(
            ARG_ID to id
        )
    }

}