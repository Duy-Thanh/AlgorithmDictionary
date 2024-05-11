package com.algorithmswiki.apps.backend.backend;

import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import com.algorithmswiki.apps.backend.backend.Object.JWTHeader;
import com.algorithmswiki.apps.backend.backend.Object.JWTTokenObject;

public class JWTTokenGenerator {
    private static final String SECRET_KEY = "AlgorithmDictionary_BackendSecretKey123@!@";

    public static String createJwtToken(String username, String password) throws Exception {
        JWTHeader header = new JWTHeader("JWT", "HS512");
        String json = JSONHelper.toJSON(header).toString();
        String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(json.toString().getBytes());

        JWTTokenObject tokenPayload = new JWTTokenObject(username, System.currentTimeMillis(), System.currentTimeMillis() / 1000L + 3600);

        String payloadJSON = JSONHelper.toJSON(tokenPayload).toString();
        String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJSON.getBytes());

        // Step 3: Sign the Token
        String signature = createSignature(encodedHeader, encodedPayload);

        // Step 4: Encode the Token
        return encodedHeader + "." + encodedPayload + "." + signature;
    }

    private static String createSignature(String encodedHeader, String encodedPayload) throws Exception {
        String message = encodedHeader + "." + encodedPayload;
        Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
        SecretKeySpec secret_key = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA512");
        sha512_HMAC.init(secret_key);

        byte[] signatureBytes = sha512_HMAC.doFinal(message.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
    }
}
