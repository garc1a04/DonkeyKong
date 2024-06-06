package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Text;

import br.com.garc1a.DonkeyKong.ClassesBase.CenarioPadrao;

public class Ranking extends CenarioPadrao{
	private List<Jogador> Ranking = new ArrayList<>();
	
	private File arquivo;// TODO estudar como esse troço funciona
	Font fonte;
	Jogador player;
	
	public Ranking(int largura, int altura) {
		super(largura, altura);
		
	}

	@Override
	public void carregar() {
		try {
			fonte = Font.createFont(Font.TRUETYPE_FONT,
			        new File("src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Font\\ARCADE_I.TTF")).deriveFont(Font.PLAIN, 25);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		player = new Jogador(Jogo.getNomeJogador(),JogoCenario.getPontosSomatoria());
		
		Ranking.add(player);
		
		for(int i = 1; i < 5;i++) {
			Ranking.add(new Jogador("NULL", 900000+i));
		}
		
		Collections.sort(Ranking);
	}

	@Override
	public void descarregar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atualizar(){
	}

	@Override
	public void desenhar(Graphics2D g){
		g.setFont(fonte);
		
		int distancia = 30;
		for(int i = 0; i < 5 ;i++) {
			
			if(Ranking.get(i).getNomes().equals(Jogo.getNomeJogador())) {
				g.drawString("HIGH SCORE", 200, 60);
				g.drawString(""+player.getPontos(), 250, 90);
			}
			
			g.setColor(Color.RED);
			g.drawString(" RANK "+" PONTOS "+" NOME ",90, 200);
			
			if(Ranking.get(i).getNomes().equals(Jogo.getNomeJogador())) {
				g.setColor(Color.YELLOW);
				
			}else {
				g.setColor(new Color(107,83,160));
			}
			
			String posicao3 = i == 2 ? "RD" : "TH";
			String posicao2 = i == 1 ? "ND" : posicao3;
			String posicao = i == 0 ? "ST" : posicao2;
			
			g.drawString("  "+(i+1)+posicao+"  "+Ranking.get(i).getPontos()+"  "+Ranking.get(i).getNomes(), 90, 230 + distancia);
			g.setColor(Color.WHITE);
			distancia+=50;
		}	
	}
}
