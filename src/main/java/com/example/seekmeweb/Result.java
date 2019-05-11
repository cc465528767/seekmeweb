package com.example.seekmeweb;

import com.google.gson.Gson;

public class Result<D> {

    private static final Gson sGson = new Gson();

    private int code;
    private String msg;
    private String token;
    private D data;
    private D data2;

    private String toJson(){
        return sGson.toJson(this);
    }
    public static <D> String create(D data){
        Result<D> result = new Result<>();

        result.data = data;
        return result.toJson();
    }
    public static <D> String create(int code,String msg,D data){
        Result<D> result = new Result<>();
        result.code = code;
        result.msg = msg;
        result.data = data;
        return result.toJson();
    }
    public static <D> String create(int code,String msg,D data,String token){
        Result<D> result = new Result<>();
        result.code = code;
        result.msg = msg;
        result.data = data;
        result.token=token;
        return result.toJson();
    }
}

