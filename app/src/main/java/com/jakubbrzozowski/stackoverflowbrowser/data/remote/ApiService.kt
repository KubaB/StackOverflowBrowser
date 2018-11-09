package com.jakubbrzozowski.stackoverflowbrowser.data.remote

interface ApiService {

    companion object {
        private const val API_VERSION = "2.2"
        const val URL = "http://api.stackexchange.com/$API_VERSION/"
    }
}
