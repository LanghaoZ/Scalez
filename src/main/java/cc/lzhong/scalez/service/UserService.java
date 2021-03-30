package cc.lzhong.scalez.service;

import cc.lzhong.scalez.dao.UserDao;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.exception.type.GeneralException;
import cc.lzhong.scalez.util.common.MD5Password;
import cc.lzhong.scalez.util.redis.UserKeyPrefix;
import cc.lzhong.scalez.util.response.AppMessage;
import cc.lzhong.scalez.vo.AuthVo;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class UserService {

    private final UserDao userDao;
    private final RedisService redisService;

    public static final String COOKIE_TOKEN_FIELD = "token";

    public UserService(UserDao userDao, RedisService redisService) {
        this.userDao = userDao;
        this.redisService = redisService;
    }

    public User getUserById(Long id) {
        User user = redisService.get(UserKeyPrefix.byId, id.toString(), User.class);
        if (user != null) {
            return user;
        }

        user = userDao.getUserById(id);
        if (user != null) {
            redisService.set(UserKeyPrefix.byId, id.toString(), user);
        }

        return user;
    }

    public User getUserByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        User user = redisService.get(UserKeyPrefix.byToken, token, User.class);
        if (user != null) {
            this.configureCookie(response, token, user);
        }

        return user;
    }

    public boolean login(HttpServletResponse response, AuthVo authVo) {
        if (authVo == null) {
            throw new GeneralException(AppMessage.INTERNAL_ERROR);
        }

        String phone = authVo.getPhone();
        String password = authVo.getPassword();
        User user = this.getUserById(Long.parseLong(phone));
        if (user == null) {
            throw new GeneralException(AppMessage.INVALID_USER);
        }

        String savedPassword = user.getPassword();
        String savedSalt = user.getSalt();
        String computedPassword = MD5Password.transformInputPassword(password, savedSalt);
        if (!computedPassword.equals(savedPassword)) {
            throw new GeneralException(AppMessage.INVALID_PASSWORD);
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        configureCookie(response, token, user);

        return true;
    }

    public void configureCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKeyPrefix.byToken, token, user);
        Cookie cookie = new Cookie(COOKIE_TOKEN_FIELD, token);
        cookie.setMaxAge(UserKeyPrefix.byToken.timeUntilExpiration());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
