/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.candice.mavenproject2;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author PAQUIER Frédéric
 */
public class Board extends BorderPane {

    private RadioButton bNoeudS;
    private RadioButton bBarre;
    private RadioButton bNoeudApp;
    private RadioButton bSelect;
    private TextArea provisoire;

    private DessinCanvas cDessin;

    private Controleur controleur;
    private Groupe model;

    public Board() {
        this(new Groupe());
    }//créé un groupe vide

    public Board(Groupe model) {

        this.model = model;
        //il n'y en a qu'un seul donc on peut le créer dès maintenant
        this.controleur = new Controleur(this);
        // On donne l'accès au controleur depuis Board.

        this.bNoeudS = new RadioButton("Noeud simple");
        this.bNoeudS.setOnAction((t) -> {
            System.out.println("noeud simple sélectionné");
            this.controleur.boutonPoints(t);
            this.controleur.changeEtat(30); //on doit fixer l'état au debut pour qu'il sache lequel on a 
        });

        this.bBarre = new RadioButton("Barre");
        this.bBarre.setOnAction((t) -> {
            System.out.println("barre sélectionné");
            this.controleur.boutonBarre(t);
            this.controleur.changeEtat(20);
        });

        this.bNoeudApp = new RadioButton("Noeud d'appui");
        this.bNoeudApp.setOnAction((t) -> {
            System.out.println("noeud appui sélectionné");
            this.controleur.boutonPointsApp(t);
            this.controleur.changeEtat(40);
        });
        
        this.bSelect = new RadioButton("Select");
        this.bSelect.setOnAction((t) -> {
            System.out.println("Selectionnez un objet");
            this.controleur.boutonSelect(t);
            this.controleur.changeEtat(10);
        });
       


        this.controleur = new Controleur(this);

        VBox vbGauche = new VBox(this.getbNoeudS(), this.getbBarre(), this.getbNoeudApp(), this.getbSelect());
        this.setLeft(vbGauche);

        this.cDessin = new DessinCanvas(this);
        this.setCenter(this.cDessin);
        this.cDessin.setOnMouseClicked((t) -> {
            System.out.println("clic en " + t.getX() + "," + t.getY());
        });

        // this.controleur.changeEtat(30); //on doit fixer l'état au debut pour qu'il sache lequel on a
        
        ToggleGroup bgEtat = new ToggleGroup();  //méthode pour avoir un seul boutton selctionné à la fois
        this.bNoeudS.setToggleGroup(bgEtat);
        this.bBarre.setToggleGroup(bgEtat);
        this.bNoeudApp.setToggleGroup(bgEtat);
        this.bSelect.setToggleGroup(bgEtat);

    }

    Board(Stage primaryStage, Groupe groupeTest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // private void setCenter(TextArea provisoire) {
    //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    // }
    //GETTERS
    public void redrawAll() {
        this.cDessin.redrawAll();
    }

    /**
     * @return the controleur
     */
    public Controleur getControleur() {
        return controleur;
    }

    /**
     * @return the model
     */
    public Groupe getModel() {
        return model;
    }

    /**
     * @return the bNoeudS
     */
    public RadioButton getbNoeudS() {
        return bNoeudS;
    }

    /**
     * @return the bBarre
     */
    public RadioButton getbBarre() {
        return bBarre;
    }

    /**
     * @return the bNoeudApp
     */
    public RadioButton getbNoeudApp() {
        return bNoeudApp;
    }

    /**
     * @return the provisoire
     */
    public TextArea getProvisoire() {
        return provisoire;
    }

    /**
     * @return the cDessin
     */
    public DessinCanvas getcDessin() {
        return cDessin;
    }

    /**
     * @return the bSelect
     */
    public RadioButton getbSelect() {
        return bSelect;
    }

}
