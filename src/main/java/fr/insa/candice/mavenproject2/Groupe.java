/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.candice.mavenproject2;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
//im

/**
 *
 * @author francois
 */
public class Groupe extends Figure {

    private List<Figure> contient;
    private List<Point> contientp;

    public List<Figure> getContient() {
        return contient;
    }

    public List<Point> getContientP() {
        return contientp;
    }

    public Groupe() {
        this.contient = new ArrayList<Figure>();
        this.contientp = new ArrayList<Point>();

    }

    public void add(Figure f) {
        if (f.getGroupe() != this) {
            if (f.getGroupe() != null) {
                throw new Error("figure déja dans un autre groupe");
            }
            this.getContient().add(f);
            f.setGroupe(this);
        }
    }

    public void addP(Point p) {
        if (p.getGroupe() != this) {
            if (p.getGroupe() != null) {
                throw new Error("point déja dans un autre groupe");
            }
            this.getContientP().add(p);
            p.setGroupe(this);
        }
    }

    public void remove(Figure f) {
        if (f.getGroupe() != this) {
            throw new Error("la figure n'est pas dans le groupe");
        }
        this.getContient().remove(f);
        f.setGroupe(null);
    }

    public void removeP(Point p) {
        if (p.getGroupe() != this) {
            throw new Error("la figure n'est pas dans le groupe");
        }
        this.getContientP().remove(p);
        p.setGroupe(null);
    }

    public void removeAll(List<Figure> lf) {
        for (Figure f : lf) {
            this.remove(f);
        }
    }

    public void clear() {
        List<Figure> toRemove = new ArrayList<>(this.getContient());
        this.removeAll(toRemove);
    }

    public int size() {
        return this.getContient().size();
    }

    /**
     * retourne la figure contenue dans le groupe la plus proche du point et au
     * maximum à distMax du point; retourne null si aucune figure n'est à une
     * distance plus faible que distMax;
     */
    public Figure plusProche(Point p, double distMax) {
        if (this.getContient().isEmpty()) {
            return null;
        } else {
            Figure fmin = this.getContient().get(0);
            double min = fmin.distancePoint(p);
            for (int i = 1; i < this.getContient().size(); i++) {
                Figure fcur = this.getContient().get(i);
                double cur = fcur.distancePoint(p);
                if (cur < min) {
                    min = cur;
                    fmin = fcur;
                }
            }
            if (min <= distMax) {
                return fmin;
            } else {
                return null;
            }
        }
    }

    public Point PplusProche(Point pcl, double distMax) {
        if (this.getContientP().isEmpty()) {
            return null;
        } else {
            Point pmin = this.getContientP().get(0);
            double min = pmin.distancePoint(pcl);
            for (int i = 1; i < this.getContientP().size(); i++) {
                Point pcur = this.getContientP().get(i);
                double cur = pcur.distancePoint(pcl);
                if (cur < min) {
                    min = cur;
                    pmin = pcur;
                }
            }
            if (min <= distMax) {
                return pmin;
            } else {
                return null;
            }
        }
    }

    /**
     * crée un sous groupe avec les figures contenues dans lf. . les figures de
     * lf doivent appartenir au groupe this. . un nouveau groupe sg est créé .
     * les figures de lf sont retirées de this . les figures de lf sont ajoutées
     * au nouveau groupe sg . le groupe sg est ajouté au groupe this
     *
     * @return le sous-groupe créé.
     */
    public Groupe sousGroupe(List<Figure> lf) {
        // verifie que les figures font actuellement partie du groupe
        // et les enleve du groupe
        for (int i = 0; i < lf.size(); i++) {
            Figure f = lf.get(i);
            if (f.getGroupe() != this) {
                throw new Error(f + " n'appartient pas au groupe " + this);
            }
            this.getContient().remove(f);
            f.setGroupe(null);
        }
        Groupe sg = new Groupe();
        for (int i = 0; i < lf.size(); i++) {
            sg.add(lf.get(i));
        }
        this.add(sg);
        return sg;
    }



    public static Groupe groupeTerrain() {   //affichage qui reste sur le canvas
        Point p1 = new Point(0,600);
        Point p2 = new Point(0,450);
        Point p3 = new Point(150, 450);
        Point p4 = new Point(150, 600);
        Point pa1 = new Point(100, 450, Color.RED);
        
        Segment s1 = new Segment(p1, p2);
        Segment s2 = new Segment(p2, p3);
        Segment s3 = new Segment(p3, p4);
        Segment s4 = new Segment(p4, p1);
        Groupe terrain1 = new Groupe();
        terrain1.add(s1);
        terrain1.add(s2);
        terrain1.add(s3);
        terrain1.add(s4);
        Groupe res = new Groupe();
       
        res.add(terrain1);
        res.add(pa1);
        Point p5 = new Point(800,600);
        Point p6 = new Point(800,450);
        Point p7 = new Point(650, 450);
        Point p8 = new Point(650, 600);
        Point pa2 = new Point(700, 450, Color.RED);
        
        Segment s5 = new Segment(p5, p6);
        Segment s6 = new Segment(p6, p7);
        Segment s7 = new Segment(p7, p8);
        Segment s8 = new Segment(p8, p5);
        Groupe terrain2 = new Groupe();
        terrain2.add(s5);
        terrain2.add(s6);
        terrain2.add(s7);
        terrain2.add(s8);
        
        res.add(terrain2);
        res.add(pa2);
        


        return res;
    }
    
     @Override
    public double distancePoint(Point p) {
        if (this.contient.isEmpty()) {
            return new Point(0, 0).distancePoint(p);
        } else {
            double dist = this.contient.get(0).distancePoint(p);
            for (int i = 1; i < this.contient.size(); i++) {
                double cur = this.contient.get(i).distancePoint(p);
                if (cur < dist) {
                    dist = cur;
                }
            }
            return dist;
        }
    }
    
@Override
    public double maxX() {
        if (this.contient.isEmpty()) {
            return 0;
        } else {
            double max = this.contient.get(0).maxX();
            for (int i = 1; i < this.contient.size(); i++) {
                double cur = this.contient.get(i).maxX();
                if (cur > max) {
                    max = cur;
                }
            }
            return max;
        }
    }

    /**
     * abscice minimale d'un groupe de figures. 0 si le groupe est vide.
     */
    @Override
    public double minX() {
        if (this.contient.isEmpty()) {
            return 0;
        } else {
            double min = this.contient.get(0).minX();
            for (int i = 1; i < this.contient.size(); i++) {
                double cur = this.contient.get(i).minX();
                if (cur < min) {
                    min = cur;
                }
            }
            return min;
        }
    }

    /**
     * ordonnee maximale d'un groupe de figures. 0 si le groupe est vide.
     */
    @Override
    public double maxY() {
        if (this.contient.isEmpty()) {
            return 0;
        } else {
            double max = this.contient.get(0).maxY();
            for (int i = 1; i < this.contient.size(); i++) {
                double cur = this.contient.get(i).maxY();
                if (cur > max) {
                    max = cur;
                }
            }
            return max;
        }
    }

    /**
     * ordonnee minimale d'un groupe de figures. 0 si le groupe est vide.
     */
    @Override
    public double minY() {
        if (this.contient.isEmpty()) {
            return 0;
        } else {
            double min = this.contient.get(0).minY();
            for (int i = 1; i < this.contient.size(); i++) {
                double cur = this.contient.get(i).minY();
                if (cur < min) {
                    min = cur;
                }
            }
            return min;
        }
    }
    
    @Override
    public void dessine(GraphicsContext context) {
        for (Figure f : this.contient) {
            f.dessine(context);
        }
    }

    @Override
    public void dessineSelection(GraphicsContext context) {
        for (Figure f : this.contient) {
            f.dessineSelection(context);
        }
    }

  

}
