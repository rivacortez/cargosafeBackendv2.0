package com.dynoware.cargosafe.platform.trips.interfaces.rest.resources;

import com.dynoware.cargosafe.platform.trips.domain.model.aggregates.Driver;
import com.dynoware.cargosafe.platform.trips.domain.model.aggregates.Vehicle;
import com.dynoware.cargosafe.platform.trips.infrastructure.persistence.jpa.DriverRepository;

public record CreateTripResource(
        String name,
        String type,
        double weight,
        String unloadDirection,
        String unloadLocation,
        String unloadDate,
        Vehicle vehicleId,
        Driver driverId,
        int numberPackages,
        String holderName,
        String destinationDate,
        double totalAmount,
        String destinationAddress,
        String loadDetail,
        String pickupAddress
) {
}