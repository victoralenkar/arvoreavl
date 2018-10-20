package br.uff.ic.algoritmos.arvore;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ArvoreGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public No node;
	public PainelArvore drawer;

	public ArvoreGUI(No node) {
		this.node = node;
	}

	public void desenhar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 200 * node.getTotalNos() / 2, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		drawer = new PainelArvore(node);
		contentPane.add(drawer);
		setContentPane(contentPane);
		setVisible(true);
	}

	public static void main(String[] args) {
		No raiz = new No(35);
		raiz.inserir(25);
		raiz.inserir(75);
		raiz.inserir(30);
		raiz.inserir(20);
		raiz.inserir(12);
		raiz.inserir(23);
		raiz.inserir(90);
		raiz.inserir(73);
		raiz.inserir(72);
		raiz.inserir(31);
		raiz.inserir(29);
		/*raiz.inserir(6);
		raiz.inserir(45);
		raiz.inserir(110);
		raiz.inserir(122);
		raiz.inserir(130);
		raiz.inserir(140);
		raiz.inserir(3);
		raiz.inserir(109);
		raiz.inserir(119);
		raiz.inserir(118);
		raiz.inserir(121);
		raiz.inserir(123);
*/
		ArvoreGUI gui = new ArvoreGUI(raiz);
		gui.desenhar();
	}

}
