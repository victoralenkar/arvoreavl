package br.uff.ic.algoritmos.arvore;

public class ArvoreAVL {

	private No raiz;
	private int comparacoes;
	private boolean debug;

	public ArvoreAVL() {
		super();
		this.raiz = null;
		this.debug = false;
		this.comparacoes = 0;
	}

	public void resetComparacoes() {
		this.comparacoes = 0;
	}

	public boolean buscar(No atual, int valor) {
		if (atual == null) {
			return false;
		}
		this.comparacoes++;
		if (atual.getValor().intValue() == valor) {
			return true;
		} else {
			if (valor < atual.getValor().intValue()) {
				return buscar(atual.getEsquerda(), valor);
			} else {
				return buscar(atual.getDireita(), valor);
			}
		}
	}

	public boolean inserir(Integer valor) {
		boolean encontrado = buscar(raiz, valor);
		if (!encontrado) {
			this.resetComparacoes();
			this.raiz = inserir(this.raiz, valor);
		}
		return !encontrado;
	}

	public Integer remover(Integer valor) {
		if (!buscar(raiz, valor)) {
			return null;
		}
		this.resetComparacoes();
		this.raiz = this.remover(raiz, valor);
		return valor;
	}

	private int altura(No no) {
		if (no == null)
			return 0;
		return no.getAltura();
	}

	private int max(int a, int b) {
		return (a > b) ? a : b;
	}

	// Obtem o fator de balanço de um nó
	private int getBalanco(No no) {
		if (no == null)
			return 0;
		return altura(no.getEsquerda()) - altura(no.getDireita());
	}
	
	private No rotacionarDireita(No y,boolean debugLocal) {
		if (debugLocal && this.debug) {
			System.out.println("Rotação Direita em nó: "+y.getValor());
			System.out.println("T1, T2 e T3 são subárvores.");
			System.out.println("                y                                x");
			System.out.println("               / \\     Rotação Dir. (y)        /  \\");
			System.out.println("              x   T3    – - – - – - - –>       T1   y");
			System.out.println("             / \\                                  / \\");
			System.out.println("            T1  T2                                T2  T3");
		}
		No x = y.getEsquerda();
		No t2 = x.getDireita();

		// Execução das trocas
		x.setDireita(y);
		y.setEsquerda(t2);

		// Atualização as alturas
		y.setAltura(max(altura(y.getEsquerda()), altura(y.getDireita())) + 1);
		x.setAltura(max(altura(x.getEsquerda()), altura(x.getDireita())) + 1);

		return x;
	}

	private No rotacionarEsquerda(No y, boolean debugLocal) {
		if (debugLocal && this.debug) {
			System.out.println("T1, T2 e T3 são subárvores.");
			System.out.println("Rotação Esquerda em nó: "+y.getValor());
			System.out.println("               y                                 x");
			System.out.println("              / \\      Rotação Esq. (y)         / \\");           
			System.out.println("             T1  x      – - – - – - - –>       y   T3");         
			System.out.println("                / \\                           / \\");                        
			System.out.println("               T2  T3                        T1  T2");
		}
		No x = y.getDireita();
		No t2 = x.getEsquerda();

		// Execução das trocas
		x.setEsquerda(y);
		y.setDireita(t2);

		// Atualização as alturas
		y.setAltura(max(altura(y.getEsquerda()), altura(y.getDireita())) + 1);
		x.setAltura(max(altura(x.getEsquerda()), altura(x.getDireita())) + 1);

		return x;
	}

	private No inserir(No no, int valor) {
		/* 1. Percorre a árvore até o ponto de inserção e insere */
		if (no == null)
			return (new No(valor));

		if (valor < no.getValor()) {
			no.setEsquerda(inserir(no.getEsquerda(), valor));
			this.comparacoes++;
		} else if (valor > no.getValor()) {
			no.setDireita(inserir(no.getDireita(), valor));
			this.comparacoes++;
		} else // Caso o valor já exista, retorne ele.
			return no;

		/* 2. Após inserção, atualiza a altura desse nó ancestral */
		no.setAltura(1 + max(altura(no.getEsquerda()), altura(no.getDireita())));

		/*
		 * 3. Obtem o balanço desse nó ancestral para verificar se houve algum
		 * desbalanceamento
		 */
		int balanco = getBalanco(no);

		// Verificando desbalanceamentos...
		// Caso 1:
		if (balanco > 1 && valor < no.getEsquerda().getValor()) {
			if (this.debug) {
				System.out.println("Rotação Direita em nó: "+no.getValor());
				//System.out.println("T1, T2, T3 e T4 são subárvores.");
				//System.out.println("                   z                                       y");
				//System.out.println("                  / \\                                   /   \\");
				//System.out.println("                 y   T4      Rotação Dir. (z)           x     z");
				//System.out.println("                / \\         – - – - – - - –>         /  \\  / \\");
				//System.out.println("               x   T3                                T1  T2 T3  T4");
				//System.out.println("              / \\");
				//System.out.println("            T1   T2");
			}
			return rotacionarDireita(no,false);
		}

		// Caso 2:
		if (balanco > 1 && valor > no.getEsquerda().getValor()) {
			if (this.debug) {
				System.out.println("Rotação Dupla Direita iniciando em nó: "+no.getValor());
				//System.out.println("T1, T2, T3 e T4 são subárvores.");
				//System.out.println("                 z                                z                           x");
				//System.out.println("                / \\                            /   \\                       /  \\"); 
				//System.out.println("               y   T4   Rotação Esq. (y)       x    T4  Rotação Dir. (z)    y     z");
				//System.out.println("              / \\      – - – - – - - –>      /  \\     – - – - – - - –>   / \\   / \\");
				//System.out.println("            T1   x                           y    T3                      T1  T2 T3  T4");
				//System.out.println("                / \\                        / \\");
				//System.out.println("              T2   T3                     T1   T2");
			}
			no.setEsquerda(rotacionarEsquerda(no.getEsquerda(),false));
			return rotacionarDireita(no,false);
		}
		
		// Caso 3:
		if (balanco < -1 && valor > no.getDireita().getValor()) {
			if (this.debug) {
				System.out.println("Rotação Esquerda em nó: "+no.getValor()+"...");
				//System.out.println("T1, T2, T3 e T4 são subárvores.");
				//System.out.println("               z                               y");
				//System.out.println("             /  \\                            /   \\");
				//System.out.println("            T1   y      Rotação Esq. (z)    z     x");
				//System.out.println("                /  \\   - - - - - - - ->    / \\   / \\");
				//System.out.println("               T2   x                     T1 T2 T3  T4");
				//System.out.println("                   / \\");
				//System.out.println("                  T3  T4");
			}
			return rotacionarEsquerda(no,false);			
		}


		// Caso 4:
		if (balanco < -1 && valor < no.getDireita().getValor()) {
			if (this.debug) {
				System.out.println("Rotação Dupla Esq iniciando em nó: "+no.getValor()+"...");
				//System.out.println("T1, T2, T3 e T4 são subárvores.");
				//System.out.println("               z                             z                             x");
				//System.out.println("              / \\                          / \\                          /   \\");
				//System.out.println("            T1   y    Rotação Dir. (y)    T1   x    Rotação Esq. (z)     z     y");
				//System.out.println("                / \\  - - - - - - - - ->     /  \\  - - - - - - - - ->  / \\   / \\");
				//System.out.println("               x   T4                      T2   y                    T1 T2 T3  T4");
				//System.out.println("              / \\                              /  \\");
				//System.out.println("            T2   T3                           T3   T4");
			}
			no.setDireita(rotacionarDireita(no.getDireita(),false));
			return rotacionarEsquerda(no,false);
		}

		return no;
	}

	// Dada uma subárvore, obtem o menor elemento da subárvore 
	private No getNoMenorValor(No no) {
		No atual = no;
		while (atual.getEsquerda() != null) {
			atual = atual.getEsquerda();
		}
		return atual;
	}

	private No remover(No no, int key) {
		// Condição de parada da remoção
		if (no == null)
			return no;

		// 1. Percurso na BST para remoção...
		if (key < no.getValor()) {
			this.comparacoes++;
			no.setEsquerda(remover(no.getEsquerda(), key));
		} else if (key > no.getValor()) {
			this.comparacoes++;
			no.setDireita(remover(no.getDireita(), key));
		} else {
			// Nó encontrado para remoção
			this.comparacoes++;
			// Verificando se nó é folha ou possui filhos
			// Caso 1: Possui algum filho ou nenhum
			if ((no.getEsquerda() == null) || (no.getDireita() == null)) {
				No temp = null;
				if (temp == no.getEsquerda())
					temp = no.getDireita();
				else
					temp = no.getEsquerda();

				//Caso 1.1: Não possui filho e remove
				if (temp == null) {
					temp = no;
					no = null;
				} else {// 1.2: Possui filho
					no = temp; // Filho substitui pai
				}
			} else {
				// Caso 2: Possui os dois filhos
				// Obtem o menor elemento da subárvore à direita do nó a ser removido,
				// pois ele o substituirá
				No temp = getNoMenorValor(no.getDireita());

				// Seta o novo nó da posição removida 
				no.setValor(temp.getValor());

				// Remoção do menor elemento da subárvore à direita, após transferência
				// do mesmo para substituir nó removido.
				no.setDireita(remover(no.getDireita(), temp.getValor()));
			}
		}

		// Caso em que a árvore possuia apenas um nó após a remoção
		if (no == null)
			return no;

		// 2. Após remoção, atualizar a altura do nó atual
		no.setAltura(max(altura(no.getEsquerda()), altura(no.getDireita())) + 1);

		// 3. Obter o balanço do nó atual para verificar desbalanceamentos
		int balanco = getBalanco(no);

		// Verificando desbalanceamentos...
		// Caso 1:
		if (balanco > 1 && getBalanco(no.getEsquerda()) >= 0) {
			if (this.debug) {
				System.out.println("Rotação Direita em nó: "+no.getValor());
				//System.out.println("T1, T2, T3 e T4 são subárvores.");
				//System.out.println("                   z                                       y");
				//System.out.println("                  / \\                                   /   \\");
				//System.out.println("                 y   T4      Rotação Dir. (z)           x     z");
				//System.out.println("                / \\         – - – - – - - –>         /  \\  / \\");
				//System.out.println("               x   T3                                T1  T2 T3  T4");
				//System.out.println("              / \\");
				//System.out.println("            T1   T2");
			}
			return rotacionarDireita(no,false);
		}
		
		// Caso 2:
		if (balanco > 1 && getBalanco(no.getEsquerda()) < 0) {
				if (this.debug) {
					System.out.println("Rotação Dupla Dir iniciando em nó: "+no.getValor());
					//System.out.println("T1, T2, T3 e T4 são subárvores.");
					//System.out.println("                 z                                z                           x");
					//System.out.println("                / \\                            /   \\                       /  \\"); 
					//System.out.println("               y   T4   Rotação Esq. (y)       x    T4  Rotação Dir. (z)    y     z");
					//System.out.println("              / \\      – - – - – - - –>      /  \\     – - – - – - - –>   / \\   / \\");
					//System.out.println("            T1   x                           y    T3                      T1  T2 T3  T4");
					//System.out.println("                / \\                        / \\");
					//System.out.println("              T2   T3                     T1   T2");
				}
			no.setEsquerda(rotacionarEsquerda(no.getEsquerda(),false));
			return rotacionarDireita(no,false);
		}

		// Caso 3:
		if (balanco < -1 && getBalanco(no.getDireita()) <= 0) {
			if (this.debug) {
				if (this.debug) {
					System.out.println("Rotação Esquerda em nó: "+no.getValor()+"...");
					//System.out.println("T1, T2, T3 e T4 são subárvores.");
					//System.out.println("               z                                y");
					//System.out.println("             /  \\                            /   \\");
					//System.out.println("            T1   y      Rotação Esq. (z)    z     x");
					//System.out.println("                /  \\   - - - - - - - ->    / \\   / \\");
					//System.out.println("               T2   x                      T1  T2 T3  T4");
					//System.out.println("                   / \\");
					//System.out.println("                  T3  T4");
				}
			}
			return rotacionarEsquerda(no,false);
		}

		// Caso 4:
		if (balanco < -1 && getBalanco(no.getDireita()) > 0) {
			if (this.debug) {
				System.out.println("Rotação Dupla iniciando em nó: "+no.getValor()+"...");
				//System.out.println("T1, T2, T3 e T4 são subárvores.");
				//System.out.println("               z                             z                             x");
				//System.out.println("              / \\                          / \\                          /   \\");
				//System.out.println("            T1   y    Rotação Dir. (y)    T1   x    Rotação Esq. (z)     z     y");
				//System.out.println("                / \\  - - - - - - - - ->     /  \\  - - - - - - - - ->  / \\   / \\");
				//System.out.println("               x   T4                      T2   y                    T1 T2 T3  T4");
				//System.out.println("              / \\                              /  \\");
				//System.out.println("            T2   T3                           T3   T4");
			}
			no.setDireita(rotacionarDireita(no.getDireita(),false));
			return rotacionarEsquerda(no,false);
		}

		return no;
	}

	public No getRaiz() {
		return raiz;
	}

	public void setRaiz(No raiz) {
		this.raiz = raiz;
	}

	public int getComparacoes() {
		return comparacoes;
	}

	public void setComparacoes(int comparacoes) {
		this.comparacoes = comparacoes;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

}
