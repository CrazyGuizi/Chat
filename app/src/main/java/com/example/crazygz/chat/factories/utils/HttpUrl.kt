package com.example.crazygz.chat.factories.utils

public object HttpUrl {

    public const val LOCAL_HOST = "192.168.1.104"
    public const val SOCKET_PORT = 3000

    public const val LOGIN = "http://$LOCAL_HOST:8080/Chat/Login";

    public const val REGISTER = "http://$LOCAL_HOST:8080/Chat/Register";
}