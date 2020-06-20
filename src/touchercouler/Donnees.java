package touchercouler;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Donnees {
	
	protected static final int WIDTH = 600; //Longueur Fenêtre
	protected static final int HEIGHT = 500; //Largeur Fenêtre
	protected static final int CELL_SIZE = 40; // Taille dalles plateau
	protected static final int SHIP_SIZE = 39; // taille bateau
	
	protected static final int X_CELL = 10; //Taille du plateau
	protected static int SHIP_ROTATE = 0; //Rotation du bateau (0 ou 90)
	
	protected Cell[][] BOARD = new Cell[X_CELL][X_CELL]; //tableau du plateau de jeu
	protected static List<BoundingBox> SHIP_POSITION = new ArrayList<BoundingBox>(); //List des positions des bateaux plac�s
	
	protected double STARTX,STARTY; // Coordon�es de repositionnement en cas de mauvais positionnement
	protected Node selected;protected Ship selected_ship; // Bateau et/ou Node s�lectionn�s
	protected Point2D offset,translateStart; // Pour la position de l'objet pendant le drag
	
	protected static int SHIP_PLACED = 0; //Nombres de bateaux plac�s pour pouvoir d�marrer la partie et l'arr�ter quand ils sont tous coul�s.
	protected static int HIT,MISS = 0; // Compte les tirs touch�s et rat�s
	
	//Création du terrain de jeu
	public class Cell extends StackPane {
		
		int value;
		int size;
		
	    Rectangle rect = new Rectangle(size,size);
		
		public Cell(int x, int y) {
			this.value = 0;
			this.size = CELL_SIZE;
			
			
			rect.setFill(Color.WHITE);
			rect.setStroke(Color.LIGHTGRAY);
			
			setTranslateX(x * CELL_SIZE);
			setTranslateY(y * CELL_SIZE);
			
			this.getChildren().add(rect);
		} 
	}
	
	//Création des bateaux
	public class Ship extends Parent {
		
		public int length;
		public int health;
		public int rotation;
		
		public Ship(int length,Color color) {
	        this.length = length;
	        this.health = length;
	        this.rotation = 0;
	        
	        VBox vbox = new VBox();
	        for (int i = 0; i < length; i++) {
	            Rectangle square = new Rectangle(SHIP_SIZE, SHIP_SIZE);
	            square.setFill(color);
	            square.setStroke(Color.BLACK);
	            vbox.getChildren().add(square);
	        }
	        
	        this.getChildren().add(vbox);
		}	
	}
}
