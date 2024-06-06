package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.garc1a.DonkeyKong.ClassesBase.Elemento;

public class Vida extends Elemento{
	private static int VIDA = 1;
	
	private ImageIcon VidaCenario1 = new ImageIcon("src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\VidaCenario1.png");
	private ImageIcon VidaCenario2 = new ImageIcon("src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\VidaCenario2.png");

	private int perdeuVida;

	private int reducao = 1;
	
	public Vida(int px, int py, int largura, int altura) {
		super(px, py, largura, altura);
		if(JogoCenario.getNIVEL() < 1) {
			setImagem(VidaCenario1);
		} else {
			setImagem(VidaCenario2);
		}
	}
	
	public void atualiza() {
		
		if(getVIDA() == 2) {
			perdeuVida = 20;
			reducao = 2;
		}
	}
	
	public void desenha(Graphics2D g) {
		int pX = getPx();
		int pY = getPy();
		
		// Largura e altura da moldura
		int largMoldura = getImagem().getIconWidth()/reducao;
		int altMoldura = getImagem().getIconHeight();
		if(getVIDA()  > 1) {
			g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura+50 - perdeuVida, pY + altMoldura+20, 0, 0,largMoldura,altMoldura, null);			
		
		}
	}
	
	public static int getVIDA() {
		return VIDA;
	}

	public static void setVIDA(int Vida) {
		VIDA = Vida;
	}
	
}
