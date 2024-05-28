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
	private ImageIcon mario = new ImageIcon("C:\\Users\\user\\Desktop\\Donkey Kong (Atari 2600)\\Programa\\src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\Mario.png");
	private ImageIcon pulinho = new ImageIcon("C:\\Users\\user\\Desktop\\Donkey Kong (Atari 2600)\\Programa\\src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\PulinMario.png");
	
	public Mario(int px, int py, int largura, int altura) {
		super(px, py, largura, altura);
		setImagem(mario);
	}
	
	public void atualiza() {
		incPx(getVel() * getDx());
		incPy(getVel() * getDy());
		
		if(Jogo.controleTecla[2]){
			tempo++;
			
			if(tempo > 1) {
				tempo = 0;
				coluna++;
			}
			esquerda = true;
			direita = false;
			
		} if (Jogo.controleTecla[3]) {
			tempo++;
			if(tempo > 1) {
				tempo = 0;
				coluna++;
			}
			direita = true;
			esquerda = false;
			
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
		
		if(direita && !JogoCenario.pulou) {
			g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg, altImg, largImg + largMoldura, altImg + altMoldura, null);
		}else if(esquerda && !JogoCenario.pulou){
			g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg + largMoldura, altImg, largImg, altImg + altMoldura, null);	
		
		}else if(JogoCenario.pulou && (Jogo.controleTecla[3] || direita)){
			g.drawImage(pulinho.getImage(), pX, pY, pX + pulinho.getIconWidth(), pY + pulinho.getIconHeight(), 0, 0,pulinho.getIconWidth(),pulinho.getIconHeight(), null);
		
		}else if(JogoCenario.pulou && (Jogo.controleTecla[2] || esquerda)){
			g.drawImage(pulinho.getImage(), pX, pY, pX + pulinho.getIconWidth(), pY+ pulinho.getIconHeight(), pulinho.getIconWidth(), 0, 0,pulinho.getIconHeight(), null);
			System.out.println("Entrouuuu");
		}	
//		}if(JogoCenario.pulou && direita) {
//			g.drawImage(pulinho.getImage(), pX, pY, pX + pulinho.getIconWidth(), pY + pulinho.getIconHeight(), 0, 0,pulinho.getIconWidth(),pulinho.getIconHeight(), null);
//		}if(JogoCenario.pulou && esquerda) {
//			g.drawImage(pulinho.getImage(), pX, pY, pX + pulinho.getIconWidth(), pY + pulinho.getIconHeight(), 0, 0,pulinho.getIconWidth(),pulinho.getIconHeight(), null);

	}
}
