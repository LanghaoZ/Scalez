package cc.lzhong.scalez.service;

import cc.lzhong.scalez.dao.UserDao;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.exception.type.GeneralException;
import cc.lzhong.scalez.util.common.MD5Password;
import cc.lzhong.scalez.util.response.AppMessage;
import cc.lzhong.scalez.vo.AuthVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
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

        return true;
    }

}
