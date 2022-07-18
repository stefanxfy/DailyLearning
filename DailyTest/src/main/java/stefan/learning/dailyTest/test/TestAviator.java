package stefan.learning.dailyTest.test;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.util.HashMap;
import java.util.Map;

public class TestAviator {
    public static void main(String[] args) {
        AviatorEvaluator.addFunction(new PKCECodeVerifierFunction());
        AviatorEvaluator.addFunction(new PKCECodeChallengeFunction());
        String exp = "pkce.codeVerifier()";
        Expression expression = AviatorEvaluator.compile(exp, true);
        Map<String, Object> env = new HashMap<>();
        env.put(PKCEUtils.CODE_CHALLENGE_METHOD, "S256");
        String verifier = String.valueOf(expression.execute(env));

        env.put(PKCEUtils.CODE_VERIFIER, verifier);

        String exp2 = "pkce.codeChallenge(code_verifier,code_challenge_method)";
        Expression expression2 = AviatorEvaluator.compile(exp2, true);
        String codeChallenge = String.valueOf(expression2.execute(env));

        System.out.println(verifier);
        System.out.println(codeChallenge);
    }
}
