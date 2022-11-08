package com.hescha.chat.domen;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum UserAvatar {
    A1(1),
    A2(2),
    A3(3),
    A4(4),
    A5(5),
    A6(6),
    A7(7),
    A8(8),
    A9(9),
    A10(10),
    A11(11),
    A12(12),
    A13(13),
    A14(14),
    A15(15);

    final int number;

    UserAvatar(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number + "";
    }

    public static UserAvatar findByNumber(int number) {
        AtomicReference<UserAvatar> result = new AtomicReference<>(A1);
        Arrays.stream(UserAvatar.values()).forEach(userAvatar -> {
            if (number == userAvatar.number)
                result.set(userAvatar);
        });

        return result.get();
    }
}
