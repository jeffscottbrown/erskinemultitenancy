package erskinemultitenancy

import grails.gorm.MultiTenant

class Vehicle implements MultiTenant<Vehicle> {
    String manufacturer
    String model
    static hasMany = [drivers: Driver]

    static mapping = {
        tenantId name: 'manufacturer'
    }
}
