package com.tawk.to.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import com.tawk.to.databinding.SearchFragmentBinding
import com.tawk.to.ktx.hideKeyboard
import com.tawk.to.ktx.lazyExecute
import com.tawk.to.ktx.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private val adapter by lazy { SearchAdapter() }
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // show soft keyboard on page open
        requireView().lazyExecute {
            binding.searchView.showKeyboard()
        }

        binding.recyclerView.adapter = adapter

        viewModel.searchedUserLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // open profile fragment with debounce effect
        adapter.getClickSubject().debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                requireView().hideKeyboard()
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToProfileFragment(it.user.userName))
            }, Throwable::printStackTrace)
            .addTo(compositeDisposable)

        // update live data with search text
        binding.searchView.queryTextChanges()
            .debounce(100, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .subscribe({
                viewModel.updateSearchQuery(it.toString())
            }, Throwable::printStackTrace)
            .addTo(compositeDisposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // clear all disposables
        compositeDisposable.clear()
    }

    companion object {
        fun newInstance() = SearchFragment()
        private const val TAG = "SearchFragment"
    }
}