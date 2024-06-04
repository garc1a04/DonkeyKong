package br.com.garc1a.DonkeyKong.ClassesPrincipais;

public class Jogador implements Comparable<Jogador>{
	private String nome;
	private int pontos;
	
	Jogador(String nome, int pontos){
		setNomes(nome);
		setPontos(pontos);	}
	
	public String getNomes() {
		return nome;
	}
	
	public void setNomes(String nomes) {
		this.nome = nomes;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	@Override
	public int compareTo(Jogador o) {
		if((this.getPontos() - o.getPontos()) < 0) {
			return -1;
		}
			
		return 1;
	}
}
