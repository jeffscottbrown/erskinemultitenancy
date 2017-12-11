package erskinemultitenancy

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.services.Service

@CurrentTenant
@Service(Vehicle)
interface VehicleService {
    Vehicle saveVehicle(String model)

    Vehicle saveVehicle(Vehicle vehicle)
}
