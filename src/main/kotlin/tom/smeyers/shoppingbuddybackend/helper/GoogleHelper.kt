package tom.smeyers.shoppingbuddybackend.helper

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import org.springframework.stereotype.Component

@Component
class GoogleHelper {
    val verifier: GoogleIdTokenVerifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), JacksonFactory.getDefaultInstance())
            .build()

    fun verifyGoogleToken(idToken: String): GoogleIdToken? {
        return verifier.verify(idToken)
    }
}