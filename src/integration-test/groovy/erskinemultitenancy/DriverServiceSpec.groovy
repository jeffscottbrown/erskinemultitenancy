package erskinemultitenancy

import grails.gorm.multitenancy.Tenant
import grails.gorm.multitenancy.Tenants
import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import spock.lang.Shared
import spock.lang.Specification

@Integration
class DriverServiceSpec extends Specification {

    @Shared
    DriverService driverService

    @Shared
    VehicleService vehicleService

    @OnceBefore
    void setupData() {
        Tenants.withId('Ford') {
            Vehicle fusion = vehicleService.saveVehicle('Fusion')
            fusion.addToDrivers name: 'Approved One', status: DriverStatus.APPROVED
            fusion.addToDrivers name: 'Approved Two', status: DriverStatus.APPROVED
            fusion.addToDrivers name: 'Inactive One', status: DriverStatus.INACTIVE
            vehicleService.saveVehicle fusion

            Vehicle focus = vehicleService.saveVehicle('Focus')
            focus.addToDrivers name: 'Approved Three', status: DriverStatus.APPROVED
            focus.addToDrivers name: 'Inactive Two', status: DriverStatus.INACTIVE
            focus.addToDrivers name: 'Inactive Three', status: DriverStatus.INACTIVE
            focus.addToDrivers name: 'Inactive Four', status: DriverStatus.INACTIVE

            vehicleService.saveVehicle focus
        }
    }

    void 'test retrieving drivers by status'() {
        expect:
        driverService.findAllDriversByStatus(DriverStatus.APPROVED)?.size() == 3
        driverService.findAllDriversByStatus(DriverStatus.INACTIVE)?.size() == 4
        driverService.findAllDriversByStatus(DriverStatus.PENDING)?.size() == 0
    }
}
