package erskinemultitenancy

import grails.gorm.services.Service

@Service(Vehicle)
interface VehicleService {
    Vehicle saveVehicle(String model)
    Vehicle saveVehicle(Vehicle vehicle)
}
