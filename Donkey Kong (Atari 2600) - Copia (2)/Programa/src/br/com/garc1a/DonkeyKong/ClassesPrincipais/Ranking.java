package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Text;

import br.com.garc1a.DonkeyKong.ClassesBase.CenarioPadrao;

public class Ranking extends CenarioPadrao{
	private List<Jogador> Ranking = new ArrayList<>();
	
	private File arquivo;// TODO estudar como esse troço funciona
	
	public Ranking(int largura, int altura) {
		super(largura, altura);
		
	}

	@Override
	public void carregar() {
		Ranking.add(new Jogador(Jogo.getNomeJogador(), 99999));
	}

	@Override
	public void descarregar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar(){
		Collections.sort(Ranking);
	}

	@Override
	public void desenhar(Graphics2D g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("Tahoma", Font.PLAIN, 40));
		g.drawString("* Ranking *", 250, 100);
		int distancia = 30;
		
		for(int i = 1; i <= 5 ;i++) {
			if(i <= Ranking.size()) {
				if(i == 1) {
					g.setColor(Color.yellow);
				}
				g.drawString(i+"- "+Ranking.get(i-1).getNomes()+": "+Ranking.get(i-1).getPontos(), 50, 200 + distancia);				
				g.setColor(Color.WHITE);
			} else {
				g.drawString(i+"- null "+": "+"00000",50, 200+distancia);
			}
			
			distancia+=50;
		}
		
	}
}
