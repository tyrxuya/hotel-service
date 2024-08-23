package com.tinqinacademy.hotel.api;

public final class HotelRestApiPaths {
    private static final String API_V1 = "/api/v1";
    private static final String HOTEL = API_V1 + "/hotel";
    private static final String SYSTEM = API_V1 + "/system";
    private static final String USER = API_V1 + "/user";

    private static final String ROOMS = "/rooms";
    private static final String ROOMID = "/{roomId}";
    private static final String BOOKINGID = "/{bookingId}";
    private static final String USERID = "/{userId}";
    private static final String ROOM = "/room";
    private static final String REGISTER = "/register";

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
}
