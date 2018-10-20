package br.uff.ic.algoritmos.arvore;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PainelArvore extends JPanel {
	private No no;

	private static final long serialVersionUID = 1L;

	public PainelArvore(No no) {
		this.no = no;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setFont(new Font("Tahoma", Font.BOLD, 20));
		drawTree(g, 0, getWidth(), 0, (getHeight() / 2) / no.getAltura(no), no);
		super.setAutoscrolls(true);
	}

	public void drawTree(Graphics g, int StartWidth, int EndWidth, int StartHeight, int Level, No no) {
		String data = String.valueOf(no.getValor());
		g.setFont(new Font("Times", Font.BOLD, 12));
		FontMetrics fm = g.getFontMetrics();
		int dataWidth = fm.stringWidth(data);

		g.drawString(data, (StartWidth + EndWidth) / 2 - dataWidth / 2, StartHeight + Level / 2);

		if (no.getEsquerda() != null) {
			drawTree(g, StartWidth, (StartWidth + EndWidth) / 2, StartHeight + Level, Level, no.getEsquerda());
		}
		if (no.getDireita() != null) {
			drawTree(g, (StartWidth + EndWidth) / 2, EndWidth, StartHeight + Level, Level, no.getDireita());
		}
	}
}