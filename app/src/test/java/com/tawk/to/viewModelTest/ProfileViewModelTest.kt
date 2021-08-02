package com.tawk.to.viewModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.paging.PagingSource
import com.tanvir.xxnetwork.viewModelTest.TestRxJavaRule
import com.tawk.to.db.dao.NoteDao
import com.tawk.to.db.dao.ProfileDao
import com.tawk.to.db.dao.UserDao
import com.tawk.to.db.entity.Note
import com.tawk.to.db.entity.Profile
import com.tawk.to.db.entity.User
import com.tawk.to.db.entity.UserDetails
import com.tawk.to.network.Api
import com.tawk.to.network.ApiService
import com.tawk.to.ui.home.HomeRepository
import com.tawk.to.ui.home.HomeViewModel
import com.tawk.to.ui.profile.ProfileRepository
import com.tawk.to.ui.profile.ProfileViewModel
import com.tawk.to.ui.search.SearchRepository
import com.tawk.to.ui.search.SearchViewModel
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieServiceApi: ApiService

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    @Mock
    lateinit var a: PagingSource<Int, UserDetails>

    @Mock
    private lateinit var profileDao: ProfileDao

    @Mock
    private lateinit var noteDao: NoteDao

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var api: Api

    private lateinit var repository: ProfileRepository

    var lifecycle: Lifecycle? = null
    lateinit var viewModel: ProfileViewModel
    lateinit var user:UserDetails
    @Mock
    private lateinit var observer: Observer<in String>


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        user  = UserDetails(User("",1,""), Note("",""), Profile("","",
      "","","",1,1))
        repository = ProfileRepository(api,userDao, profileDao, noteDao)
        viewModel = ProfileViewModel(repository,"aa")
    }



    @Test
    fun test_profile_update() {
        `when`(repository.updateProfile("AA")).thenReturn(viewModel.profileUpdate("AA"))
            assertTrue( repository.updateProfile("AA") ==  viewModel.profileUpdate("AA"))
        }


}