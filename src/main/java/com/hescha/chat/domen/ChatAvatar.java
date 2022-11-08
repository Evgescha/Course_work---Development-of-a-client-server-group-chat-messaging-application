package com.hescha.chat.domen;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum ChatAvatar {
    A1(16),
    A2(17),
    A3(18),
    A4(19),
    A5(20);

    final int number;

    ChatAvatar(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number + "";
    }

    public static ChatAvatar findByNumber(int number) {
        AtomicReference<ChatAvatar> result = new AtomicReference<>(A1);
        Arrays.stream(ChatAvatar.values()).forEach(avatar -> {
            if (number == avatar.number)
                result.set(avatar);
        });

        return result.get();
    }
}
