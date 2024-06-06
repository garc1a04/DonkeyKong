package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.garc1a.DonkeyKong.ClassesBase.Elemento;

public class Jurandinho extends Elemento{
	private int linha;
	private int coluna;
	private int tempo;
	private boolean andar = true;
	private boolean sinal = true;
	
	public Jurandinho(int px, int py, int largura, int altura) {
		super(px, py, largura, altura);
		setImagem(new ImageIcon("src\\\\br\\\\com\\\\garc1a\\\\DonkeyKong\\\\ClassesPrincipais\\\\Sprite\\\\Jurandinho.png"));
	}
	
	public void atualiza() {
		incPx(getVel() * getDx());
		incPy(getVel() * getDy());
		
		if(andar) {
			if(sinal) {
				this.setPx(this.getPx()+7);			
			}else {					
				this.setPx(this.getPx()-7);						
			}
		}
	}
	
	public void desenha(Graphics2D g) {
		int pX = getPx() + 20;
		int pY = getPy() - 10;
		
		// Largura e altura da moldura
		int largMoldura = getImagem().getIconWidth();
		int altMoldura = getImagem().getIconHeight();
		
		// Largura e altura do recorte da imagem
		int largImg = largMoldura * (coluna);
		int altImg = altMoldura * linha;
		
		g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, largImg, altImg, largImg + largMoldura, altImg + altMoldura, null);
	}
	
	public boolean isSinal() {
		return sinal;
	}

	public void setSinal(boolean sinal) {
		this.sinal = sinal;
	}

	public boolean isAndar() {
		return andar;
	}

	public void setAndar(boolean andar) {
		this.andar = andar;
	}
}
