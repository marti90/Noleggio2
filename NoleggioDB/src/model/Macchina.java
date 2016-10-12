package model;

public class Macchina {
	
	private int id_macchina;
	private String modello;
	private String targa;
	
	public Macchina(){
		
	}
	
	public Macchina(int id, String modello, String targa){
		this.id_macchina = id;
		this.modello = modello;
		this.targa = targa;
	}
	
	public int getId_macchina() {
		return id_macchina;
	}
	
	public void setId_macchina(int id_macchina) {
		this.id_macchina = id_macchina;
	}
	
	public String getModello() {
		return modello;
	}
	
	public void setModello(String modello) {
		this.modello = modello;
	}
	
	public String getTarga() {
		return targa;
	}
	
	public void setTarga(String targa) {
		this.targa = targa;
	}

}
