package org.chocolate.shop.connectionmanager;

public enum DataBase implements PropertyFileNameProvider {

    LOCALDB("localDB.properties");
    
    private String propertyFileName;
    
    DataBase(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }
    
    public String getName() {
        return this.propertyFileName;
    }
}
