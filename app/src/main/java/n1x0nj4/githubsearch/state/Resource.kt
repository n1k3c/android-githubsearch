package n1x0nj4.githubsearch.state

class Resource<out T> constructor(val status: ResourceState,
                                  val data: T?,
                                  val message: String?)