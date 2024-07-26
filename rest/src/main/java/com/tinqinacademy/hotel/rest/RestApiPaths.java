package com.tinqinacademy.hotel.rest;

public class RestApiPaths {
    public static final String BASIC = "/api/v1";
    public static final String HOTEL = BASIC + "/hotel";
    public static final String SYSTEM = BASIC + "/system";
    public static final String USER = BASIC + "/user";

    public static final String ROOMS = "/rooms";
    public static final String ROOMID = "/{roomId}";
    public static final String BOOKINGID = "/{bookingId}";
    public static final String USERID = "/{userId}";
    public static final String ROOM = "/room";
    public static final String REGISTER = "/register";

    public static final String CHECK_ROOM = HOTEL + ROOMS;
    public static final String GET_ROOM_INFO = HOTEL + ROOMID;
    public static final String BOOK_ROOM = HOTEL + ROOMID;
    public static final String UNBOOK_ROOM = HOTEL + BOOKINGID;

    public static final String REGISTER_VISITOR = SYSTEM + REGISTER + BOOKINGID;
    public static final String GET_VISITORS_INFO = SYSTEM + REGISTER;
    public static final String CREATE_ROOM = SYSTEM + ROOM;
    public static final String UPDATE_ROOM = SYSTEM + ROOM + ROOMID;
    public static final String PARTIAL_UPDATE_ROOM = SYSTEM + ROOM + ROOMID;
    public static final String DELETE_ROOM = SYSTEM + ROOM + ROOMID;

    public static final String DELETE_USER = USER + USERID;
    public static final String GET_USER = USER + USERID;
    public static final String CREATE_USER = USER;
}
