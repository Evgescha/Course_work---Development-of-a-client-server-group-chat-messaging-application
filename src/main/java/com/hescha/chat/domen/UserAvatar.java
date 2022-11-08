package com.hescha.chat.domen;

public enum UserAvatar {
    A1("1"),
    A2("2"),
    A3("3"),
    A4("4"),
    A5("5"),
    A6("6"),
    A7("7"),
    A8("8"),
    A9("9"),
    A10("10"),
    A11("11"),
    A12("12"),
    A13("13"),
    A14("14"),
    A15("15");

    final String url;

    UserAvatar(String url) {
        this.url=url;
    }

    @Override
    public String toString() {
        return url;
    }
}
