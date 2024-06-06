package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.garc1a.DonkeyKong.ClassesBase.Elemento;

public class ChaoFase2 extends Elemento{
	private boolean ativo = true;
	private boolean repetiu = false;;
	

	public ChaoFase2(int px, int py, int largura, int altura) {
		super(px, py, largura, altura);
		setImagem(new ImageIcon("src\\\\br\\\\com\\\\garc1a\\\\DonkeyKong\\\\ClassesPrincipais\\\\Sprite\\\\Chao.png"));
		
	}
	
	public void desenha(Graphics2D g){
		int pX = getPx() + 20;
		int pY = getPy() - 10;
		
		// Largura e altura da moldura
		int largMoldura = getImagem().getIconWidth();
		int altMoldura = getImagem().getIconHeight();
		
		// Largura e altura do recorte da imagem
		int largImg = largMoldura;
		int altImg = altMoldura;
		
		g.drawImage(getImagem().getImage(), pX, pY, pX + largMoldura, pY + altMoldura, 0,0, largMoldura+10, altImg + 10, null);
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isRepetiu() {
		// TODO Auto-generated method stub
		return repetiu;
	}
	
	public void setRepetiu(boolean Repetiu) {
		 repetiu = Repetiu;
	}
}
