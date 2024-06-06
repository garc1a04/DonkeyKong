package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import br.com.garc1a.DonkeyKong.ClassesBase.CenarioPadrao;
import br.com.garc1a.DonkeyKong.ClassesBase.Elemento;
import br.com.garc1a.DonkeyKong.ClassesBase.MatUtil;
import br.com.garc1a.DonkeyKong.ClassesBase.Texto;
import br.com.garc1a.DonkeyKong.ClassesBase.Util;

public class JogoCenario extends CenarioPadrao{
	
	public enum Estado {
		JOGANDO, GANHOU, PERDEU,PAUSA
	}
	
	public enum Direcao {
		NORTE, SUL, OESTE, LESTE;
	}
	
	private Direcao prxDirecao = Direcao.OESTE;
	
	private Texto texto = new Texto(30);
	
	private static int Pontos = 5000;
	
	private static int pontosSomatoria = 0;
	
	private int contadorNivel = 0;
	
	private static final int _LARG = 15;
	
	int py = 0;
	
	private static Vida VIDA;
	
	private static int NIVEL = 0;
	
	private ImageIcon cenario;
	
	// Coisas dos Personagens :)
	private Mario mario;
	
	private DonkeyKong dk;
	
	private Elemento princesa;
	
	private Elemento martelo;
	
	private Cookies cookie;
	
	Cookies[] cookies = new Cookies[4];
	Jurandinho[] jurandinho = new Jurandinho[4];
	ChaoFase2[] chao = new ChaoFase2[8];
	
	private static boolean escada = false;
	
	private Elemento[] nivel;
	
	private static boolean colidiu = false;
	
	private boolean paraBaixo = false;
	
	private boolean pressionou = false;
	
	static boolean pulou = false;
	
	static boolean peso = true;
	
	private int diagonal = 1;
	
	private int DI = -2;
	
	private Estado estado = Estado.JOGANDO;

	private boolean bateuBorda;

	private boolean pesoCookie;

	private boolean colidiuRecente;

	private int contadorNaoAtivos ;
	
	long tempoInicial;
	
	private static boolean subindo;

	private static boolean saiuColisao;
	
	public JogoCenario(int largura, int altura){
		super(largura, altura);
	}
	
	@Override
	public void carregar() {
		Pontos = 5000;
		
		tempoInicial = System.currentTimeMillis();
		
		colidiu = false;
		
		paraBaixo = false;
		
		pressionou = false;
		
		pulou = false;
		
		peso = true;
		
		escada = false;

		contadorNaoAtivos = 0;
		
		diagonal = 1;
		
		DI = -2;
		
		VIDA = new Vida(530,50,_LARG,_LARG);
		
		//Cenario
		if(getNIVEL() == 0) {
			 cenario = new ImageIcon("src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\Cenario1.png");
		}else if(getNIVEL() == 1) {
			cenario = new ImageIcon("src\\br\\com\\garc1a\\DonkeyKong\\ClassesPrincipais\\Sprite\\Cenario2.png");
		}
		
		// mario :)
		if(getNIVEL() == 0) {
			mario = new Mario(150,530,_LARG,_LARG);
			mario.setVel(5);
			mario.setAtivo(true);
		}else if(getNIVEL() == 1) {
			mario = new Mario(150,510,_LARG,_LARG);
			mario.setVel(5);
			mario.setAtivo(true);
		}
		
		//DK :)
		dk = new DonkeyKong(25,50,_LARG,_LARG);
		dk.setAtivo(true);
		
		//PONTOS :)
		texto.setCor(Color.WHITE);
		
		//INIMIGOS DE CADA FASE
		if(getNIVEL() == 0) {
			cookies[0] = new Cookies(100,130,_LARG,_LARG);
			cookies[0].setAtivo(true);
			
			cookies[1] = new Cookies(100,130,_LARG,_LARG);
			cookies[1].setAtivo(false);
			
			cookies[2] = new Cookies(100,130,_LARG,_LARG);
			cookies[2].setAtivo(false);
			
			cookies[3] = new Cookies(100,130,_LARG,_LARG);
			cookies[3].setAtivo(false);
			
		} else {
			jurandinho[0] = new Jurandinho(100,130,_LARG,_LARG);
			jurandinho[0].setVel(5);
			jurandinho[0].setAtivo(true);
			
			jurandinho[1] = new Jurandinho(100,230,_LARG,_LARG);
			jurandinho[1].setVel(5);
			jurandinho[1].setAtivo(true);
			
			jurandinho[2] = new Jurandinho(100,330,_LARG,_LARG);
			jurandinho[2].setVel(5);
			jurandinho[2].setAtivo(true);
			
			jurandinho[3] = new Jurandinho(100,430,_LARG,_LARG);
			jurandinho[3].setVel(5);
			jurandinho[3].setAtivo(true);
			
			blocosChao();
		}
		
		
		char[][] nivelSelecionado = Niveis.niveis[getNIVEL()];
		
		nivel = new Elemento[nivelSelecionado.length*20];			
		
		for (int linha = 0; linha < nivelSelecionado.length; linha++) {
			for (int coluna = 0; coluna < nivelSelecionado[0].length; coluna++) {
				
				if(getNIVEL() == 0) {
					condicao1(nivelSelecionado,linha,coluna);					
				} else if(getNIVEL() == 1) {
					condicao2(nivelSelecionado,linha,coluna);	
				}
				
			}
		}
	}
	
	private void blocosChao() {
		chao[0] = new ChaoFase2(198,139,_LARG,_LARG);

		chao[1] = new ChaoFase2(483,139,_LARG,_LARG);
		
		chao[2] = new ChaoFase2(198,236,_LARG,_LARG);
		
		chao[3] = new ChaoFase2(483,236,_LARG,_LARG);
		
		chao[4] = new ChaoFase2(198,333,_LARG,_LARG);
		
		chao[5] = new ChaoFase2(483,333,_LARG,_LARG);
		
		chao[6] = new ChaoFase2(198,430,_LARG,_LARG);
		
		chao[7] = new ChaoFase2(483,430,_LARG,_LARG);
	}

	private void condicao2(char[][] nivelSelecionado, int linha, int coluna) {
		if (nivelSelecionado[linha][coluna] == 'B') {
			
			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,211));
			
			e.setPx(_LARG * coluna);
			e.setPy((_LARG * linha));

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			
			e.setIndicacao('B');
			nivel[contadorNivel++] = e;
			
		}if (nivelSelecionado[linha][coluna] == 'G') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,211));
			
			e.setPx(_LARG * coluna);
			e.setPy((_LARG * linha)+9);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			
			e.setIndicacao('B');
			nivel[contadorNivel++] = e;
			
		}if (nivelSelecionado[linha][coluna] == 'E') {
			
			Elemento e = new Elemento();
			e.setAtivo(true);
			e.setCor(new Color(150,75,0));

			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha);
			
			e.setAltura(_LARG);
			e.setLargura(_LARG);
			e.setIndicacao('E');
			
			nivel[contadorNivel++] = e;
		}if (nivelSelecionado[linha][coluna] == 'A') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(Color.WHITE);
			
			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			
			e.setIndicacao('F');
			nivel[contadorNivel++] = e;
			
		}if (nivelSelecionado[linha][coluna] == 'Y') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(Color.WHITE);
			
			e.setPx(_LARG * coluna);
			e.setPy((_LARG * linha)+9);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			
			e.setIndicacao('F');
			nivel[contadorNivel++] = e;
		}if (nivelSelecionado[linha][coluna] == 'M') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,211));
			
			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha);

			e.setAltura(_LARG-14);
			e.setLargura(_LARG-10);
			
			e.setIndicacao('M');
			nivel[contadorNivel++] = e;
		}if(nivelSelecionado[linha][coluna] == 'X') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(Color.RED);
			
			e.setPx(_LARG * coluna+1);
			e.setPy(_LARG * linha);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			
			e.setIndicacao('X');
			nivel[contadorNivel++] = e;
			
		}if(nivelSelecionado[linha][coluna] == 'Z') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(Color.RED);
			
			e.setPx(_LARG * coluna+1);
			e.setPy((_LARG * linha)+9);
			
			e.setAltura(_LARG);
			e.setLargura(_LARG);
			
			e.setIndicacao('X');
			nivel[contadorNivel++] = e;
			
		}
	}
	
	public void condicao1(char[][] nivelSelecionado,int linha,int coluna) {
		
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
			
		}if (nivelSelecionado[linha][coluna] == 'N') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,211));
			
			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			
			e.setIndicacao('N');
			nivel[contadorNivel++] = e;
			
		}
		if (nivelSelecionado[linha][coluna] == 'G') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,211));
			
			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			
			e.setIndicacao('I');
			nivel[contadorNivel++] = e;
			
		}if (nivelSelecionado[linha][coluna] == 'A') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,211));
			
			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			
			e.setIndicacao('F');
			nivel[contadorNivel++] = e;
			
		} if(nivelSelecionado[linha][coluna] == 'D') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,213));
			
			e.setPx(_LARG * coluna-1);
			e.setPy(_LARG * linha+25-diagonal);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			e.setIndicacao('I');
			nivel[contadorNivel++] = e;
			diagonal++;
			
		}if(nivelSelecionado[linha][coluna] == 'H') {
			
			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,213));
			
			e.setPx(_LARG * coluna-1);
			e.setPy(_LARG * linha+25-diagonal);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			e.setIndicacao('F');
			nivel[contadorNivel++] = e;
			diagonal++;
			
			
		} if (nivelSelecionado[linha][coluna] == 'J') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,213));
			
			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha-20-diagonal);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			e.setIndicacao('I');
			nivel[contadorNivel++] = e;
			diagonal++;
		}if (nivelSelecionado[linha][coluna] == 'K') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			 e.setCor(new Color(148,0,213));
			
			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha-13-diagonal);

			e.setAltura(_LARG);
			e.setLargura(_LARG);
			e.setIndicacao('F');
			nivel[contadorNivel++] = e;
			diagonal++;
		}if (nivelSelecionado[linha][coluna] == 'U') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			e.setCor(new Color(148,0,212));
			
			e.setPx(_LARG * coluna-10);
			e.setPy(_LARG * linha-45+DI);

			e.setAltura(_LARG);	
			e.setLargura(_LARG);
			e.setIndicacao('B');
			
			nivel[contadorNivel++] = e;
			DI++; 
		
		}if (nivelSelecionado[linha][coluna] == 'P') {

			Elemento e = new Elemento();
			e.setAtivo(true);
			e.setCor(new Color(148,0,212));
			
			e.setPx(_LARG * coluna-10);
			e.setPy(_LARG * linha-45+DI);

			e.setAltura(_LARG);	
			e.setLargura(_LARG);
			e.setIndicacao('F');
			
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
			e.setIndicacao('B');
			
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
		}if (nivelSelecionado[linha][coluna] == 'C') {
			
			Elemento e = new Elemento();
			e.setAtivo(true);
			e.setCor(new Color(150,75,0));

			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha);
			
			e.setAltura(_LARG);
			e.setLargura(_LARG);
			e.setIndicacao('C');
			
			//PROX NIVEL
			
			nivel[contadorNivel++] = e;
		} if (nivelSelecionado[linha][coluna] == 'L'){
			
			Elemento e = new Elemento();
			e.setAtivo(true);
			e.setCor(new Color(150,75,0));

			e.setPx(_LARG * coluna);
			e.setPy(_LARG * linha-20);
			
			e.setAltura(_LARG+20);
			e.setLargura(_LARG);
			e.setIndicacao('E');
			
			nivel[contadorNivel++] = e;
		}
	}
	
	@Override
	public void descarregar() { // resetar o jogo :)
		Pontos = 5000;
		
		colidiu = false;
		
		paraBaixo = false;
		
		pressionou = false;
		
		pulou = false;
		
		peso = true;
		
		escada = false;
		
		contadorNaoAtivos = 0;	
		// mario :)
		if(getNIVEL() == 0) {
			mario = new Mario(150,530,_LARG,_LARG);
			mario.setVel(5);
			mario.setAtivo(true);
		}else if(getNIVEL() == 1) {
			mario = new Mario(150,530,_LARG,_LARG);
			mario.setVel(5);
			mario.setAtivo(true);
		}
		
		//DK :)
		dk = new DonkeyKong(25,50,_LARG,_LARG);
		dk.setAtivo(true);
		
		//Cookies :)
		cookie = new Cookies(100,130,_LARG,_LARG);
		cookie.setVel(5);
		cookie.setAtivo(true);
		
		cookies[0] = cookie;
		
		if(getNIVEL() == 0) {
			
			for(int i = 1; i < cookies.length;i++) {
				cookies[i] = new Cookies(100,130,_LARG,_LARG);
			}			
		} else {
			cookies[0].setAtivo(false);
			
			jurandinho[0] = new Jurandinho(100,130,_LARG,_LARG);
			jurandinho[0].setVel(5);
			jurandinho[0].setAtivo(true);
			
			jurandinho[1] = new Jurandinho(100,230,_LARG,_LARG);
			jurandinho[1].setVel(5);
			jurandinho[1].setAtivo(true);
			
			jurandinho[2] = new Jurandinho(100,330,_LARG,_LARG);
			jurandinho[2].setVel(5);
			jurandinho[2].setAtivo(true);
			
			jurandinho[3] = new Jurandinho(100,430,_LARG,_LARG);
			jurandinho[3].setVel(5);
			jurandinho[3].setAtivo(true);
			
			
			blocosChao();
		}
		
		contadorNivel = 0;
	}
	
	public void desativarCookies() {
		for(int i = 0; i < cookies.length;i++) {
			cookies[i].setAtivo(false);
		}
	}
	
	@Override
	public void atualizar() { // Movimentação, colisão e etc
		if(estado != Estado.JOGANDO) {
			return;
		}if(Jogo.controleTecla[3] && !Jogo.controleTecla[4] && !isEscada()) {//direita
			peso = true;
			pressionou = false;
			
			mario.setPx(mario.getPx()+mario.getVel());

		} else if (Jogo.controleTecla[2] && !Jogo.controleTecla[4] && !isEscada()) { //esquerda
			peso = true;
			pressionou = false;
			
			mario.setPx(mario.getPx()-mario.getVel());

		}else if(Jogo.controleTecla[4] && !pressionou && !pulou && !isEscada()){ // pular
			mario.setPy(mario.getPy() - 30);
			
			if(Jogo.controleTecla[4]) {
				pressionou = true;
				if(Jogo.controleTecla[2] || Jogo.controleTecla[3]) {
					int DirEsq = Jogo.controleTecla[2] ? 0-(mario.getVel()+7): mario.getVel()+7;
					mario.setPx(mario.getPx()+DirEsq);				
				}
			}
			pressionou = false;
			pulou = true;
			peso = false;
			
		}else if(Jogo.controleTecla[0] && !pulou){
			peso = false;
		} else if(Jogo.controleTecla[1] && !pulou) {
			peso = false;
		}
		
		acoesMario();
		
		if(getNIVEL() == 0) {
			cookies();
		}else {
			jurandinho();
		}
		
		if(getNIVEL() == 0) {
			VIDA = new Vida(530,50,_LARG,_LARG);			
		}else {
			VIDA = new Vida(550,5,_LARG,_LARG);
		}
		
		pontuacao();
        texto.atualiza();
		mario.atualiza();
		dk.atualiza();
		VIDA.atualiza();
	}
	
	public void acoesMario() {
		int i = 0;
		// peso	mario
		while(i < 10001 && peso && !pressionou && !isEscada()) { // && peso && !pressionou && !escada
			if(i == 1000) {
				mario.setPy(mario.getPy()+3);
				pressionou = false;
			}
			i++;
		}
		
		//peso mario
		while(!peso && pulou && pressionou){
			mario.setPy(mario.getPy()+3);
			peso = true;
			pressionou = false;
		}
		
		//LooP para colisoes mario e açoes
		for (Elemento e : nivel) {
			if (e == null)
				break;
			
			if(Util.colide(mario, e) && (e.getIndicacao() == 'B' || e.getIndicacao() == 'I' || e.getIndicacao() == 'X')){
				if(e.getIndicacao() != 'X') {
					colidiuRecente = false;					
				}
					
				pulou = false;
				setColidiu(true);
				peso = true;
				paraBaixo = false;
				setEscada(false);
				subindo = false;
				mario.setPy(e.getPy() -  e.getLargura());
				
			} if (Util.colide(mario, e) && e.getIndicacao() == 'F'){
				if(Util.diferencaPosY(mario, e) == -1 && !paraBaixo) {
					setColidiu(true);
					peso = true;
					pulou = false;
					setEscada(false);
					mario.setPy(e.getPy() -  e.getLargura());
				
				}if(Util.diferencaPosY(mario, e) == -1 && Jogo.controleTecla[1]){
					paraBaixo = true;
					setColidiu(false);
					peso = false;
					pulou = false;
					setEscada(true);
					mario.setPy(mario.getPy() + mario.getVel()/2);
				}
				else {
					paraBaixo = false;
					setColidiu(false);	
					peso = true;
				}	
			}if(Jogo.controleTecla[0] && Util.colide(mario, e) && (e.getIndicacao() == 'E' || e.getIndicacao() == 'F' || e.getIndicacao() == 'M') && !pulou){
				if(e.getIndicacao() == 'M') {
					mario.setPy(mario.getPy() - (mario.getVel()-1));
				}else {
					mario.setPy(mario.getPy() - mario.getVel()/2);
					
				}
				if(e.getIndicacao() == 'F') {
					colidiuRecente = false;					
				}
				peso = false;
				setColidiu(true);
				setEscada(true);
				subindo = true;
				
			}if(Jogo.controleTecla[1] && Util.colide(mario, e) && (e.getIndicacao() == 'E' || e.getIndicacao() == 'F' || e.getIndicacao() == 'M')  && !pulou && !pressionou){
				setSubindo(true);
				peso = false;
				setColidiu(true);
				setEscada(true);
				if(e.getIndicacao() == 'M') {
					mario.setPy(mario.getPy() + (mario.getVel()-1));
				}else {
					mario.setPy(mario.getPy() + mario.getVel()/2);					
				}
				
				if(Util.colide(mario, e) && e.getIndicacao() == 'B'){
					setEscada(false);
					subindo = false;
				}
				
			}if(Util.colide(mario, e) && e.getIndicacao() == 'C'){
				setPontosSomatoria(getPontosSomatoria() + Pontos);
				
				if(getNIVEL() == 0) {
					setNIVEL(getNIVEL() + 1);					
				}else {
					setNIVEL(getNIVEL() - 1);
					//TODO AUMENTAR A VELOCIDADE DO CENARIO :)
				}
				
				descarregar();
				carregar();
				
			}else{
				peso = true;
				setColidiu(false);
			}
		}
	}
	
	public void cookies() {
		//peso cookie
		for(int j = 0; j < 100001 && pesoCookie;j++) {
			if(j == 10000) {
				for(Cookies c:cookies) {
					if(c.isAtivo()) {
						c.setPy(c.getPy()+5);															
					}
				}
			}
		}
				
		// colisoes cookie
		for (Elemento e : nivel) {
			if(e == null)
				break;
			
			for(Cookies c: cookies){
				if(c.isAtivo()) {
					if(foraDaTela(c) && c.isAndar() && !c.isQueda()){
						c.setAndar(false);
						c.setQueda(true);
						
						if(c.getPx() + c.getLargura() >= 520){
							c.setSinal(false);							
						} else {
							c.setSinal(true);
						}
						
					}if(Util.colide(c, e) && (e.getIndicacao() == 'B' || e.getIndicacao() == 'I') ){
						c.setPy(e.getPy() -  e.getLargura());
						
						if(c.isQueda() && !bateuBorda) {
							bateuBorda = true;
						}else {
							bateuBorda = false;
						}
						
					}if(Util.colide(c, e) && (e.getIndicacao() == 'F')){
						c.setPy(e.getPy() -  c.getLargura());
						
						if(c.isQueda() && !bateuBorda) {
							bateuBorda = true;
						}else {
							bateuBorda = false;
						}
						
					}if(Util.colide(c, e) && (e.getIndicacao() == 'N')){
						c.setAtivo(false);
						c.setPx(100);
						c.setPy(130);
						c.setAtivo(true);
						c.setSinal(true);
						
					}if(Util.colide(c, mario)){
						Vida.setVIDA(Vida.getVIDA()-1);
						
						descarregar();
						
					}else {
						if(bateuBorda) {
							c.setAndar(true);
							c.setQueda(false);
						}
						pesoCookie = true;
					}
					
				}
			}
		}
		
			
		//animação e atualização dos cookies
		int k = 1;
		for(Cookies c: cookies){
			if(c.isAtivo()) {
				if(c.getPy() > 180 && k < 4){
					cookies[k].setAtivo(true);	
					k++;
				}
			}
			
			c.atualiza();
		}
		
	}
	
	public void pontuacao() {
		long tempoAtual = ((System.currentTimeMillis() - tempoInicial)/1000);
		
		if(tempoAtual == 3) {
			Pontos-=100;
			tempoAtual = 0;
			tempoInicial = System.currentTimeMillis();
		}
		
		
	}
	
	public void jurandinho() {
		
		for (Elemento e : nivel) {
			if(e == null)
				break;
			for(Jurandinho j: jurandinho) {
				if(foraDaTela2(j) &&  j.getPx() + j.getLargura() > 585) {
					j.setSinal(false);
				}else if(foraDaTela2(j) && j.getPx() < 100 ) {
					j.setSinal(true);
				}
				
				if(Util.colide(mario, j)) {
					Vida.setVIDA(Vida.getVIDA()-1);
					
					descarregar();
				}
			}
		}
		
		for(Jurandinho j: jurandinho) {
			if(j.isAtivo()) {
				j.atualiza();				
			}
		}
	}
	@Override
	public void desenhar(Graphics2D g) {
		
		
		if(getNIVEL() == 0){
			g.drawImage(cenario.getImage(),100, 50, 1000,900, 0, 0, 400,274, null);// imagem do cenario
			
			for(Cookies c: cookies){
				if(c == null)
					break;
				
				if(c.isAtivo()){
					c.desenha(g);				
				}
			}
		} else{
			
			g.drawImage(cenario.getImage(),100, 50, 1200,1000, 0, 0, 400,274, null);// imagem do cenario
			for(ChaoFase2 c: chao){
				if(c == null)
					break;
				
				if(c.isAtivo() && Util.colideChao(mario, c)) {
					c.setAtivo(false);
					colidiuRecente = true;	
					
				}else if(!c.isAtivo() && Util.colideChao(mario, c) && !colidiuRecente){
					Vida.setVIDA(Vida.getVIDA()-1);
					
					descarregar();
					
				} if(c.isAtivo()) {
					c.desenha(g);					
				}
			}
			
			for(Jurandinho j: jurandinho){
				if(j == null)
					break;
				
				j.desenha(g);
			}
			
			for(ChaoFase2 c: chao){
				if(!c.isAtivo() && !c.isRepetiu()) {
					contadorNaoAtivos+=1;	
					c.setRepetiu(true);
					
				}if(contadorNaoAtivos == 8){
					setPontosSomatoria(getPontosSomatoria() + Pontos);
						setNIVEL(getNIVEL() - 1);
						descarregar();
						carregar();
				}
			}
			
		}

		
//		for(Elemento e: nivel) {
//			if(e == null) 
//				break;
//
//				e.desenha(g);
//		}
		
//		g.drawRect(90, 0, 470, 550);
		
		texto.setCor(new Color(148,0,211));
		texto.desenha(g, ""+Pontos , 330,40);
		
		VIDA.desenha(g);
		mario.desenha(g);
        dk.desenha(g);
	}
	
	private boolean foraDaTela(Elemento el) {
		if (el.getPx() < 100 || el.getPx() + el.getLargura() > 520)
			return true;

		return false;
	}
	
	private boolean foraDaTela2(Elemento el) {
		if (el.getPx() < 100 || el.getPx() + el.getLargura() > 520)
			return true;

		return false;
	}
	
	public static boolean isSaiuColisao() {
		return saiuColisao;
	}

	public void setSaiuColisao(boolean saiuColisao) {
		this.saiuColisao = saiuColisao;
	}

	public static int getVIDA() {
		return VIDA.getVIDA();
	}

	public static void setVIDA(int Vida) {
		VIDA.setVIDA(Vida);
	}

	public static boolean isEscada() {
		return escada;
	}

	public void setEscada(boolean escada) {
		this.escada = escada;
	}
	public static boolean isSubindo() {
		return subindo;
	}

	public void setSubindo(boolean subindo) {
		this.subindo = subindo;
	}

	public static boolean isColidiu() {
		return colidiu;
	}

	public void setColidiu(boolean colidiu) {
		this.colidiu = colidiu;
	}

	public static int getNIVEL() {
		return NIVEL;
	}

	public static void setNIVEL(int nIVEL) {
		NIVEL = nIVEL;
	}

	public static int getPontosSomatoria() {
		return pontosSomatoria;
	}

	public static void setPontosSomatoria(int pontosSomatoria) {
		JogoCenario.pontosSomatoria = pontosSomatoria;
	}
}