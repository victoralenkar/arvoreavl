package br.uff.ic.algoritmos.arvore;

public class No {

	private int valor;
	private No esquerda;
	private No direita;
	private int altura;
	private int totalNos;

	public int getValor() {
		return valor;
	}

	public No() {
		this.totalNos = 0;
		this.altura = 0;
	}

	public No(int valor) {
		this();
		this.valor = valor;
		this.totalNos = 1;
	}

	public int getAltura(No node) {
		int alt = 0;
		if (node.getEsquerda() == null && node.getDireita() == null) {
			return 0;
		} else {
			int alturaEsq = node.getEsquerda() != null ? (node.getEsquerda().getAltura(node.getEsquerda()) + 1) : 0;
			int alturaDir = node.getDireita() != null ? (node.getDireita().getAltura(node.getDireita()) + 1) : 0;
			alt = Math.max(alturaEsq, alturaDir);
			node.setAltura(alt);
		}
		return alt;
	}

	public void inserir(int valor) {
		if (this.esquerda == null && this.direita == null) {
			if (valor <= this.valor) {
				this.esquerda = new No(valor);
			} else {
				this.direita = new No(valor);
			}
		} else {
			if (valor <= this.valor) {
				if (this.esquerda != null) {
					this.esquerda.inserir(valor);
				} else {
					this.esquerda = new No(valor);
				}
			} else {
				if (this.direita != null) {
					this.direita.inserir(valor);
				} else {
					this.direita = new No(valor);
				}
			}
		}
		this.altura = getAltura(this);
		this.totalNos+=1;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public No getEsquerda() {
		return esquerda;
	}

	public void setLeft(No left) {
		this.esquerda = left;
	}

	public No getDireita() {
		return direita;
	}

	public void setRight(No right) {
		this.direita = right;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getTotalNos() {
		return this.totalNos;
	}

}
