package n1x0nj4.githubsearch.ui.search

import n1x0nj4.githubsearch.util.RxSchedulersOverrideRule
import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
abstract class BaseViewModelTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulersOverrideRule()

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    abstract fun setUp()
}