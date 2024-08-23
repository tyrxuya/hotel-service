package com.tinqinacademy.hotel.api;

public final class HotelFeignClientApiPaths {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static final String PATCH = "PATCH";

    private static final String SPACE_SYMBOL = " ";

    private static final String CHECK_ROOMS_REQUEST_PARAMS = "?startDate={startDate}&endDate={endDate}&bedSize={bedSize}&bathroomType={bathroomType}&bedCount={bedCount}";
    private static final String GET_REGISTERED_VISITORS_REQUEST_PARAMS = "?roomNo={roomNo}&firstName={firstName}&lastName={lastName}&phoneNo={phoneNo}&civilNumber={civilNumber}&birthday={birthday}&idIssueAuthority={idIssueAuthority}&idIssueDate={idIssueDate}";

    public static final String CHECK_ROOMS = GET + SPACE_SYMBOL + HotelRestApiPaths.CHECK_ROOM + CHECK_ROOMS_REQUEST_PARAMS;
    public static final String GET_ROOM_BY_ID = GET + SPACE_SYMBOL + HotelRestApiPaths.GET_ROOM_INFO;
    public static final String BOOK_ROOM = POST + SPACE_SYMBOL + HotelRestApiPaths.BOOK_ROOM;
    public static final String UNBOOK_ROOM = DELETE + SPACE_SYMBOL + HotelRestApiPaths.UNBOOK_ROOM;
    public static final String REGISTER_VISITOR = POST + SPACE_SYMBOL + HotelRestApiPaths.REGISTER_VISITOR;
    public static final String GET_REGISTERED_VISITORS = GET + SPACE_SYMBOL + HotelRestApiPaths.GET_VISITORS_INFO + GET_REGISTERED_VISITORS_REQUEST_PARAMS;
    public static final String CREATE_ROOM = POST + SPACE_SYMBOL + HotelRestApiPaths.CREATE_ROOM;
    public static final String UPDATE_ROOM = PUT + SPACE_SYMBOL + HotelRestApiPaths.UPDATE_ROOM;
    public static final String PARTIAL_UPDATE_ROOM = PATCH + SPACE_SYMBOL + HotelRestApiPaths.PARTIAL_UPDATE_ROOM;
    public static final String DELETE_ROOM = DELETE + SPACE_SYMBOL + HotelRestApiPaths.DELETE_ROOM;
}
