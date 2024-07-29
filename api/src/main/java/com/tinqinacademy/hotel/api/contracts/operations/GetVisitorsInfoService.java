package com.tinqinacademy.hotel.api.contracts.operations;

import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsInput;
import com.tinqinacademy.hotel.api.operations.getregisteredvisitors.GetRegisteredVisitorsOutput;

public interface GetVisitorsInfoService {
    GetRegisteredVisitorsOutput getVisitorsInfo(GetRegisteredVisitorsInput input);
}
