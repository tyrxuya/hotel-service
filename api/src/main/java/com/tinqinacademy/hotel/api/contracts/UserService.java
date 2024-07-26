package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.createuser.CreateUserInput;
import com.tinqinacademy.hotel.api.operations.createuser.CreateUserOutput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserInput;
import com.tinqinacademy.hotel.api.operations.deleteuser.DeleteUserOutput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserInput;
import com.tinqinacademy.hotel.api.operations.getuser.GetUserOutput;

public interface UserService {
    CreateUserOutput createUser(CreateUserInput input);
    DeleteUserOutput deleteUser(DeleteUserInput input);
    GetUserOutput getUser(GetUserInput input);
}
