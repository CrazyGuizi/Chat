package com.example.crazygz.chat.common.util

import android.util.Log

public object LogUtil {

        private const val VERBOSE: Int = 1;

        private const val DEBUG: Int = 2;

        private const val INFO: Int = 3;

        private const val WARN: Int = 4;

        private const val ERROR: Int = 5;

        private const val NOTHING: Int = 6;

        private var level: Int= VERBOSE;

        public fun setNothing() = {level= NOTHING}

        public fun v(tag: String, msg: String) = {if (level <=  VERBOSE) Log.v(tag, msg)}

        public fun d(tag: String, msg: String) = {if (level <=  DEBUG) Log.d(tag, msg)}

        public fun i(tag: String, msg: String) = {if (level <=  INFO) Log.i(tag, msg)}

        public fun w(tag: String, msg: String) = {if (level <=  WARN) Log.w(tag, msg)}

        public fun e(tag: String, msg: String) = {if (level <=  ERROR) Log.e(tag, msg)}

}