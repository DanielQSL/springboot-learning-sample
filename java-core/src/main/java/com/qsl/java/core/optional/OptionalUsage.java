package com.qsl.java.core.optional;

import com.qsl.java.core.dataobject.UserDO;
import org.apache.catalina.User;

import java.util.Optional;

/**
 * Optional使用
 * 1.isPresent()判断几乎与NULL一样，使用这个API的意义不大
 * 2.认识到orElse、orElseGet、map等方法的妙用
 * 3.get、ifPresent这样的方法更应该看做是私有方法（不要直接去使用）
 *
 * @author qianshuailong
 * @date 2020/11/11
 */
@SuppressWarnings("ALL")
public class OptionalUsage {

    public static void main(String[] args) {
        // ======= Optional无意义API =======
        isUserEqualNull();
        // ================================

        UserDO userDO = null;
        Optional<UserDO> optionalUser = Optional.ofNullable(userDO);
        // =========== 下面为Optional有意义的API ==============
        // 1.1存在则返回，空则提供默认值
        optionalUser.orElse(new UserDO());
        // 1.2存在则返回，空则由函数返回
        optionalUser.orElseGet(() -> defaultUser());
        // 1.3存在则返回，空则抛出异常
        optionalUser.orElseThrow(RuntimeException::new);

        // 2.存在才去做相应措施
        optionalUser.ifPresent(u -> System.out.println(u.getName()));

        // 3.map可以对Optional中的对象执行某种操作，且返回一个Optional对象
        optionalUser.map(u -> u.getName()).orElse("default");
        // 3.1.map是可以无限级联操作的
        optionalUser.map(u -> u.getName()).map(name -> name.length()).orElse(0);
    }

    private static UserDO defaultUser() {
        return new UserDO();
    }

    private static void isUserEqualNull() {
        User user = null;
        if (user != null) {
            System.out.println("User is not null");
        } else {
            System.out.println("User is null");
        }

        Optional<UserDO> optionalUserDO = Optional.empty();
        if (optionalUserDO.isPresent()) {
            System.out.println("User is not null");
        } else {
            System.out.println("User is null");
        }
    }

}
