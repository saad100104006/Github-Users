package com.tawk.to.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.tawk.to.R
import com.tawk.to.databinding.ProfileFragmentBinding
import com.tawk.to.ktx.hideKeyboard
import com.tawk.to.ktx.toEditable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val args: ProfileFragmentArgs by navArgs()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var binding: ProfileFragmentBinding

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModel.AssistedFactory
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModel.provideFactory(profileViewModelFactory, args.userName)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ProfileFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userDetails.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.user.avatarUrl)
                .placeholder(R.drawable.ic_github)
                .into(binding.profileImageView)

            binding.noteInputLayout.editText?.text = it?.note?.note?.toEditable() ?: "".toEditable()

            it.profile?.let { profile ->
                binding.followerCountTv.text = profile.followers.toString()
                binding.followingCountTv.text = profile.following.toString()
                binding.nameTv.text = profile.name
                binding.companyTv.text = profile.company
                binding.blogTv.text = profile.blog
            }
        }


        binding.updateButton.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe({
                requireView().hideKeyboard()

                val note = binding.noteInputLayout.editText?.text.toString()
                viewModel.initAction(UpdateNote(note))
            }, Throwable::printStackTrace)
            .addTo(compositeDisposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}