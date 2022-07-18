package stefan.learning.dailyTest.test;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.Map;

/**
 * ${pkce.codeChallenge(code_verifier,code_challenge_method)}
 */
public class PKCECodeChallengeFunction extends AbstractFunction {
    @Override
    public String getName() {
        return "pkce.codeChallenge";
    }

    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        String codeVerifier = FunctionUtils.getStringValue(arg1, env);
        String codeChallengeMethod = FunctionUtils.getStringValue(arg2, env);

        // 根据code_challenge_method 计算 code_challenge
        String codeChallenge = PKCEUtils.getCodeChallenge(codeVerifier, codeChallengeMethod);

        return new AviatorString(codeChallenge);
    }


}
