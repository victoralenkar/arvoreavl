package br.uff.ic.algoritmos.arvore;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import br.uff.ic.algoritmos.arvore.gui.TextInBox;
import br.uff.ic.algoritmos.arvore.gui.TextInBoxNodeExtentProvider;
import br.uff.ic.algoritmos.arvore.gui.TextInBoxTreePane;

public class ArvoreGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	public ArvoreAVL arvore;

	public ArvoreGUI(ArvoreAVL arvore) {
		this.arvore = arvore;
	}

	private static void showInDialog(JDialog dialog, JComponent panel) {
		Container contentPane = dialog.getContentPane();
		((JComponent) contentPane).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		contentPane.removeAll();
		contentPane.add(panel);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	public static TreeForTreeLayout<TextInBox> createTree(DefaultTreeForTreeLayout<TextInBox> tree, TextInBox paiBox,
			No pai) {
		if (tree == null) {
			String data = String.valueOf(pai.getValor());
			int dataWidth = 15 * data.length();
			paiBox = new TextInBox(data, dataWidth, 20);
			tree = new DefaultTreeForTreeLayout<TextInBox>(paiBox);
		}

		if (pai != null) {
			No filhoEsquerda = pai.getEsquerda();
			TextInBox noFilho = null;
			if (filhoEsquerda != null) {
				String data = String.valueOf(filhoEsquerda.getValor());
				int dataWidth = 15 * data.length();
				noFilho = new TextInBox(data, dataWidth, 20);
				tree.addChild(paiBox, noFilho);
				createTree(tree, noFilho, filhoEsquerda);
			}

			No filhoDireita = pai.getDireita();
			if (filhoDireita != null) {
				String data = String.valueOf(filhoDireita.getValor());
				int dataWidth = 15 * data.length();
				noFilho = new TextInBox(data, dataWidth, 20);
				tree.addChild(paiBox, noFilho);
				createTree(tree, noFilho, filhoDireita);
			}

		}
		return tree;
	}

	public static void main(String[] args) throws IOException {
		JDialog dialog = new JDialog();
		ArvoreAVL arvore = new ArvoreAVL();
		// 25,75,30,20,12,23,90,73,72,31,29,6,45,110,122,130,140,3,109,119,118,121,123
		while (true) {
			System.out.println("Digite \"-\" quando quiser sair...");
			System.out.print("Inserir um valor: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String leitura = br.readLine();
			if (leitura != null && !leitura.equals("-")) {
				arvore.inserir(Integer.parseInt(leitura));
				System.out.print("Valor inserido com sucesso!");
				System.out.println();
				showInDialog(dialog, getPanel(arvore));
			} else {
				br.close();
				System.exit(0);
			}
		}

	}

	public static TextInBoxTreePane getPanel(ArvoreAVL arvore) {
		TreeForTreeLayout<TextInBox> tree = createTree(null, null, arvore.getRaiz());

		// setup the tree layout configuration
		double gapBetweenLevels = 50;
		double gapBetweenNodes = 10;
		DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(gapBetweenLevels,
				gapBetweenNodes);

		// create the NodeExtentProvider for TextInBox nodes
		TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

		// create the layout
		TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree, nodeExtentProvider, configuration);

		// Create a panel that draws the nodes and edges and show the panel
		TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
		return panel;
	}

}
