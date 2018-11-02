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
				if (pai.getDireita() == null) {
					data += "(esq)";
					dataWidth += 25;
				}
				noFilho = new TextInBox(data, dataWidth, 20);
				tree.addChild(paiBox, noFilho);
				createTree(tree, noFilho, filhoEsquerda);
			}

			No filhoDireita = pai.getDireita();
			if (filhoDireita != null) {
				String data = String.valueOf(filhoDireita.getValor());
				int dataWidth = 15 * data.length();
				if (pai.getEsquerda() == null) {
					data += "(dir)";
					dataWidth += 25;
				}
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

			System.out.println();
			System.out.print("Escolha uma opção (B)uscar, (I)nserir, (R)emover, (S)air: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String leitura = br.readLine();

			if (leitura.equalsIgnoreCase("b")) {
				System.out.print("Digite o valor a ser buscado: ");
				br = new BufferedReader(new InputStreamReader(System.in));
				leitura = br.readLine();
				arvore.resetComparacoes();
				try {
					if (arvore.buscar(arvore.getRaiz(), Integer.parseInt(leitura))) {
						System.out.println("Valor encontrado!");
						System.out.println("Quantidade de comparações: " + arvore.getComparacoes());
					} else {
						System.out.println("Valor não encontrado!");
						System.out.println("Quantidade de comparações: " + arvore.getComparacoes());
					}
				} catch (NumberFormatException e) {
					System.out.println("O valor digitado não é número! Tente novamente!");
					System.out.println();
				}
			} else if (leitura.equalsIgnoreCase("i")) {

				System.out.print("Digite o valor a ser inserido: ");
				br = new BufferedReader(new InputStreamReader(System.in));
				leitura = br.readLine();
				System.out.println();
				arvore.resetComparacoes();
				try {
					if (arvore.inserir(Integer.parseInt(leitura))) {
						System.out.println("Valor inserido com sucesso!");
						System.out.println();
						System.out.println("Quantidade de comparações: " + arvore.getComparacoes());
					} else {
						System.out.println("Valor já existente na árvore!");
						System.out.println("Quantidade de comparações: " + arvore.getComparacoes());
					}
				} catch (NumberFormatException e) {
					System.out.println("O valor digitado não é número! Tente novamente!");
					System.out.println();
				}
			} else if (leitura.equalsIgnoreCase("r")) {
				System.out.print("Digite o valor a ser removido: ");
				br = new BufferedReader(new InputStreamReader(System.in));
				leitura = br.readLine();
				System.out.println();
				arvore.resetComparacoes();
				try {
					if (arvore.remover(Integer.parseInt(leitura)) != null) {
						System.out.println("Valor removido com sucesso!");
						System.out.println();
						System.out.println("Quantidade de comparações: " + arvore.getComparacoes());
					} else {
						System.out.println("Valor não encontrado para remoção!");
						System.out.println("Quantidade de comparações: " + arvore.getComparacoes());
					}
				} catch (NumberFormatException e) {
					System.out.println("O valor digitado não é número! Tente novamente!");
					System.out.println();
				}
			} else if (leitura.equalsIgnoreCase("s")) {
				br.close();
				System.exit(0);
			} else {
				System.out.println();
				System.out.println("Opção " + leitura + " inválida, tente novamente!");
			}
			if (arvore != null && arvore.getRaiz() != null) {
				showInDialog(dialog, getPanel(arvore));
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
