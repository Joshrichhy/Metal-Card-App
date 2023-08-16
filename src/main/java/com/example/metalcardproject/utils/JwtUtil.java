package com.example.metalcardproject.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.metalcardproject.Exceptions.NoUserAccountException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

import static com.example.metalcardproject.utils.AppUtils.CLAIMS_VALUE;

@AllArgsConstructor
@Getter
public class JwtUtil {

    private final String secret;

    public Map<String, Claim> extractClaimFrom(String token) throws NoUserAccountException {
        validateToken(token);
        DecodedJWT decodedJWT = validateToken(token);
        if (decodedJWT.getClaim(CLAIMS_VALUE) == null) throw new NoUserAccountException("");
       return decodedJWT.getClaims();
    }

    private DecodedJWT validateToken(String token) {
    return JWT.require(Algorithm.HMAC512(secret))
            .build().verify(token);

    }
}
