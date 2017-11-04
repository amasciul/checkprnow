package checkprnow

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.routing.get
import io.ktor.routing.post
import java.text.DateFormat

fun Application.main() {
    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

    routing {
        post("/event") {
            val event: Event = call.receive()
            println("Event received: $event")
            if (event.isOpenedPr()) {
                onPrOpened(event.pullRequest!!)
            }
        }
    }
}

fun onPrOpened(pullRequest: PullRequest) {
    println("New PR opened on ${pullRequest.repo.name}")
    println("Checking opened PR count...")

    "http://api.github.com/repos/${pullRequest.repo.owner.login}/${pullRequest.repo.name}/pulls"
            .httpGet()
            .responseString { _, _, result ->
                println(result.get())

                when (result) {
                    is Result.Failure -> {
                        println("Error: ${result.error}")
                    }
                    is Result.Success -> {
                        val prs : List<PullRequest> = Gson().fromJson(result.get(), object : TypeToken<List<PullRequest>>(){}.getType())
                        println("${pullRequest.repo.name} has ${prs.size} PRs!")
                    }
                }
            }
}