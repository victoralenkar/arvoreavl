package br.uff.ic.algoritmos.arvore;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

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
		panel.setAutoscrolls(true);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.add(panel);
		jScrollPane.setViewportView(panel);
		contentPane.removeAll();
		contentPane.add(jScrollPane);
		dialog.pack();
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

	public static Integer[] gerarAleatorios(int tamanho) {
		Integer[] arr = new Integer[tamanho];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		Collections.shuffle(Arrays.asList(arr));
		return arr;
	}

	public static void main(String[] args) throws IOException {
		JDialog dialog = new JDialog();		
		dialog.setTitle("Árvore Atual");
		JDialog anterior = new JDialog();
		anterior.setTitle("Árvore Anterior");
		ArvoreAVL arvore = new ArvoreAVL();

		/*System.out.println(
				"Carregamento inicial da árvore. Com quantos números aleatórios deseja carregar inicialmente a árvore? ");*/
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		FileReader fileReader = new FileReader(args[0]);
		BufferedReader br = new BufferedReader(fileReader);
		
		String leitura = br.readLine();
		leitura.replace(" ", "");
		String[] numeros = leitura.split(",");
		Integer[] randoms = new Integer[numeros.length];
		for (int i = 0; i < numeros.length; i++) {
			randoms[i]= Integer.valueOf(numeros[i]);
		}
		try {
			//Integer tamanho = Integer.parseInt(leitura);
			//randoms = gerarAleatorios(tamanho);
			System.out.println("Valores aleatórios inseridos na seguinte ordem:");
			System.out.println();
			for (int i = 0; i < randoms.length; i++) {
				arvore.inserir(randoms[i].intValue());
				if (i == randoms.length - 1) {
					System.out.print(randoms[i].intValue());
				} else {
					System.out.print(randoms[i].intValue() + ", ");
				}
			}
			if (arvore != null && arvore.getRaiz() != null) {
				showInDialog(dialog, getPanel(arvore));
			}
		} catch (NumberFormatException e1) {
			System.out.println("Valor digitado não é um número!");
			System.exit(1);
		}
		System.out.println();
		arvore.setDebug(true);
		while (true) {			

			System.out.println();
			System.out.print("Escolha uma opção (B)uscar, (I)nserir, (R)emover, (S)air: ");
			br = new BufferedReader(new InputStreamReader(System.in));
			leitura = br.readLine();

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
				if (dialog != null) {
					showInDialog(anterior, getPanel(arvore));
				}
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
				if (dialog != null) {
					showInDialog(anterior, getPanel(arvore));
				}
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
				anterior.setLocation(dialog.getX(), dialog.getY()+dialog.getHeight());				
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
		panel.setAutoscrolls(true);
		return panel;
	}

}
