package com.zk;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

public class Digest {

    public static void main(String[] args) {

        try {
//            String result = DigestAuthenticationProvider.generateDigest("root:123456");
            String result = DigestAuthenticationProvider.generateDigest("super:admin");
            System.out.println("result："+result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

}
