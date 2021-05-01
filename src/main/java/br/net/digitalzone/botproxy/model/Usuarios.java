package br.net.digitalzone.botproxy.model;

public enum Usuarios {
	LUIZ("Luiz", "@lfelipe1993"),
	JOABE("Cafet�o da Bah�a", "@Jobasbdo"),
	//FERNANDO("Mestre dos podcasts", "@Fernando9790"),
	EMERSON("Rei dos modelos de reclama��es", "@emersonbezerril");
	//FELIPE("Ga�cho de pelotas", "@Felipe_Quadri");
	
	private String nome;
	private String user;
	
	private Usuarios(String nome, String user) {
		this.nome = nome;
		this.user = user;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
