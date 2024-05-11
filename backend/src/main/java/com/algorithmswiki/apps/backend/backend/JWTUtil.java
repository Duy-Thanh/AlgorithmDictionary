package com.algorithmswiki.apps.backend.backend;

import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.*;

import com.algorithmswiki.apps.backend.backend.Object.JWTTokenObject;

public class JWTUtil {
    private static final String SECRET_KEY = "AlgorithmDictionary_BackendSecretKey123@!@";

    private static String createSignature(String encodedHeader, String encodedPayload) throws Exception {
        String message = encodedHeader + "." + encodedPayload;
        Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
        SecretKeySpec secret_key = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA512");
        sha512_HMAC.init(secret_key);

        byte[] signatureBytes = sha512_HMAC.doFinal(message.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
    }

    public static boolean isTokenValid(String token) throws Exception {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }

            // Decode the header and payload
            String header = new String(Base64.getUrlDecoder().decode(parts[0]));
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            // Re-compute the signature
            String signature = createSignature(parts[0], parts[1]);

            // Compare the computed signature with the signature of the access_token
            return signature.equals(parts[2]);
        } catch (Exception e) {
            return false;
        }
    }

    public static String extractUsername(String token) throws Exception {
        try {
            if (!isTokenValid(token)) {
                return "Cannot verify integrity of token or your token is corrupted. Due to security reason, you are prohibited to continue your action.";
            }
            else {
                // Split the JWT token
                String[] parts = token.split("\\.");
                if (parts.length != 3) {
                    return "error";
                }

                String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
                JWTTokenObject payload = JSONHelper.fromJSON(payloadJson, JWTTokenObject.class);
                
                return (String)payload.getSub();
            }
        } catch (Exception e) {
            return "Cannot retrieve neccessary information. Due to security reason, you are prohibited to continue your action. Error details: " + e.getLocalizedMessage();
        }
    }
}
