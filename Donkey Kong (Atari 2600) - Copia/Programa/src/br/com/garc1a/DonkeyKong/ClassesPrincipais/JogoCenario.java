package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import br.com.garc1a.DonkeyKong.ClassesBase.CenarioPadrao;
import br.com.garc1a.DonkeyKong.ClassesBase.Elemento;
import br.com.garc1a.DonkeyKong.ClassesBase.MatUtil;
import br.com.garc1a.DonkeyKong.ClassesBase.Texto;
import br.com.garc1a.DonkeyKong.ClassesBase.Util;

public class JogoCenario extends CenarioPadrao{
	
	public enum Estado {
		JOGANDO, GANHOU, PERDEU
	}
	
	public enum Direcao {
		NORTE, SUL, OESTE, LESTE;
	}
	
	
	private Direcao prxDirecao = Direcao.OESTE;
	
	private Texto texto = new Texto();
	
	
	private int contadorNivel = 0;
	
	private static final int _LARG = 15;
	int py = 0,largura = 0;
	private static int VIDA = 3;
	
	private final ImageIcon cenario = new ImageIcon("C:\\Users\\user\\Desktop\\Donkey Kong (Atari 2600)\\Programa\\src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\Cenario1.png");
	
	// Personagens :)
	private Mario mario;
	private DonkeyKong dk;
	private Elemento princesa;
	private Elemento martelo;
	private Elemento cookie;
	
	private boolean escada = true;
	private Elemento[] nivel;
	private boolean colidiu = false;
	private boolean pressionou = false;
	static boolean pulou = false;
	static boolean peso = true;
	
	private Estado estado = Estado.JOGANDO;
	
	public JogoCenario(int largura, int altura) {
		super(largura, altura);
	}

	@Override
	public void carregar() {
		texto.setCor(Color.WHITE);
		
		// mario :)
		mario = new Mario(150,530,_LARG,_LARG);
		mario.setVel(5);
		mario.setAtivo(true);
		mario.setCor(Color.RED);
		
		//DK :)
		dk = new DonkeyKong(25,50,_LARG,_LARG);
		dk.setAtivo(true);
		dk.setCor(new Color(66,38,0));
		
		int diagonal = 1;
		int DI = -2;
		
		char[][] nivelSelecionado = Niveis.niveis[0];
		nivel = new Elemento[Niveis.niveis[0].length*20];
		
		for (int linha = 0; linha < nivelSelecionado.length; linha++) {
			for (int coluna = 0; coluna < nivelSelecionado[0].length; coluna++) {
				if (nivelSelecionado[linha][coluna] == 'B') {

					Elemento e = new Elemento();
					e.setAtivo(true);
					 e.setCor(new Color(148,0,211));
					
					e.setPx(_LARG * coluna);
					e.setPy(_LARG * linha);

					e.setAltura(_LARG);
					e.setLargura(_LARG);
					
					e.setIndicacao('B');
					nivel[contadorNivel++] = e;
					
				}if (nivelSelecionado[linha][coluna] == 'D') {

					Elemento e = new Elemento();
					e.setAtivo(true);
					 e.setCor(new Color(148,0,213));
					
					e.setPx(_LARG * coluna-1);
					e.setPy(_LARG * linha-14-diagonal);

					e.setAltura(_LARG);
					e.setLargura(_LARG);
					e.setIndicacao('I');
					nivel[contadorNivel++] = e;
					diagonal++;
				}if (nivelSelecionado[linha][coluna] == 'U') {

					Elemento e = new Elemento();
					e.setAtivo(true);
					e.setCor(new Color(148,0,212));
					
					e.setPx(_LARG * coluna-10);
					e.setPy(_LARG * linha-51+DI);

					e.setAltura(_LARG);	
					e.setLargura(_LARG);
					e.setIndicacao('I');
					
					nivel[contadorNivel++] = e;
					DI++; 
				
				}if (nivelSelecionado[linha][coluna] == 'I') {

					Elemento e = new Elemento();
					e.setAtivo(true);
					e.setCor(new Color(148,0,212));
					
					e.setPx(_LARG * coluna);
					e.setPy(_LARG * linha-51+DI);

					e.setAltura(_LARG);	
					e.setLargura(_LARG);
					e.setIndicacao('I');
					
					nivel[contadorNivel++] = e;
					DI++;
					
				}if (nivelSelecionado[linha][coluna] == 'F') {

					Elemento e = new Elemento();
					e.setAtivo(true);
					e.setCor(new Color(150,75,0));
					
					e.setPx(_LARG * coluna);
					e.setPy(_LARG * linha-51+DI);

					e.setAltura(_LARG);	
					e.setLargura(_LARG);
					e.setIndicacao('F');
					
					nivel[contadorNivel++] = e;
					DI++;
					
				} if (nivelSelecionado[linha][coluna] == 'E') {
					
					Elemento e = new Elemento();
					e.setAtivo(true);
					e.setCor(new Color(150,75,0));

					e.setPx(_LARG * coluna);
					e.setPy(_LARG * linha);
					
					e.setAltura(_LARG);
					e.setLargura(_LARG);
					e.setIndicacao('E');
					
					nivel[contadorNivel++] = e;
				}
			}
		}
	}
	
	@Override
	public void descarregar() { // resetar o jogo :)
		mario = new Mario(150,520,_LARG,_LARG);
		mario.setVel(5);
		mario.setAtivo(true);
		mario.setCor(Color.RED);
	}

	@Override
	public void atualizar() { // Movimentação, colisão e etc
		if(estado != Estado.JOGANDO) {
			return;
		} if (Jogo.controleTecla[3] && !Jogo.controleTecla[4]) {//direita
			mario.setPx(mario.getPx()+mario.getVel());

		} else if (Jogo.controleTecla[2] && !Jogo.controleTecla[4]) { //esquerda
			mario.setPx(mario.getPx()-mario.getVel());

		}else if(Jogo.controleTecla[4] && !pressionou && !pulou){ // pular
			mario.setPy(mario.getPy() - 25);
			
			if(Jogo.controleTecla[4]) {
				pressionou = true;
				if(Jogo.controleTecla[2] || Jogo.controleTecla[3]) {
					int DirEsq = Jogo.controleTecla[2] ? 0-mario.getVel() : mario.getVel();
					mario.setPx(mario.getPx()+DirEsq);				
				}
			}
			
			
			pulou = true;
			peso = false;
			
		}else if(Jogo.controleTecla[0]) {
			peso = false;
		} else if(Jogo.controleTecla[1]) {
			peso = false;
		}
		
		int i = 0;
		// peso	
		while(i < 10001 && pulou && peso) {
			if(i == 1000) {
				mario.setPy(mario.getPy()+5);
				pressionou = false;
			}
			i++;
		}
		
		while(pulou && !peso && pressionou) {
			mario.setPy(mario.getPy()+5);
			peso = true;
			pressionou = false;
			i++;
		}
		
		for (Elemento e : nivel) {
			
			if (e == null)
				break;
			if (Util.colide(mario, e) && e.getIndicacao() == 'B'){
				pulou = false;
				colidiu = true;
				peso = true;
				
				mario.setPy(e.getPy() -  e.getLargura());
				
			}if (Util.colide(mario, e) && e.getIndicacao() == 'I'){
				pulou = false;
				colidiu = true;
				peso = true;
				
				mario.setPy(e.getPy() -  e.getLargura());
				System.out.println(e.getIndicacao());
				
			}if (Util.colide(mario, e) && e.getIndicacao() == 'F' && (!(Jogo.controleTecla[1]) || !(Jogo.controleTecla[0]))){
				pulou = false;
				colidiu = true;
				peso = false;
				
				mario.setPy(e.getPy() -  e.getLargura());
				System.out.println(e.getIndicacao());
				
			}if(Jogo.controleTecla[0] && Util.colide(mario, e) && e.getIndicacao() == 'E'){
				peso = false;
				mario.setPy(mario.getPy() - mario.getVel()/2);
				
			}if(Jogo.controleTecla[1] && Util.colide(mario, e) &&  e.getIndicacao() == 'F' ){
				mario.setPy(mario.getPy() + mario.getVel()/2);
				System.out.println("......................................................");
				peso = true;
				
			}else{
				peso = true;
				colidiu = false;
			}
		}
		
		mario.atualiza();
	}
	
	@Override
	public void desenhar(Graphics2D g) {	
		
		g.drawImage(cenario.getImage(),100, 50, 1000,900, 0, 0, 400,274, null);// imagem do cenario
		
//		for (Elemento e : nivel) {
//			if (e == null)
//				break;
//			if(e.getIndicacao() == 'E') {
//				e.desenha(g);								
//			} if(e.getIndicacao() == 'F') {
//				e.desenha(g);								
//			}
//		}
		
		
		
		
        mario.desenha(g);
        
        dk.desenha(g);
	}

}
