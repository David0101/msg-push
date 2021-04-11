package com.jim.msg.push.auth.utils;

import com.jim.msg.push.auth.entity.LoginKeyPair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CryptoUtils {
    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMElPASMpf7mWSjKu/Rj7swqHqvEGbJlB9lMyKnqHBwWnc73BhZCgI/zVOHOuqFzi+JXEEOSCfyI/kF5nZHSYChfB8eZUDWRbMAPvelVkvSbTfXIy95jMc3jC/kzDgFlVE7Pzu6lh2AHC8wStApkGxAG2GbF9txnzUbNKdp9w3VbAgMBAAECgYEAg7Brd36eP2m3KMTx2fO5AaNGPj76dlPEQjjEKHXirJQPDOoIUG0PUxbJrRxSy3oIyk4qXKfZ/0E4elP9R0t1G19zckpja5wL6OMAcWHreP9OlwlJ++hcmO56hTMOMBTC4LF4A7PkDheF54Oy0Ct/uhesIEwNBt3UFOKsMr3SNIECQQD/PBQw4llZoVDsuDGqpioFBO7yQO7cXOinDZjQ4xbu47CXhMOxH3BD6WVBuxguo6FwxmvFR/8VziMmcofXR5JxAkEAwbl+wh1MH0bbYKj45blIBBSacZzZd7QvrCCL/AhLRJRh4PlPzhFwPATSbEOb2KfG0W8i2nlE4u0uG7iwkssSiwJAF76uHjIgn98LGUq1jCuzQw5HcJAr6KJYHpp8Ogq2ankSR7ZZQlQrbJX9DpVqVYRxk31SL2NYTt4DlSMYkiWdcQJAOEgL65lXZD7RvWNZ7GPH6GeT9y36gCx5LSjHgpFmTKZPSW0lIlkuokFEYO/Dd05HFNGU7qQMv2Do5GTj58HXwQJAIoO6giuOS4W7O72XkOQECiH/5IQJNiZNkD2Rx7ihQjYINDG29BElqG/u7QDT3D0hQTTIeVEqZyV++9zWnr2eFA==";

    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBJTwEjKX+5lkoyrv0Y+7MKh6rxBmyZQfZTMip6hwcFp3O9wYWQoCP81Thzrqhc4viVxBDkgn8iP5BeZ2R0mAoXwfHmVA1kWzAD73pVZL0m031yMveYzHN4wv5Mw4BZVROz87upYdgBwvMErQKZBsQBthmxfbcZ81GzSnafcN1WwIDAQAB";

    private static ConcurrentHashMap<String,Key> rsaKey = new ConcurrentHashMap<>();

    private static final String RSA_AlRORITHM = "RSA";

    private static final String RSA_PUB_KEY = "rsa_pub_key";

    private static final String RSA_PRI_KEY = "rsa_pri_key";



    static {
        byte[] publicKeyBytes = Base64.decodeBase64(publicKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_AlRORITHM);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            rsaKey.put(RSA_PUB_KEY,publicKey);

            byte[] privateKeyBytes = Base64.decodeBase64(privateKey);
            keyFactory = KeyFactory.getInstance(RSA_AlRORITHM);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            rsaKey.put(RSA_PRI_KEY,privateKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    private static final String AES_KEY_ALGORITHM = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//AES加密算法

    public static String encryptAES(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

            byte[] byteContent = content.getBytes("UTF-8");

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return Base64.encodeBase64String(result);//通过Base64转码返回

        } catch (Exception ex) {
            log.error("", ex);
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    public static String decryptAES(String content, String password) {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

            //执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));

            return new String(result, "utf-8");
        } catch (Exception ex) {
            log.error("", ex);
        }

        return null;
    }

    private static SecretKey getSecretKey(final String password) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance(AES_KEY_ALGORITHM);

            //AES 要求密钥长度为 128
            kg.init(128, new SecureRandom(password.getBytes()));

            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return secretKey;
            //return new SecretKeySpec(secretKey.getEncoded(), AES_KEY_ALGORITHM);// 转换为AES专用密钥

        } catch (NoSuchAlgorithmException ex) {
            log.error(null, ex);
        }

        return null;
    }


    public static LoginKeyPair getKeyPair(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_AlRORITHM);
            keyPairGenerator.initialize(512,new SecureRandom(RandomStringUtils.randomAlphanumeric(6).getBytes()));
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            Key publicKey = keyPair.getPublic();
            Key privateKey = keyPair.getPrivate();
            String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
            String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
            return new LoginKeyPair(privateKeyStr,publicKeyStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String RSAEncrypt(String content){
        //取得私钥
        KeyFactory keyFactory = null;
        try {
            byte[] contentBytes = content.getBytes();
            Key publicKey = rsaKey.get(RSA_PUB_KEY);
            Cipher cipher = Cipher.getInstance(RSA_AlRORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptPass = cipher.doFinal(contentBytes);
            return Base64.encodeBase64String(encryptPass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密后返回base64字符串
     * @param content
     * @param publicKeyStr
     * @return
     */
    public static String RSAEncrypt(String content,String publicKeyStr){
        //取得私钥（公钥加密 ，用于测试 客户端拿到公钥后自动加密密文(如：密码)）
        try {
            byte[] contentBytes = content.getBytes();
            byte[] publicKeyBytes = Base64.decodeBase64(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_AlRORITHM);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            Cipher cipher = Cipher.getInstance(RSA_AlRORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptPass = cipher.doFinal(contentBytes);
            return Base64.encodeBase64String(encryptPass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String RSADecrypt(String content){
        //取得私钥
        KeyFactory keyFactory = null;
        try {
            Key privateKey = rsaKey.get(RSA_PRI_KEY);
            Cipher cipher = Cipher.getInstance(RSA_AlRORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] rawBytes = Base64.decodeBase64(content);
            byte[] encryptPass = cipher.doFinal(rawBytes);
            return new String(encryptPass,"utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * rsa解密，密文经过base64编码，需要先解码
     * @param content
     * @param privateKeyStr
     * @return
     */
    public static String RSADecrypt(String content, String privateKeyStr){
        //取得私钥
        try {

            byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_AlRORITHM);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            Cipher cipher = Cipher.getInstance(RSA_AlRORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] rawBytes = Base64.decodeBase64(content);
            byte[] encryptPass = cipher.doFinal(rawBytes);

            //return Base64.decodeBase64(encryptPass);
            return new String(encryptPass,"utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void main(String[] args) {
        String content = "000001";
        String password = "hello wolrd";
        //公钥加密，私钥解密，公钥发给客户端
        LoginKeyPair loginKeyPair = getKeyPair();
        loginKeyPair.setPrivateKey("MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA2VJk0vy5GrKqYvgpnftmghxywg7Xm3xCY/qOsCzMDY39Lgiz80vbZoOIoB46gmyD0xoBck/dKslZEQldbVD0PQIDAQABAkEAzhV4TywhTNQJB/lVXd89gj0ABk6U33mia7T7As44YtB3yp1uEPAWqhaKA6HdqwjNBHsPIPGolzy/y8DSe8hT8QIhAPCRtffEUE2L8iVJQFO2qJjWrpfZn8aqBHzfMMuqatNvAiEA50LysGSSoaqZWI33OJVEMId/+iW52jG5aamUj3fzbRMCIGNN8sylJ/Tq2PkVRM2JsAzvScD7H55VXmbhhvX7kf9XAiEAgF03ylM41MWV8oJyfBTWUb2UeqvgGk3JZe38CRqCosMCIDAKTvjLn2LZb3Edl83U7r3TlLt9phKuCfP94uAnLsL7");
        loginKeyPair.setPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANlSZNL8uRqyqmL4KZ37ZoIccsIO15t8QmP6jrAszA2N/S4Is/NL22aDiKAeOoJsg9MaAXJP3SrJWREJXW1Q9D0CAwEAAQ==");
        String miwen = RSAEncrypt(content,loginKeyPair.getPublicKey());

        System.out.println("miwen:" + miwen);


        String mingwen = RSADecrypt(miwen,loginKeyPair.getPrivateKey());
        System.out.println("mingwen:" + mingwen);
        System.out.println(Base64.decodeBase64("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIhkrPPxo3w2JiFHsJjjzJwemr0pD-wLLRaGBCmMutDlDoZBAB5gjA5tbVedkb1l_HjVWL4jbrtGgjlvJyM8J0ECAwEAAQ"));

    }
}
