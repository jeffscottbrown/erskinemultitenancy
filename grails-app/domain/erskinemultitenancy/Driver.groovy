package erskinemultitenancy

import grails.gorm.MultiTenant

class Driver implements MultiTenant<Driver> {
    String name
    DriverStatus status
    String manufacturer

    def beforeInsert() {
        manufacturer = vehicle.manufacturer
    }

    def beforeUpdate() {
        manufacturer = vehicle.manufacturer
    }

    static belongsTo = [vehicle: Vehicle]

    static mapping = {
        tenantId name: 'manufacturer'
    }

    @Override
    String toString() {
        name
    }
}
