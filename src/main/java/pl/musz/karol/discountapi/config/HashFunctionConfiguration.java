package pl.musz.karol.discountapi.config;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;
import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_512;

@Configuration
public class HashFunctionConfiguration {

    @Primary
    @Bean(name = "SHA-256")
    public DigestUtils sha256() {
        return new DigestUtils(SHA_256);
    }

    @Bean(name = "SHA-512")
    public DigestUtils sha512() {
        return new DigestUtils(SHA_512);
    }
}
