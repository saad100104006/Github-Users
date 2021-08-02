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
    private lateinit var movieServiceApi: ApiService
    @Mock
    private lateinit var favoriteDao: UserDao
    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var repository: SearchRepository
/*    private lateinit var movie: Movie
    private lateinit var movieGenre: MovieGenre
    private lateinit var movieResponse: MovieResponse*/
    var lifecycle: Lifecycle? = null
    lateinit var viewModel: SearchViewModel
    var id = 1
    var vote_count = 50
    var popularity = 7
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

        repository = SearchRepository(favoriteDao)
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun `Verify livedata values changes on event`() {
        assertNotNull(viewModel.updateSearchQuery("aaa"))
        viewModel.searchQueryLD.observeForever(observer)
        viewModel.updateSearchQuery("")
        verify(observer).onChanged("aaa")
    }


    @Test
    fun test_movie_details() {
        `when`(favoriteDao.searchUser("")).thenReturn(listOf(user))
        viewModel.updateSearchQuery("")
        viewModel.searchQueryLD.value.let {
           assertTrue(it == "id")
        }

    }

}