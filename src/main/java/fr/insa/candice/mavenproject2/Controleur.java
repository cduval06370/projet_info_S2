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
    
    private double[] pos1 = new double[2]; //stocker la position de l'extrémité du segment

    public Controleur(Board vue) {
        //le controleur a accès au Board directement
        this.vue = vue;
        this.selection= new ArrayList<> (); // elements selectionnés
        this.selectionp= new ArrayList<> ();

    }

    public void changeEtat(int nouvelEtat) {
        //on ne créé qu'une chose à la fois
        if (nouvelEtat == 30) {  //creation de points / noeuds simples
            //this.vue.getbBarre().setDisable(true);
            // this.vue.getbNoeudApp().setDisable(true);
            this.vue.redrawAll();
            this.getSelection().clear(); // on la met vide au début
                    
        }
        if (nouvelEtat == 40) {  // creation noeud appui
            // this.vue.getbBarre().setDisable(true);
            // this.vue.getbNoeudS().setDisable(true);
            this.vue.redrawAll();
        }
        if (nouvelEtat == 20) {  // creation barre
            //this.vue.getbNoeudS().setDisable(true);
            //this.vue.getbNoeudS().setDisable(true);
            this.vue.redrawAll();
            this.getSelection().clear();
        }
        if (nouvelEtat == 10) {
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

    public void boutonBarre(ActionEvent t) {
        this.changeEtat(20);
    }

    public void boutonSelect(ActionEvent t) {
        this.changeEtat(10);
    }

    void clicDansZoneDessin(MouseEvent t) { //méthode qui traite le clic dans la zone de dessin pour afficher un point

        if (this.etat == 30) { //NOEUD SIMPLE
            double px = t.getX();
            double py = t.getY();
            Color col = Color.BLUE;
            Groupe model = this.vue.getModel();
            model.add(new Point(px, py, col));
            this.vue.redrawAll();

        } else if (this.etat == 40) {   //NOEUD APPUI
            double px = t.getX();
            double py = t.getY();
            Color col = Color.RED;
            Groupe model = this.vue.getModel();
            model.add(new Point(px, py, col));
            this.vue.redrawAll();
        }

            //SEGMENT
       //  else if (this.etat == 20) { //coordonnées de l'etrémité 1
       //     this.pos1[0] = t.getX();
       //     this.pos1[1] = t.getY();
       //     this.changeEtat(21);
       // } else if (this.etat == 21) {//création du segment
       //     double px2 = t.getX();
       //     double py2 = t.getY();
        //    Color col = Color.GREENYELLOW;
       //     this.vue.getModel().add(
       //             new Segment(new Point(this.pos1[0], this.pos1[1]), new Point(px2, py2, col)));
       //     this.vue.redrawAll();
       //     this.changeEtat(20); // qaund on a fait deux etrémités, on revient à la création d'un segment= etat 20
       //}
            
        else if (this.etat == 20) {
            //Point pclic1 = new Point(t.getX(), t.getY());
            //Point proche1 = this.vue.getModel().PplusProche(pclic1, Double.MAX_VALUE);
           // this.pos1[0]=proche1.getPx();
            //this.pos1[0]=proche1.getPx();
            this.pos1[0] = t.getX();
            this.pos1[1] = t.getY();
            this.vue.getModel().add(new Point(this.pos1[0], this.pos1[1], Color.YELLOW));
            
             this.changeEtat(21);
             } else if (this.etat == 21) {
               Point pclic2 = new Point(t.getX(), t.getY()); 
               //Point proche2 = this.vue.getModel().PplusProche(pclic2, Double.MAX_VALUE);
               double px2=pclic2.getPx();
               double py2=pclic2.getPy();
               Color col = Color.GREENYELLOW;
               //this.vue.getModel().add(
               //new Segment(new Point(this.pos1[0], this.pos1[1]), new Point(px2, py2, col)));
               
               this.vue.getModel().add(new Point(px2, py2, col));
               this.vue.redrawAll();
               this.changeEtat(20);
             }
            
            
            
         else if (this.etat == 100) { // selection
           
            Point pclic = new Point(t.getX(), t.getY()); // la ou on a cliqué
            Point proche = this.vue.getModel().PplusProche(pclic, Double.MAX_VALUE);
             //si pas de plus proche
            if (proche != null) {
                if (t.isShiftDown()) {
                  this.getSelection().add(proche);
                } 
               else if (t.isControlDown()) {  //si on veut  faire avec le clavier
                   if (this.selectionp.contains(proche)) {
                        this.selectionp.remove(proche);
                    } else {
                        this.selectionp.add(proche);
                    }
                } else {
                this.getSelection().clear();
               this.getSelection().add(proche);
                    
                }
                // this.activeBoutonsSuivantSelection(); //pr les regrouper. pas besoin
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

   
    

