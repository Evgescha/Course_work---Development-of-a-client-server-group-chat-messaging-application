package com.hescha.chat.domen;

public enum ChatAvatar {
    A1("16"),
    A2("17"),
    A3("18"),
    A4("19"),
    A5("20");

    final String url;

    ChatAvatar(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
