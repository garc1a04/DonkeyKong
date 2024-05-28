package br.com.garc1a.DonkeyKong.ClassesPrincipais;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.garc1a.DonkeyKong.ClassesBase.CenarioPadrao;
import br.com.garc1a.DonkeyKong.ClassesBase.Texto;

public class Jogo extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private static final int FPS = 1000 / 20;

	private static final int JANELA_ALTURA = 600;

	private static final int JANELA_LARGURA = 720;

	private JPanel tela;

	private Graphics2D g2d;

	private BufferedImage buffer;

	private CenarioPadrao cenario;
	
	private int contador;
	

	public static final Texto textoPausa = new Texto(new Font("Tahoma", Font.PLAIN, 40));

	public static boolean[] controleTecla = new boolean[5];

	public static void liberaTeclas() {
		for (int i = 0; i < controleTecla.length; i++) {
			controleTecla[i] = false;
		}
	}

	private void setaTecla(int tecla, boolean pressionada) {
		switch (tecla) {
		case KeyEvent.VK_UP:
			controleTecla[0] = pressionada;
			break;
		case KeyEvent.VK_DOWN:
			controleTecla[1] = pressionada;
			break;
		case KeyEvent.VK_LEFT:
			controleTecla[2] = pressionada;
			break;
		case KeyEvent.VK_RIGHT:
			controleTecla[3] = pressionada;
			break;
		case KeyEvent.VK_SPACE:
			controleTecla[4] = pressionada;
			break;
		}
	}
	
	public static int nivel;

	public static int velocidade;

	public static boolean pausado;

	public Jogo() {
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setaTecla(e.getKeyCode(), false);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				setaTecla(e.getKeyCode(), true);
			}
		});

		buffer = new BufferedImage(JANELA_LARGURA, JANELA_ALTURA, BufferedImage.TYPE_INT_RGB);

		g2d = buffer.createGraphics();

		tela = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(buffer, 0, 0, null);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(JANELA_LARGURA, JANELA_ALTURA);
			}

			@Override
			public Dimension getMinimumSize() {
				return getPreferredSize();
			}
		};

		getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();

		setVisible(true);
		tela.repaint();
	}
	
	public void carregarJogo() {
		cenario = new JogoCenario(JANELA_LARGURA, JANELA_ALTURA);
		cenario.carregar();
	}
	
	public void iniciarJogo() {
		long prxAtualizacao = 0;
		
		while (true) {
			if (System.currentTimeMillis() >= prxAtualizacao) {
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, JANELA_LARGURA, JANELA_ALTURA);
				
				if(cenario == null) {
						g2d.setColor(Color.WHITE);
						g2d.drawString("Carregando...", 20, 20);
				} else {
					cenario.desenhar(g2d);
					cenario.atualizar();
				}
				
				tela.repaint();
				prxAtualizacao = System.currentTimeMillis() + FPS;
			}
		}
	}
}
