package checkprnow

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
        get("/event") {
            call.respond(HttpStatusCode.MethodNotAllowed, "Use POST method")
        }
        post("/event") {
            val event: Event = call.receive()
            println("Event received: $event")
            if (event.isOpenedPr()) {
                checkOpenedPrCount()
            }
        }
    }
}

fun checkOpenedPrCount() {
    println("Checking opened PR count...")
}