package com.allega.nomad.controller.encrypt.utils;

public class ADZSocketConstant {
    public static final String HTTP_200 = "200 OK";
    public static final String HTTP_206 = "206 Partial Content";
    public static final String HTTP_BAD_REQUEST = "400 Bad Request";
    public static final String HTTP_416 = "416 Range not satisfiable";
    public static final String HTTP_INTERNAL_ERROR = "500 Internal Server Error";

    public static final String METHOD_GET = "GET";
    public static final String HEADER_RANGE = "range";
    public static final String HEADER_CONTENT_LENGHT = "Content-Length";
    public static final String HEADER_CONTENT_RANGE = "Content-Range";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCEPT_RANGES = "Accept-Ranges";
    public static final String PREFIX_RANGE = "bytes=";
    public static final String PREFIX_MINUS = "-";
    public static final String VALUE_BYTE = "bytes";
    public static String REQUEST_HTTP_FORMAT = "HTTP/1.0";
}
