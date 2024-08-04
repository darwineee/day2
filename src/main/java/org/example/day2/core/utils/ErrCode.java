package org.example.day2.core.utils;

public class ErrCode {
    //Common: range 0-99
    public static final int OTHER = 0;
    public static final int FIELD_VALIDATION = 1;

    //Authentication: range 100-150
    public static final int USER_EXISTED = 100;
    public static final int USER_NOT_FOUND = 102;
    public static final int ACC_INACTIVE = 103;
    public static final int UNAUTHENTICATED = 104;
}
