package com.dynoware.cargosafe.platform.trips.application.internal.commandservices;

import com.dynoware.cargosafe.platform.trips.domain.model.aggregates.Trip;
import com.dynoware.cargosafe.platform.trips.domain.model.commands.CreateTripCommand;
import com.dynoware.cargosafe.platform.trips.domain.model.commands.DeleteTripCommand;
import com.dynoware.cargosafe.platform.trips.domain.model.commands.UpdateTripCommand;
import com.dynoware.cargosafe.platform.trips.domain.services.TripCommandService;
import com.dynoware.cargosafe.platform.trips.infrastructure.persistence.jpa.DriverRepository;
import com.dynoware.cargosafe.platform.trips.infrastructure.persistence.jpa.repositories.TripRepository;
import com.dynoware.cargosafe.platform.trips.infrastructure.persistence.jpa.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TripCommandServiceImpl implements TripCommandService {
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;


    public TripCommandServiceImpl(TripRepository tripRepository, VehicleRepository vehicleRepository, DriverRepository driverRepository) {
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public Optional<Trip> handle(CreateTripCommand command) {
        var trip = new Trip(command);
        tripRepository.save(trip);
        return Optional.of(trip);
    }

    @Override
    public Optional<Trip> handle(UpdateTripCommand command) {
        var trip = tripRepository.findById(command.id());
        if (trip.isPresent()) {
            trip.get().updateTrip(command);
            tripRepository.save(trip.get());
        }
        return trip;
    }

    @Override
    public void handle(DeleteTripCommand command) {
        if (tripRepository.existsById(command.Id())) {
            tripRepository.deleteById(command.Id());
        } else {
            throw new IllegalArgumentException("Trip not found");
        }
    }

    @Override
    public Optional<Trip> assignVehicle(Long tripId, Long vehicleId) {
        var trip = tripRepository.findById(tripId);
        var vehicle = vehicleRepository.findById(vehicleId);
        if (trip.isPresent() && vehicle.isPresent()) {
            trip.get().setVehicle(vehicle.get());
            tripRepository.save(trip.get());
            return Optional.of(trip.get());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Trip> assignDriver(Long tripId, Long driverId) {
        var trip = tripRepository.findById(tripId);
        var driver = driverRepository.findById(driverId);
        if (trip.isPresent() && driver.isPresent()) {
            trip.get().setDriver(driver.get());
            tripRepository.save(trip.get());
            return Optional.of(trip.get());
        }
        return Optional.empty();
    }
}