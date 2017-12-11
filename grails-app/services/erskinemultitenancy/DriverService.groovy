package erskinemultitenancy

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.services.Service

@CurrentTenant
@Service(Driver)
interface DriverService {
    List<Driver> findAllDriversByStatus(DriverStatus status)
}
