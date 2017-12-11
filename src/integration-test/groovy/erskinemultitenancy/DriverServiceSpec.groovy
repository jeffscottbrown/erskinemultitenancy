package erskinemultitenancy

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

        Tenants.withId('Chevy') {
            Vehicle chevelle = vehicleService.saveVehicle('Chevelle')
            chevelle.addToDrivers name: 'Approved Chevy One', status: DriverStatus.APPROVED
            chevelle.addToDrivers name: 'Inactive Chevy One', status: DriverStatus.INACTIVE

            vehicleService.saveVehicle chevelle
        }
    }

    void 'test retrieving drivers by status'() {
        expect:
        Tenants.withId('Ford') {
            assert driverService.findAllDriversByStatus(DriverStatus.APPROVED)?.size() == 3
            assert driverService.findAllDriversByStatus(DriverStatus.INACTIVE)?.size() == 4
            assert driverService.findAllDriversByStatus(DriverStatus.PENDING)?.size() == 0
            assert !driverService.findAllDriversByStatus(DriverStatus.APPROVED)?.findResult { it.name == 'Approved Chevy One' }
            true
        }

        and: "check the chevy drivers"
        Tenants.withId('Chevy') {
            assert driverService.findAllDriversByStatus(DriverStatus.APPROVED)?.size() == 1
            assert driverService.findAllDriversByStatus(DriverStatus.INACTIVE)?.size() == 1
            assert driverService.findAllDriversByStatus(DriverStatus.PENDING)?.size() == 0
            assert driverService.findAllDriversByStatus(DriverStatus.APPROVED)?.findResult { it.name == 'Approved Chevy One' }
            true
        }

    }
}
