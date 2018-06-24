package com.example.crazygz.chat.factories.okhttp.request

import java.util.concurrent.ConcurrentHashMap

public class RequestParams(var urlParams: ConcurrentHashMap<String, String>?,
                           var fileParams: ConcurrentHashMap<String, String>?) {


    constructor() : this(ConcurrentHashMap(), ConcurrentHashMap())

    constructor(map: Map<String, String>) : this(ConcurrentHashMap(), ConcurrentHashMap()) {
        if (map != null) for (m in map.entries) {
            put(m.key, m.value)
        }
    }

    public fun put(key: String?, value: String?) {
        if (key != null && value != null) {
            urlParams!![key] = value
        }
    }
}