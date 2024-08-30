package org.huyhieu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class IdentityServiceApplicationTest {
    private static final Logger LOG = LogManager.getLogger(IdentityServiceApplicationTest.class);
    private String password = "123456";

    @Test
    void hashMD5() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte[] digest = md.digest();
        String md5Hash = DatatypeConverter.printHexBinary(digest);

        LOG.info("MD5 1 time: {}", md5Hash);

        md.update(password.getBytes());
        digest = md.digest();
        md5Hash = DatatypeConverter.printHexBinary(digest);

        LOG.info("MD5 2 times: {}", md5Hash);
    }

    @Test
    void hashBCRYPT() throws NoSuchAlgorithmException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        LOG.info("BCRYPT 1 time: {}", passwordEncoder.encode(password));
        LOG.info("BCRYPT 2 time: {}", passwordEncoder.encode(password));
        LOG.info(passwordEncoder.encode(password).equals(passwordEncoder.encode(password)));
    }
}
