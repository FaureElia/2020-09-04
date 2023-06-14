/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGrandoMax"
    private Button btnGrandoMax; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="txtRank"
    private TextField txtRank; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMovie"
    private ComboBox<Movie> cmbMovie; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	Movie m=this.cmbMovie.getValue();
    	if(m!=null) {
    		List<Movie> cammino=this.model.doRicorsione(m);
    		this.txtResult.setText("cammino migliore trovato :\n");
    		if(cammino==null) {
    			this.txtResult.setText("nessun confinante");
    		}
    		for(Movie movie: cammino) {
    			this.txtResult.appendText(movie+"\n");
    			
    		}
    	}else {
    		this.txtResult.setText("inserire un film!");
    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.cmbMovie.getItems().clear();
    	String rankS=this.txtRank.getText();
    	if(rankS=="") {
    		this.txtResult.setText("inserire un rank");
    		return;
    	}
    	try {
    		double rank=Double.parseDouble(rankS);
    		List<Movie> vertici=this.model.creaGrafo(rank);
    		this.cmbMovie.getItems().addAll(vertici);
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.setText("Inserire un numero!");
    	}
    	
    }

    @FXML
    void doGradoMax(ActionEvent event) {
    	Movie m=this.model.getGradoMassimo();
    	if(m!=null) {
    		int grado=this.model.getMassimo();
    		this.txtResult.setText("Trovato Movie con grado Massimo: \n");
    		this.txtResult.appendText(m+" grado: "+grado);
    		return;
    	}
    	this.txtResult.setText("trovato nulla");
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGrandoMax != null : "fx:id=\"btnGrandoMax\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRank != null : "fx:id=\"txtRank\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMovie != null : "fx:id=\"cmbMovie\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
