package com.book.api.user.model;

public class AccessLists {
    public static boolean checkUriAndUser(String uri, String user) {
        return "admin".equals(user);
    }
}

