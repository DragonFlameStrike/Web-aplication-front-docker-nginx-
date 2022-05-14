package com.jarsoft.webapp.adverts.testtask.security;

public class SqlInjectionSecurity {
    static public Boolean check(String SQL){
        return SQL.contains("'") || SQL.contains("=") || SQL.contains("<") || SQL.contains(">");
    }
}
