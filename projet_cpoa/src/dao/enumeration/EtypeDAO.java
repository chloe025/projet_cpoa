package dao.enumeration;

public enum EtypeDAO {
	MYSQL(0), MEMORY(1);
	
	private int code;
	
	public int getCode() {
		return code;
	}
	
	EtypeDAO(int code){
		this.code = code;
	}
	
	EtypeDAO getTypeFromCide(int code) {
		if (code == 0)
			return MYSQL;
		else return MEMORY;
	}
}
