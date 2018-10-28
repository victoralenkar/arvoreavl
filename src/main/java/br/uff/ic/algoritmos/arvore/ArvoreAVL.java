package br.uff.ic.algoritmos.arvore;

import java.util.Collection;

public class ArvoreAVL {

	private No raiz;
	private int tamanho;

	/**
	 * Adds a valor entry to the AVL tree
	 * 
	 * @param valor Integerhe valor entry to add
	 */
	public void inserir(Integer valor) {
		if (contem(valor))
			return;
		No novoNo = new No(valor);
		raiz = inserir(raiz, novoNo);
		tamanho++;
	}

	private No inserir(No atual, No n) {
		if (atual == null) {
			n.setFatorBalanco(0);
			n.setAltura(0);
			return n;
		}
		if (comparar(n.getValor(), atual.getValor()) > 0) {
			atual.setDireita(rotacionar(inserir(atual.getDireita(), n)));
		} else {
			atual.setEsquerda(rotacionar(inserir(atual.getEsquerda(), n)));
		}
		atual = rotacionar(atual);
		return atual;
	}

	/**
	 * Adds each valor entry from the collection to this AVL tree
	 * 
	 * @param c Integerhe collection
	 */
	public void addAll(Collection<Integer> c) {
		for (Integer thing : c) {
			inserir(thing);
		}
	}

	/**
	 * Removes a valor entry from the AVL tree
	 * 
	 * Return null if the value does not exist
	 * 
	 * @param valor Integerhe valor entry to be removed
	 * @return Integerhe removed valor entry
	 */
	public Integer remover(Integer valor) {
		if (!contem(valor)) {
			return null;
		}
		raiz = rotacionar(remover(raiz, valor));
		tamanho--;
		return valor;
	}

	private No remover(No atual, Integer n) {

		if (comparar(atual.getValor(), n) == 0) {
			if (atual.getDireita() == null && atual.getEsquerda() == null) {
				return null;
			} else if (atual.getDireita() == null) {
				return rotacionar(atual.getEsquerda());
			} else if (atual.getEsquerda() == null) {
				return rotacionar(atual.getDireita());
			} else {
				No pre = atual.getEsquerda();
				No predecessor;
				if (pre.getDireita() == null) {
					predecessor = pre;
					predecessor.setDireita(atual.getDireita());
				} else {
					while (pre.getDireita().getDireita() != null) {
						pre = pre.getDireita();
					}
					predecessor = pre.getDireita();
					pre.setDireita(predecessor.getEsquerda());
					predecessor.setEsquerda(atual.getEsquerda());
					predecessor.setDireita(atual.getDireita());
				}
				return predecessor;
			}
		} else {
			if (comparar(n, atual.getValor()) > 0) {
				atual.setDireita(rotacionar(remover(atual.getDireita(), n)));
			} else {
				atual.setEsquerda(rotacionar(remover(atual.getEsquerda(), n)));
			}
			return rotacionar(atual);
		}
	}

	/**
	 * Checks if the AVL tree contains a valor entry
	 * 
	 * @param valor Integerhe valor entry to be checked
	 * @return If the valor entry is in the AVL tree
	 */
	public boolean contem(Integer valor) {
		if (isVazio())
			return false;
		return contem(raiz, valor);
	}

	private boolean contem(No atual, Integer n) {
		if (atual == null)
			return false;
		if (comparar(atual.getValor(), n) == 0) {
			return true;
		} else {
			if (contem(atual.getDireita(), n)) {
				return true;
			} else if (contem(atual.getEsquerda(), n)) {
				return true;
			}
			return false;
		}
	}


	private No atualizarAlturaFator(No n) {
		int alturaEsquerda, alturaDireita;
		alturaEsquerda = n.getEsquerda() != null ? n.getEsquerda().getAltura() : -1;
		alturaDireita = n.getDireita() != null ? n.getDireita().getAltura() : -1;
		n.setFatorBalanco(alturaEsquerda - alturaDireita);
		n.setAltura((alturaDireita > alturaEsquerda ? alturaDireita : alturaEsquerda) + 1);
		return n;
	}


	private No rotacionar(No n) {
		if (n == null)
			return n;
		n = atualizarAlturaFator(n);
		if (n.getFatorBalanco() < -1) {
			if (n.getDireita().getFatorBalanco() > 0) {
				n = rotacionarDireitaEsquerda(n);
			} else {
				n = rotacionarEsquerda(n);
			}
		} else if (n.getFatorBalanco() > 1) {
			if (n.getEsquerda().getFatorBalanco() < 0) {
				n = rotacionarEsquerdaDireita(n);
			} else {
				n = rotacionarDireita(n);
			}
		}
		return n;
	}

	private No rotacionarEsquerda(No n) {
		System.out.println("Rotação à Esquerda efetuada no nó:" + n.getValor());
		No novaRaiz = n.getDireita();
		No temp = n.getDireita().getEsquerda();
		n.getDireita().setEsquerda(n);
		n.setDireita(temp);
		n = atualizarAlturaFator(n);
		return novaRaiz;
	}

	private No rotacionarDireita(No n) {
		System.out.println("Rotação à Direita efetuada no nó:" + n.getValor());
		No newRoot = n.getEsquerda();
		No temp = n.getEsquerda().getDireita();
		n.getEsquerda().setDireita(n);
		n.setEsquerda(temp);
		n = atualizarAlturaFator(n);
		return newRoot;
	}

	private No rotacionarEsquerdaDireita(No n) {
		System.out.println("Rotação Esquerda/Direita efetuada no nó:" + n.getValor());
		n.setEsquerda(rotacionarEsquerda(n.getEsquerda()));
		n = rotacionarDireita(n);
		return n;
	}


	private No rotacionarDireitaEsquerda(No n) {
		System.out.println("Rotação Direita/Esquerda efetuada no nó:" + n.getValor());
		n.setDireita(rotacionarDireita(n.getDireita()));
		n = rotacionarEsquerda(n);
		return n;
	}


	public boolean isVazio() {
		if (tamanho == 0)
			return true;
		return false;
	}

	public void limpar() {
		tamanho = 0;
		raiz = null;
	}


	private int comparar(Integer d1, Integer d2) {
		if (d1 == null && d2 == null) {
			return 0;
		} else if (d1 == null) {
			return 1;
		} else if (d2 == null) {
			return -1;
		} else {
			return d1.compareTo(d2);
		}
	}

	/*
	 * Getters and Setters: Do not modify anything below this point
	 */

	public No getRaiz() {
		return raiz;
	}

	public void setRaiz(No root) {
		this.raiz = root;
	}

	public int size() {
		return tamanho;
	}

	public void setTamanho(int size) {
		this.tamanho = size;
	}

	public int getTamanho() {
		return tamanho;
	}

}
