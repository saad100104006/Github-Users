package com.tawk.to.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.tawk.to.R
import com.tawk.to.ktx.lazyExecute
import com.tawk.to.ktx.visibleIfOrGone
import com.tawk.to.ui.Connected
import com.tawk.to.ui.MainActivityViewModel
import com.tawk.to.ui.home.adapter.UserAdapter
import com.tawk.to.ui.home.adapter.UsersLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import com.tawk.to.databinding.HomeFragmentBinding
import com.tawk.to.ktx.safeExecute
import com.tawk.to.ui.Disconnected


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val adapter by lazy { UserAdapter() }
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        binding.recyclerView.adapter = adapter.withLoadStateFooter(UsersLoadStateAdapter())

        adapter.addLoadStateListener { loadState ->
            // show shimmering effect only when there is not previous data loaded and pagination refreshing
            val firstLoading = loadState.mediator?.refresh == LoadState.Loading && adapter.itemCount == 0
            binding.shimmerPlaceholder.visibleIfOrGone(firstLoading)
        }

        viewModel.getUsers().subscribe {
            adapter.submitData(lifecycle, it)
        }

        activityViewModel.getNetworkStatus().observe(viewLifecycleOwner) {
            // retry failed request loading when network is available
            if (it == Connected) {
                activityViewModel.updateNetworkStatus(Connected)
                adapter.retry()
            }
        }

        // open profile fragment with a debounce effect to eliminate unintentional multiple clicks
        adapter.getClickSubject().throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe({
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment(it.user.userName))
            }, Throwable::printStackTrace)
            .addTo(compositeDisposable)


        // if there is saved recyclerview state then load it
        savedInstanceState?.let {
            safeExecute {
                requireView().lazyExecute {
                    val recyclerViewState = savedInstanceState.getParcelable<LinearLayoutManager.SavedState>("recyclerViewState")
                    (binding.recyclerView.layoutManager as LinearLayoutManager).onRestoreInstanceState(recyclerViewState);
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        // save recyclerview info on orientation change
        safeExecute {
            val recyclerViewState = (binding.recyclerView.layoutManager as LinearLayoutManager).onSaveInstanceState()
            outState.putParcelable("recyclerViewState", recyclerViewState)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // clear all disposables
        compositeDisposable.clear()
    }


    companion object {
        fun newInstance() = HomeFragment()
        private const val TAG = "HomeFragment"
    }
}