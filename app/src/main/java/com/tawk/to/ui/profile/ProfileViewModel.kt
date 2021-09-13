package com.tawk.to.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tawk.to.db.entity.Note
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


class ProfileViewModel @AssistedInject constructor(
    private val repository: ProfileRepository,
    @Assisted private val userName: String
) : ViewModel() {
    init {
        profileUpdate(userName)
    }

    val isNoteUpdated: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val userDetails = repository.getUserDetails(userName)

    //update note if it's not empty
    fun initAction(actions: Actions) {
        when (actions) {
            is UpdateNote -> {
                repository.updateNote(Note(userName, actions.noteText))
                if(actions.noteText.isNotEmpty()) {
                    isNoteUpdated.postValue(true)
                }
            }
        }
    }
    //update the profile
     fun profileUpdate(userName: String){
        repository.updateProfile(userName)

    }

    override fun onCleared() {
        super.onCleared()
        repository.clearResources()
    }


    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(userName: String): ProfileViewModel
    }

    companion object {
        private const val TAG = "ProfileViewModel"
        fun provideFactory(
            assistedFactory: AssistedFactory,
            userName: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(userName) as T
            }
        }
    }
}


sealed class Actions
data class UpdateNote(val noteText: String) : Actions()