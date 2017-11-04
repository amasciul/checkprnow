package checkprnow

import com.google.gson.annotations.SerializedName

data class Event(private val type: String, private val action: String, @SerializedName("pull_request") val pullRequest: PullRequest?) {
    companion object {
        val TYPE_PULL_REQUEST = "PullRequest"
        val ACTION_OPENED = "opened"
    }

    fun isOpenedPr(): Boolean {
        return type == TYPE_PULL_REQUEST && action == ACTION_OPENED
    }
}

data class PullRequest(val repo: Repo)

data class Repo(val name: String)