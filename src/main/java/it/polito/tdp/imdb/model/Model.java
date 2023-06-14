package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	private Graph<Movie,DefaultWeightedEdge> grafo;
	private Map<Integer, Movie> idMapMovie;
	private int gradoMax;
	//ricorsione
	private List<Movie> camminoMigliore;
	private int lunghezzaMigliore;
	
	public Model() {
		this.dao=new ImdbDAO();
	}

	public List<Movie> creaGrafo(double rank) {
		this.idMapMovie=new HashMap<>();
		this.grafo=new SimpleWeightedGraph<Movie,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Movie> vertici=this.dao.listAllRankedMovies();
		for(Movie m:vertici) {
			this.idMapMovie.put(m.getId(), m);
		}
		Graphs.addAllVertices(this.grafo, vertici);
		
		List<Coppia> archi=this.dao.getCoppie(rank,this.idMapMovie);
		for (Coppia c: archi) {
			DefaultWeightedEdge e=this.grafo.addEdge(c.getFilm1(), c.getFilm2());
			this.grafo.setEdgeWeight(e, c.getPeso());
		}
		
		System.out.println(this.grafo.vertexSet().size());
		System.out.println(this.grafo.edgeSet().size());
		
		return vertici;
		
		
		
	}

	public Movie getGradoMassimo() {
		if(this.grafo.vertexSet().isEmpty()) {
			return null;
		}
		
		double gradoMassimo=0;
		Movie migliore=null;
		for (Movie m:this.grafo.vertexSet()) {
			double grado=0;
			Set<DefaultWeightedEdge> archiIncidenti=this.grafo.outgoingEdgesOf(m);
			for(DefaultWeightedEdge e: archiIncidenti) {
				double peso=this.grafo.getEdgeWeight(e);
				grado+=peso;
			}
		if (gradoMassimo==0 || grado>gradoMassimo) {
			migliore=m;
			gradoMassimo=grado;
			}	
		}
		
		this.gradoMax=(int)gradoMassimo;
		return migliore;
	}
	
	public int getMassimo() {
		return this.gradoMax;
	}

	public List<Movie> doRicorsione(Movie m) {
		this.camminoMigliore=new ArrayList<Movie>();
		this.lunghezzaMigliore=0;
		if(!this.grafo.vertexSet().contains(m)) {
			System.out.println("Errore!!!");
			return null;
		}
		Set<Movie> confinanti=Graphs.neighborSetOf(this.grafo, m);
		if(confinanti.size()==0) {
			System.out.println("il nodo non ha nessun confinante!");
			return null;
		}
		
		List <Movie> parziale=new ArrayList<Movie>();
		parziale.add(m);
		confinanti.removeAll(parziale);
		cerca(parziale, m, 0,confinanti);
		
		for(int i=1; i<this.lunghezzaMigliore; i++) {
			DefaultWeightedEdge e=this.grafo.getEdge(this.camminoMigliore.get(i),this.camminoMigliore.get(i-1));
			System.out.println(this.grafo.getEdgeWeight(e));
		}
		return this.camminoMigliore;
	}
	
	private void cerca(List<Movie> parziale, Movie last, double lastWeight, Set <Movie> confinanti) {
		if(parziale.size()>this.lunghezzaMigliore || confinanti.size()==0) {
			System.out.println("migliorato");
			this.lunghezzaMigliore=parziale.size();
			this.camminoMigliore=new ArrayList<>(parziale);
		}
		
		for(Movie m: confinanti) {
			if(this.grafo.getEdgeWeight(this.grafo.getEdge(last, m))>lastWeight) {
				parziale.add(m);
				Set<Movie> confinantiNuove=Graphs.neighborSetOf(this.grafo, m);
				confinantiNuove.removeAll(parziale);
				cerca(parziale, m,this.grafo.getEdgeWeight(this.grafo.getEdge(last, m)),confinantiNuove);
				parziale.remove(m);
			}
		}
		
		
	}
	
}
