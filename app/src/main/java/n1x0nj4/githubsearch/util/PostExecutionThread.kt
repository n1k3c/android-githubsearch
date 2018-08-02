package n1x0nj4.githubsearch.util

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}
