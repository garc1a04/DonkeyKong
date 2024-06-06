package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.garc1a.DonkeyKong.ClassesBase.Elemento;
import br.com.garc1a.DonkeyKong.ClassesPrincipais.JogoCenario.Direcao;

public class Mario extends Elemento{
	private int linha;
	private int coluna;
	private int tempo;
	
	private boolean direita = true;
	private boolean esquerda = false;
	
	private boolean pulin = false;
	private ImageIcon mario = new ImageIcon("src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\Mario.png");
	private ImageIcon pulinho = new ImageIcon("src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\PulinMario.png");
	private ImageIcon subindoEscada = new ImageIcon("src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\SubindoEscada.png");
	private int coluna2;
	private int linha2;
	private int tempo2;
	private boolean apertando;
	
	public Mario(int px, int py, int largura, int altura) {
		super(px, py, largura, altura);
		setImagem(mario);
	}
	
	public void atualiza() {
		incPx(getVel() * getDx());
		incPy(getVel() * getDy());
		
		if(Jogo.controleTecla[0]  && JogoCenario.isEscada()) {
			apertando = true;
			
			tempo2++;
			if(tempo2 > 3) {
				tempo2 = 0;
				coluna2++;
			}
			
			if(coluna2 > 1) {
				coluna2 = 0;
			}
			
		}if(Jogo.controleTecla[1]  && JogoCenario.isEscada()) {
			apertando = true;
			
			tempo2++;
			
			if(tempo2 > 3) {
				tempo2 = 0;
				coluna2++;
			}
			
			if(coluna2 > 1) {
				coluna2 = 0;
			}
			
		}
		
		if(Jogo.controleTecla[2] && (!JogoCenario.isEscada()  || JogoCenario.peso)){
			tempo++;
			
			if(tempo > 1) {
				tempo = 0;
				coluna++;
			}
			esquerda = true;
			direita = false;
			apertando = false;
			
		} if (Jogo.controleTecla[3]  && (!JogoCenario.isEscada() || JogoCenario.peso)) {
			tempo++;
			if(tempo > 1) {
				tempo = 0;
				coluna++;
			}
			direita = true;
			esquerda = false;
			apertando = false;
		} if(coluna > 3) {
			coluna = 0;
		}
	}
	
	public void desenha(Graphics2D g) {
		int pX = getPx() + 20;
		int pY = getPy() - 20;
		
		// Largura e altura da moldura
		int largMoldura = getImagem().getIconWidth() / 4;
		int altMoldura = getImagem().getIconHeight();
		
		// Largura e altura do recorte da imagem
		int largImg = largMoldura * (coluna);
		int altImg = altMoldura * linha;
		
		//Largura e altura subindo
		int largMoldur2 = subindoEscada.getIconWidth()/2;
		int altMoldur2 = subindoEscada.getIconHeight();
		
		// Largura e altura do recorte da imagem
		int largImg2 = largMoldura * coluna2;
		int altImg2 = altMoldura * linha2;
		
		if(direita && !JogoCenario.pulou && !apertando) {
			g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg, altImg, largImg + largMoldura, altImg + altMoldura, null);
		
		}else if(esquerda && !JogoCenario.pulou && !apertando){
			g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg + largMoldura, altImg, largImg, altImg + altMoldura, null);	
		
		}else if(JogoCenario.pulou && (Jogo.controleTecla[3] || direita)){
			g.drawImage(pulinho.getImage(), pX, pY, pX + pulinho.getIconWidth(), pY + pulinho.getIconHeight(), 0, 0,pulinho.getIconWidth(),pulinho.getIconHeight(), null);
		
		}else if(JogoCenario.pulou && (Jogo.controleTecla[2] || esquerda)){
			g.drawImage(pulinho.getImage(), pX, pY, pX + pulinho.getIconWidth(), pY+ pulinho.getIconHeight(), pulinho.getIconWidth(), 0, 0,pulinho.getIconHeight(), null);
			
		}else if(Jogo.controleTecla[0] || JogoCenario.isEscada()){
			
			g.drawImage(subindoEscada.getImage(), pX, pY, pX + largMoldur2, pY + altMoldur2, largImg2+5, altImg2, largImg2 + largMoldur2, altImg2 + altMoldur2, null);
			
		}else if(Jogo.controleTecla[1] || JogoCenario.isEscada()){
			g.drawImage(subindoEscada.getImage(), pX, pY, pX + largMoldur2, pY + altMoldur2, largImg2 + largMoldur2, altImg2, largImg2+5, altImg2 + altMoldur2, null);	
		
		} else {
			g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg, altImg, largImg + largMoldura, altImg + altMoldura, null);
		}
	}
}
