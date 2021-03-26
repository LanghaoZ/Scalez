package cc.lzhong.scalez.util.common;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Password {

    private static final String salt = "da4Kj32k";

    public static String md5Transform(String source) {
        return DigestUtils.md5Hex(source);
    }

    public static String transformPlainPassword(String password) {
        String transformedPassword = "" + salt.charAt(2) + salt.charAt(5) + password + salt.charAt(1) + salt.charAt(0);
        System.out.println(salt);
        System.out.println(password);
        System.out.println(transformedPassword);
        return md5Transform(transformedPassword);
    }

    public static String transformInputPassword(String password, String salt) {
        String transformedPassword = salt.charAt(2) + salt.charAt(5) + password + salt.charAt(1) + salt.charAt(0);
        return md5Transform(transformedPassword);
    }

    public static String transformPassword(String password, String dbsalt) {
        String inputPassword = transformPlainPassword(password);
        String savedPassword = transformInputPassword(inputPassword, dbsalt);
        return savedPassword;
    }

}
