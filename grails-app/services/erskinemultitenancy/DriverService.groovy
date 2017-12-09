package erskinemultitenancy

import grails.gorm.services.Service

@Service(Driver)
interface DriverService {
    List<Driver> findAllDriversByStatus(DriverStatus status)
}
