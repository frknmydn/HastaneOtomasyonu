package Helper;

public class Item {
	private int key;
	private String value;
	
	
	///bu item class�n�n amac� verileri hashmap mant���yla ayn�. �rnek kullan�m� kullan�c�n�n id'siyle 
	//name'ini item i�inde saklay�p gerekli yerde kullanmay� verebilirz.
	
	
	public Item(int key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.value;
	}
}
