package br.uff.ic.algoritmos.arvore;

public class No {

	private Integer valor;
	private No esquerda, direita;
	private int altura;
	private int fatorBalanco;

	public No(Integer data) {
		setValor(data);
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer data) {
		this.valor = data;
	}

	public No getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(No left) {
		this.esquerda = left;
	}

	public No getDireita() {
		return direita;
	}

	public void setDireita(No right) {
		this.direita = right;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int height) {
		this.altura = height;
	}

	public int getFatorBalanco() {
		return fatorBalanco;
	}

	public void setFatorBalanco(int bf) {
		this.fatorBalanco = bf;
	}
}