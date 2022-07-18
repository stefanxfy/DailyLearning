package stefan.learning.dailyTest.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PKCEUtils {
    public static final String CODE_CHALLENGE = "code_challenge";
    public static final String CODE_CHALLENGE_METHOD = "code_challenge_method";
    public static final String CODE_VERIFIER = "code_verifier";

    public static boolean isOpenPKCE(String name) {
        if (CODE_CHALLENGE_METHOD.equals(name)) {
            return true;
        }
        return false;
    }

    public static String getCodeVerifier() {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);
        return verifier;
    }

    public static String getCodeChallenge(String verifier, String codeChallengeMethod) {
        if ("S256".equals(codeChallengeMethod)) {
            return sha256(verifier);
        }
        if ("plain".equals(codeChallengeMethod)) {
            return verifier;
        }
        return "";
    }

    private static String sha256(String verifier) {
        try {
            byte[] bytes = verifier.getBytes("US-ASCII");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes, 0, bytes.length);
            byte[] digest = md.digest();
            String challenge = Base64.getUrlEncoder().encodeToString(digest);
            return challenge;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
