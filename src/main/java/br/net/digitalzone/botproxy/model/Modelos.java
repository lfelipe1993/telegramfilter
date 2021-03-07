package br.net.digitalzone.botproxy.model;

public enum Modelos {
	//SE
	iSE_64("SE", "64",2400.00,"SE64"),
	iSE_128("SE", "128",2600.00,"SE128"),
	iSE_256("SE", "256",2800.00,"SE256"),
	//XR
	iXR_64("XR", "64",3450.00,"XR64"),
	iXR_128("XR", "128",3650.00, "XR128"),
	//11
	i11_64("11", "64",4200.00,"1164"),
	i11_128("11", "128",4500.00, "11128"),
	i11_256("11", "256",4800.00, "11256"),
	//12
	i12_64("12", "64",5400.00,"1264"),
	i12_128("12", "128",5800.00, "12128"),
	i12_256("12", "256",6200.00, "12256"),
	//12P
	i12_128P("12P", "128",6500.00, "12P128"),
	i12_256P("12P", "256",7000.00, "12P256"),
	i12_512P("12P", "512",7500.00, "12P256"),
	//12PM
	i12_128PM("12PM", "128",7700.00, "12PM128"),
	i12_256PM("12PM", "256",8200.00, "12PM256"),
	i12_512PM("12PM", "512",8500.00, "12PM256");

	private String model;
	private String armaz;
	private Double limitPrice;
	private String sigla;
	
	private Modelos(String model, String armaz, Double limitPrice, String sigla) {
		this.model = model;
		this.armaz = armaz;
		this.limitPrice = limitPrice;
		this.sigla = sigla;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getArmaz() {
		return armaz;
	}

	public void setArmaz(String armaz) {
		this.armaz = armaz;
	}

	public Double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(Double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public static Modelos toEnum(String sigla) {
		if (sigla == null) {
			return null;
		}

		for (Modelos x : Modelos.values()) {
			if (sigla.equals(x.getSigla())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Modelo inválido: " + sigla);
	}

}
