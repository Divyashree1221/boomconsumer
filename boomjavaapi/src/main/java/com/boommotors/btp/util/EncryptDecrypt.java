/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.util;

import com.boommotors.btp.exception.AppException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;



/**
 *
 * @author rjanumpally
 */
@Component
public class EncryptDecrypt {

    private static final String ALGORITHUM = "AES";
    private static final byte[] keyValue
            = new byte[]{'O', 'P', 'T', 'U', 'M', 'I', 'R',
                'M', '@', 'M', 'O', 'D', 'U', 'L', 'E', '$'};

    public String encrypt(String data) throws AppException {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHUM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            String encryptedValue = new Base64(true).encodeToString(encVal);
            return encryptedValue;
        } catch (Exception e) {
            throw new AppException("Exception Occurred while Encrypting:" + e.getMessage());
        }
    }

    public String decrypt(String encryptedData) throws AppException {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHUM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = new Base64().decode(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;
        } catch (Exception e) {
            throw new AppException("Exception Occurred while Dencrypting:" + e.getMessage());
        }
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHUM);
        return key;
    }

//    public static void main(String[] args) {
//        try {
//            EncryptDecrypt ed = new EncryptDecrypt();
//            System.out.println(">>>>>" + ed.encrypt("7"));
//            System.out.println(">>>>>" + ed.encrypt("10"));
//            System.out.println(">>>>>" + ed.encrypt("13"));
//        } catch (AppException ex) {
//            Logger.getLogger(EncryptDecrypt.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}