package me.Testing;

import me.API.Net;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        System.out.println(Net.netty.sendGETRequest("https://translate.google.com/?hl=ru&sl=en&tl=ru&text=hello&op=translate"));
    }
}
