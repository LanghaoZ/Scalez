package cc.lzhong.scalez.util.annotation;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    private static final Pattern validPhoneRegex = Pattern.compile("^[0-9]{10}");

    public static boolean isValidPhoneNumber(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        Matcher matcher = validPhoneRegex.matcher(phone);
        return matcher.matches();
    }

}
