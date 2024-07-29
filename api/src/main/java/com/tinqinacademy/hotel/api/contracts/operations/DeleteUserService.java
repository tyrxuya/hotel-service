package com.tinqinacademy.hotel.api.contracts.operations;

import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserInput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserOutput;

public interface DeleteUserService {
    DeleteUserOutput deleteUser(DeleteUserInput input);
}
