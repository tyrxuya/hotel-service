package com.tinqinacademy.hotel.api.contracts.operations;

import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;

public interface RegisterVisitorService {
    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);
}
