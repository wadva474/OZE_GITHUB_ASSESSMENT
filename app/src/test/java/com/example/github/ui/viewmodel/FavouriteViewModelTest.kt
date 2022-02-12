package com.example.github.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.github.extension.observeWithMock
import com.example.github.local.model.GithubUser
import com.example.github.remote.repository.GithubUserRepository
import com.example.github.remote.repository.RxSchedulers
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verifySequence
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FavouriteViewModelTest {
    @MockK
    private lateinit var githubUserRepository: GithubUserRepository

    @MockK
    lateinit var rxSchedulers: RxSchedulers
    @InjectMockKs
    lateinit var favouritesViewModel: FavouriteViewModel

    private val listOfUsers = listOf(
        GithubUser(
            "wadlagos",
            "https://wadud",
            "https//gateway",
            "https//gateway",
            "https//gateway"
        ),
        GithubUser(
            "Lagos",
            "https://lagos",
            "https//gateway",
            "https//gateway",
            "https//gateway"
        )
    )

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        every { rxSchedulers.io() } returns Schedulers.trampoline()
        every { rxSchedulers.mainThread() } returns Schedulers.trampoline()
    }

        @Test
        fun getGithubUser() {
            coEvery { githubUserRepository.fetchAllUsers() } returns Flowable.just(listOfUsers)
            val githubUserObserver = favouritesViewModel.githubUser.observeWithMock()
            favouritesViewModel.getAllUsers()
            val slot = slot<List<GithubUser>>()
            verifySequence {
                githubUserObserver.apply {
                    onChanged(capture(slot))
                }
            }
            val githubUsers = slot.captured
            Assert.assertEquals(githubUsers, listOfUsers)
        }

}