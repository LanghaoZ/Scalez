package cc.lzhong.scalez.vo;

import cc.lzhong.scalez.util.annotation.Phone;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AuthVo {

    @NotNull
    @Phone
    private String phone;

    @NotNull
    @Length(min=5)
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthVo{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
