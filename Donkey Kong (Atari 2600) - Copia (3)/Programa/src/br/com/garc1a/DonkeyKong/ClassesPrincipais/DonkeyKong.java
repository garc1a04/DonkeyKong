package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.garc1a.DonkeyKong.ClassesBase.Elemento;

public class DonkeyKong extends Elemento{
	private int linha;
	private int tempo;
	private int coluna;
	
	private boolean esquerda = true;
	private boolean direita = false;
	
	public DonkeyKong(int px, int py, int largura, int altura) {
		super(px, py, largura, altura);
		setImagem(new ImageIcon("src\\\\br\\\\com\\\\garc1a\\\\DonkeyKong\\\\ClassesPrincipais\\\\Sprite\\\\DK.png"));
	}
	
	public void atualiza() {
		incPx(getVel() * getDx());
		incPy(getVel() * getDy());
		
		if(Jogo.controleTecla[3]) {
			esquerda = true;
			direita = false; 
		} if(Jogo.controleTecla[2]) {
			esquerda = false; 
			direita = true;
		}
	}
	
	public void desenha(Graphics2D g) {
		int pX = getPx()+100;
		int pY = getPy()-50;
		
		// Largura e altura da moldura
		int largMoldura = getImagem().getIconWidth() / 2;
		int altMoldura = getImagem().getIconHeight();
		
		// Largura e altura do recorte da imagem
		int largImg = largMoldura * (coluna);
		int altImg = altMoldura * linha;
		
		if(esquerda) {
			g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg, altImg, largImg + largMoldura, altImg + altMoldura, null);
		}if(direita){
			g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg + largMoldura, altImg, largImg, altImg + altMoldura, null);	
		}
		
	}
}
