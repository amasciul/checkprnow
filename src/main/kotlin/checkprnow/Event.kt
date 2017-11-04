package checkprnow

import com.google.gson.annotations.SerializedName

data class Event (@SerializedName("pull_request") val pullRequest: PullRequest)

data class PullRequest(val number: Int)