package stefan.learning.dailyTest.test;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Test714 {
    public static void main(String[] args) {
        String codeVerifier = getCodeVerifier();
        String codeChallenge = sha256(codeVerifier);
        System.out.println(codeVerifier);
        System.out.println(codeChallenge);
        codeChallenge = sha2562(codeVerifier);
        System.out.println(codeChallenge);

    }
    private static String sha256(String verifier) {
        try {
            byte[] bytes = verifier.getBytes("US-ASCII");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes, 0, bytes.length);
            byte[] digest = md.digest();
            String challenge = org.apache.commons.codec.binary.Base64.encodeBase64String(digest);
            return challenge;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    private static String sha2562(String verifier) {
        try {
            byte[] bytes = verifier.getBytes("US-ASCII");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes, 0, bytes.length);
            byte[] digest = md.digest();
            String challenge = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(digest);
            return challenge;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getCodeVerifier() {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        String verifier = Base64.getUrlEncoder().withoutPadding().encodeToString(code);
        return verifier;
    }

    public static void test() {
        throw new RuntimeException("测试异常");
    }
}
