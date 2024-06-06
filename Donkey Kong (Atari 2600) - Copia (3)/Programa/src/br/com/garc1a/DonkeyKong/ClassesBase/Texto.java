package br.com.garc1a.DonkeyKong.ClassesBase;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

public class Texto extends Elemento {
	private Font fonte;
	
	public Texto(int Tamanho) {
		try {
			fonte = Font.createFont(Font.TRUETYPE_FONT,
			        new File("src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Font\\ARCADE_I.TTF")).deriveFont(Font.PLAIN, Tamanho);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public Texto(Font fonte) {
		this.fonte = fonte;
	}

	public void desenha(Graphics2D g, String texto) {
		desenha(g, texto, getPx(), getPy());
	}

	public void desenha(Graphics2D g, String texto, int px, int py) {
		if (getCor() != null)
			g.setColor(getCor());
		
		g.setFont(fonte);
		g.drawString(texto, px, py);
	}

	public Font getFonte() {
		return fonte;
	}

	public void setFonte(Font fonte) {
		this.fonte = fonte;
	}

}