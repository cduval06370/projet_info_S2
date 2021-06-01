/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.candice.mavenproject2;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.BLUE;

/**
 *
 * @author PAQUIER Frédéric
 */
public class Controleur {

    private Board vue;
    private int etat;
    private List<Figure> selection;
    private List<Point> selectionp;
    private Point point1;
    private Point point2;
    private Point point3;

    private double[] pos1 = new double[2]; //stocker la position de l'extrémité du segment
    private double[] pos2 = new double[2];

    public Controleur(Board vue) {
        //le controleur a accès au Board directement
        this.vue = vue;
        this.selection = new ArrayList<>(); // elements selectionnés
        this.selectionp = new ArrayList<>();

    }

    public void changeEtat(int nouvelEtat) {
        //on ne créé qu'une chose à la fois
        if (nouvelEtat == 30) {  //creation de points / noeuds simples
            this.vue.redrawAll();
            this.getSelection().clear(); // on la met vide au début

        }
        if (nouvelEtat == 40) {  // creation noeud appui

            this.vue.redrawAll();
        }
        if (nouvelEtat == 20) {  // creation barre

            this.vue.redrawAll();
            this.getSelection().clear();
        }
        if (nouvelEtat == 10) {
            this.vue.redrawAll();
            this.getSelection().clear();
        }
        if (nouvelEtat == 10) {
            this.vue.redrawAll();
            this.getSelection().clear();
        }
        if (nouvelEtat == 50) {
            this.vue.redrawAll();
            this.getSelection().clear();
        }
        if (nouvelEtat == 60) {
            this.vue.redrawAll();
            this.getSelection().clear();
        }

        this.etat = nouvelEtat;
    }

    public void boutonPoints(ActionEvent t) {
        this.changeEtat(30);
    }

    public void boutonPointsApp(ActionEvent t) {
        this.changeEtat(40);
    }

    public void boutonBarretri(ActionEvent t) {
        this.changeEtat(20);
    }

    public void boutonBarredou(ActionEvent t) {
        this.changeEtat(50);
    }

    public void boutonBarresim(ActionEvent t) {
        this.changeEtat(60);
    }

    public void boutonSelect(ActionEvent t) {
        this.changeEtat(10);
    }

    void clicDansZoneDessin(MouseEvent t) { //méthode qui traite le clic dans la zone de dessin pour afficher un point
///////////////////////////NOEUD SIMPLE////////////////////
        if (this.etat == 30) { 
            double px = t.getX();
            double py = t.getY();
            Color col = Color.BLUE;
            Groupe model = this.vue.getModel();
            model.add(new Point(px, py, col));
            this.vue.redrawAll();

 ////////////////////////NOEUD APPUI///////////////////
        } else if (this.etat == 40) {   
            double px = t.getX();
            double py = t.getY();
            Color col = Color.RED;
            Groupe model = this.vue.getModel();
            model.add(new Point(px, py, col));
            this.vue.redrawAll();
          
        } 
//////////////////////////SEGMENT SIMPLE////////////////////////////
        else if (this.etat == 60) { //coordonnées de l'etrémité 1
            this.pos1[0] = t.getX();// on stocke ces coordonnées
            this.pos1[1] = t.getY();
            point1 = new Point(this.pos1[0],this.pos1[1],Color.BLUE);
            this.vue.getModel().add(point1);
            this.changeEtat(61);
        } else if (this.etat == 61) {//création du segment
            double px2 = t.getX();  //2eme clic dans la zone de dessin
            double py2 = t.getY();
            point2 = new Point(px2,py2,Color.BLUE);
            Color col = Color.BROWN;
            this.vue.getModel().add(
                    new Segment(point1,point2,col));           
            this.vue.getModel().add(point2);
            this.vue.redrawAll();
            this.changeEtat(60); // qaund on a fait deux etrémités, on revient à la création d'un segment= etat 60
          
        } 
/////////////////////SEGMENT TRIPLE/////////////////////////
        else if (this.etat == 20) {
            //Point pclic1 = new Point(t.getX(), t.getY());
            //Point proche1 = this.vue.getModel().PplusProche(pclic1, Double.MAX_VALUE);
            // this.pos1[0]=proche1.getPx();
            //this.pos1[0]=proche1.getPx();
            //L'utilisation de la méthode PplusProche ne fonctionne pas
            this.pos1[0] = t.getX();//enregistrement du premier point
            this.pos1[1] = t.getY();
            point1 = new Point(this.pos1[0], this.pos1[1], Color.BLUE);
            this.vue.getModel().add(point1);

            this.changeEtat(21);
        } else if (this.etat == 21) {
            Point pclic2 = new Point(t.getX(), t.getY());
            //Point proche2 = this.vue.getModel().PplusProche(pclic2, Double.MAX_VALUE);
            double px2 = pclic2.getPx();
            double py2 = pclic2.getPy();
            point2 = new Point(px2, py2, Color.BLUE);
            Color col = Color.BROWN;
            this.vue.getModel().add(new Segment(point1, point2, col));

            this.vue.getModel().add(new Point(px2, py2, Color.BLUE));
            this.vue.redrawAll();
            this.changeEtat(22);
        } else if (this.etat == 22) {
            Point pclic3 = new Point(t.getX(), t.getY());

            this.pos2[0] = t.getX();// on l'enregistre pour faire le troisieme segment
            this.pos2[1] = t.getY();
            Color col = Color.BROWN;
            point3 = new Point(this.pos2[0], this.pos2[1], Color.BLUE);

            this.vue.getModel().add(new Segment(point3, point2, col)); //on relie les trois points entre eux
            this.vue.getModel().add(new Segment(point3, point1, col));

            this.vue.getModel().add(point3);
            this.vue.redrawAll();
            this.changeEtat(20);

        } 
 /////////////////////////SEGMENT DOUBLE//////////////////
        else if (this.etat == 50) {
            
            this.pos1[0] = t.getX();
            this.pos1[1] = t.getY();
            point1 = new Point(this.pos1[0], this.pos1[1], Color.BLUE);
            this.vue.getModel().add(point1);

            this.changeEtat(51);
        } else if (this.etat == 51) {
            Point pclic2 = new Point(t.getX(), t.getY());
            //Point proche2 = this.vue.getModel().PplusProche(pclic2, Double.MAX_VALUE);
            double px2 = pclic2.getPx();
            double py2 = pclic2.getPy();
            point2 = new Point(px2, py2, Color.BLUE);
            Color col = Color.BROWN;
            
            this.vue.getModel().add(new Segment(point1, point2, col));
            this.vue.getModel().add(new Point(px2, py2, Color.BLUE));
            this.vue.redrawAll();
             this.changeEtat(52);
        }else if (this.etat == 52) {
            Point pclic3 = new Point(t.getX(), t.getY());

            this.pos2[0] = t.getX();// on l'enregistre pour faire le deuxieme segment
            this.pos2[1] = t.getY();
            Color col = Color.BROWN;
            point3 = new Point(this.pos2[0], this.pos2[1], Color.BLUE);

            this.vue.getModel().add(new Segment(point3, point2, col));

            this.vue.getModel().add(point3);
            this.vue.redrawAll();
            this.changeEtat(50);
       
        } 

        else if (this.etat == 10) { // selection

            Point pclic = new Point(t.getX(), t.getY()); // la où on a cliqué
            Figure proche = this.vue.getModel().plusProche(pclic, Double.MAX_VALUE);
            //si pas de plus proche
            if (proche != null) {
                if (t.isShiftDown()) {
                    this.getSelection().add(proche);
                } else if (t.isControlDown()) {  //si on veut  faire avec le clavier
                    if (this.selection.contains(proche)) {
                        this.selection.remove(proche);
                    } else {
                        this.selection.add(proche);
                    }
                } else {
                    this.getSelection().clear();
                    this.getSelection().add(proche);

                }
                this.vue.redrawAll();
                //voir les selectionnées

            }
        }

    }

    public List<Figure> getSelection() {
        return selection;
    }

    public void setSelection(List<Figure> selection) {
        this.selection = selection;
    }

}
