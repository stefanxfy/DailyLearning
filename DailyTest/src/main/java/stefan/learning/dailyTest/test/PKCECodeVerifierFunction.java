package stefan.learning.dailyTest.test;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.Map;

/**
 * ${pkce.codeVerifier()}
 */
public class PKCECodeVerifierFunction extends AbstractFunction {
    @Override
    public String getName() {
        return "pkce.codeVerifier";
    }

    public AviatorObject call(Map<String, Object> env) {
        String codeVerifier = PKCEUtils.getCodeVerifier();
        return new AviatorString(codeVerifier);
    }


}
