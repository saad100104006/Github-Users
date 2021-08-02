package com.tawk.to.viewModelTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.tanvir.xxnetwork.viewModelTest.TestRxJavaRule
import com.tawk.to.db.dao.UserDao
import com.tawk.to.db.entity.Note
import com.tawk.to.db.entity.Profile
import com.tawk.to.db.entity.User
import com.tawk.to.db.entity.UserDetails
import com.tawk.to.network.ApiService
import com.tawk.to.ui.search.SearchRepository
import com.tawk.to.ui.search.SearchViewModel
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
class MovieDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var rxJavaRule: TestRule = TestRxJavaRule()

    @Mock
    private lateinit var favoriteDao: UserDao
    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var repository: SearchRepository
    var lifecycle: Lifecycle? = null
    lateinit var viewModel: SearchViewModel
    lateinit var user: UserDetails
    @Mock
    private lateinit var observer: Observer<in String>


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        user = UserDetails(User("abc", 1, "testurl"), Note("abc", "xyz"), Profile("abc", "abc",
                "", "", "abc@gmail.com", 1, 1))
        repository = SearchRepository(favoriteDao)
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun `Verify livedata values changes on event`() {
        assertNotNull(viewModel.updateSearchQuery("testQuery"))
        viewModel.searchQueryLD.observeForever(observer)
        viewModel.updateSearchQuery("")
        verify(observer).onChanged("testQuery")
    }


    @Test
    fun test_search() {
        `when`(favoriteDao.searchUser("testQuery")).thenReturn(listOf(user))
        viewModel.updateSearchQuery("testQuery")
        viewModel.searchQueryLD.value.let {
            assertTrue(it == "testQuery")
        }
    }

}