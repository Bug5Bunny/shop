package org.chocolate.shop.connectionmanager;

public enum DataBase implements PropertyFileNameProvider {

	LOCALDB("localDB.properties");

	private String propertyFileName;

	DataBase(final String propertyFileName) {
		this.propertyFileName = propertyFileName;
	}

	@Override
	public String getName() {
		return this.propertyFileName;
	}
}
