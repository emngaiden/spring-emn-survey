package com.emnsoft.emnsurvey.security.jwt;

import java.nio.charset.Charset;
import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultSignerFactory;
import io.jsonwebtoken.impl.crypto.JwtSigner;
import io.jsonwebtoken.impl.crypto.Signer;
import io.jsonwebtoken.impl.crypto.SignerFactory;
import io.jsonwebtoken.lang.Assert;

public class JwtSignerImpl implements JwtSigner{

    private static final Charset US_ASCII = Charset.forName("US-ASCII");
    private static final Base64UrlCodec codec = new Base64UrlCodec();

    private final Signer signer;

    public JwtSignerImpl(SignatureAlgorithm alg, Key key) {
        this(DefaultSignerFactory.INSTANCE, alg, key);
    }

    public JwtSignerImpl(SignerFactory factory, SignatureAlgorithm alg, Key key) {
        Assert.notNull(factory, "SignerFactory argument cannot be null.");
        this.signer = factory.createSigner(alg, key);
    }

    @Override
    public String sign(String jwtWithoutSignature) {
        byte[] bytesToSign = jwtWithoutSignature.getBytes(US_ASCII);

        byte[] signature = signer.sign(bytesToSign);

        return codec.encode(signature);
    }
    
}
