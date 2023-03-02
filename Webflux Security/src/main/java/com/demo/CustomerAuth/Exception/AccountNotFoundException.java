package com.demo.CustomerAuth.Exception;

public class AccountNotFoundException extends RuntimeException {
    public String msg;

    public AccountNotFoundException(String str) {
        this.msg = str;
    }

}
