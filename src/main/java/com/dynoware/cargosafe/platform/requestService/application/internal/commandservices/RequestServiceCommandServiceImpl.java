package com.dynoware.cargosafe.platform.requestService.application.internal.commandservices;

import com.dynoware.cargosafe.platform.iam.domain.model.aggregates.User;
import com.dynoware.cargosafe.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.dynoware.cargosafe.platform.requestService.domain.model.aggregates.RequestService;
import com.dynoware.cargosafe.platform.requestService.domain.model.commands.CreateRequestServiceCommand;
import com.dynoware.cargosafe.platform.requestService.domain.model.commands.DeleteRequestServiceCommand;
import com.dynoware.cargosafe.platform.requestService.domain.model.commands.UpdateRequestServiceCommand;
import com.dynoware.cargosafe.platform.requestService.domain.model.entities.RequestServiceStatus;
import com.dynoware.cargosafe.platform.requestService.domain.model.entities.Status;
import com.dynoware.cargosafe.platform.requestService.domain.model.valueobjects.StatusName;
import com.dynoware.cargosafe.platform.requestService.domain.services.RequestServiceCommandService;
import com.dynoware.cargosafe.platform.requestService.infrastructure.persistence.jpa.repositories.RequestServiceRepository;
import com.dynoware.cargosafe.platform.requestService.infrastructure.persistence.jpa.repositories.StatusRepository;
import com.dynoware.cargosafe.platform.trips.domain.model.aggregates.Driver;
import com.dynoware.cargosafe.platform.trips.domain.model.aggregates.Trip;
import com.dynoware.cargosafe.platform.trips.domain.model.aggregates.Vehicle;
import com.dynoware.cargosafe.platform.trips.infrastructure.persistence.jpa.DriverRepository;
import com.dynoware.cargosafe.platform.trips.infrastructure.persistence.jpa.repositories.TripRepository;
import com.dynoware.cargosafe.platform.trips.infrastructure.persistence.jpa.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestServiceCommandServiceImpl implements RequestServiceCommandService {
    private final RequestServiceRepository repository;
    private final StatusRepository statusRepository;
    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public RequestServiceCommandServiceImpl(RequestServiceRepository repository, StatusRepository statusRepository, TripRepository tripRepository, DriverRepository driverRepository, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.repository = repository;
        this.statusRepository = statusRepository;
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RequestService handle(CreateRequestServiceCommand command) {
        var requestService = new RequestService();
        requestService.setUnloadDirection(command.unloadDirection());
        requestService.setType(command.type());
        requestService.setNumberPackages(command.numberPackages());
        requestService.setCountry(command.country());
        requestService.setDepartment(command.department());
        requestService.setDistrict(command.district());
        requestService.setDestination(command.destination());
        requestService.setUnloadLocation(command.unloadLocation());
        requestService.setUnloadDate(command.unloadDate());
        requestService.setDistance(command.distance());
        requestService.setHolderName(command.holderName());
        requestService.setPickupAddress(command.pickupAddress());
        requestService.setPickupLat(command.pickupLat());
        requestService.setPickupLng(command.pickupLng());
        requestService.setDestinationAddress(command.destinationAddress());
        requestService.setDestinationLat(command.destinationLat());
        requestService.setDestinationLng(command.destinationLng());
        requestService.setLoadDetail(command.loadDetail());
        requestService.setWeight(command.weight());

        Long statusId = command.statusId() != null ? command.statusId() : 3L;
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));
        requestService.setStatus(status);

        RequestServiceStatus requestServiceStatus = new RequestServiceStatus();
        requestServiceStatus.setRequestService(requestService);
        requestServiceStatus.setStatus(status);
        requestService.getStatuses().add(requestServiceStatus);

        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        requestService.setUser(user);

        repository.save(requestService);
        return requestService;
    }

    @Override
    public RequestService handle(UpdateRequestServiceCommand command) {
        var requestService = repository.findById(command.id())
                .orElseThrow(() -> new IllegalArgumentException("RequestService not found"));

        Status status = statusRepository.findById(command.statusId())
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));
        requestService.setStatus(status);

        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        requestService.setUser(user);

        repository.save(requestService);
        return requestService;
    }

    @Override
    public void handle(DeleteRequestServiceCommand command) {
        repository.deleteById(command.id());
    }

    public RequestService updateStatus(RequestService requestService, Long statusId) {
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Status not found"));
        requestService.setStatus(status);

        for (RequestServiceStatus requestServiceStatus : requestService.getStatuses()) {
            requestServiceStatus.setStatus(status);
        }

        repository.save(requestService);

        if (status.getName() == StatusName.Accepted) {
            Trip trip = new Trip();
            trip.setName("Accepted");
            trip.setType(requestService.getType());
            trip.setWeight(requestService.getWeight());
            trip.setUnloadDirection(requestService.getUnloadDirection());
            trip.setUnloadLocation(requestService.getUnloadLocation());
            trip.setUnloadDate(requestService.getUnloadDate());
            trip.setNumberPackages(requestService.getNumberPackages());
            trip.setHolderName(requestService.getHolderName());
            trip.setDestinationAddress(requestService.getDestinationAddress());
            trip.setLoadDetail(requestService.getLoadDetail());
            trip.setPickupAddress(requestService.getPickupAddress());
            trip.setDestinationDate("2024-11-21");
            trip.setTotalAmount(0.0);

            trip.setVehicle(null);
            trip.setDriver(null);

            tripRepository.save(trip);
        }

        return requestService;
    }

    public RequestService assignVehicle(RequestService requestService, Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + vehicleId));
        // Assuming there is a method to set the vehicle in RequestService
        requestService.setVehicle(vehicle);

        repository.save(requestService);
        return requestService;
    }

    public RequestService assignDriver(RequestService requestService, Long driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: " + driverId));
        // Assuming there is a method to set the driver in RequestService
        requestService.setDriver(driver);

        repository.save(requestService);
        return requestService;
    }
}