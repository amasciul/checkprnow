package checkprnow

data class Event(val type: String, val action: String) {
    companion object {
        val TYPE_PULL_REQUEST = "PullRequest"
        val ACTION_OPENED = "opened"
    }

    fun isOpenedPr(): Boolean {
        return type == TYPE_PULL_REQUEST && action == ACTION_OPENED
    }
}

