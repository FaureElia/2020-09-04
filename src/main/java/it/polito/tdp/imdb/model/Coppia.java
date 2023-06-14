package it.polito.tdp.imdb.model;

public class Coppia {
	
	private Movie film1;
	private Movie film2;
	private int peso;
	
	public Coppia(Movie film1, Movie film2, int peso) {
		this.film1 = film1;
		this.film2 = film2;
		this.peso = peso;
	}
	
	public Movie getFilm1() {
		return film1;
	}
	public void setFilm1(Movie film1) {
		this.film1 = film1;
	}
	public Movie getFilm2() {
		return film2;
	}
	public void setFilm2(Movie film2) {
		this.film2 = film2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	

}
