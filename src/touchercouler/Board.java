package touchercouler;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import touchercouler.Donnees.Cell;
import touchercouler.Donnees.Ship;

public class Board extends Application {
	
	//Création plateau avec différentes cellules

	private Parent CreateBoard() {
		
		//Fenétre plus données.
		Donnees data = new Donnees();
		Pane root = new Pane();
		root.setPrefSize(Donnees.WIDTH, Donnees.HEIGHT);
		
		//Group contenant les objets.
		Group GameBoard = new Group();
		Group Ship = new Group();
		Group button = new Group();
		
		//Sons libres de droits.
		AudioClip startSound = new AudioClip(new File("son/start.wav").toURI().toString());
		AudioClip ploufSound = new AudioClip(new File("son/plouf.wav").toURI().toString());
		AudioClip hitSound = new AudioClip(new File("son/hit.wav").toURI().toString());
		AudioClip fatalSound = new AudioClip(new File("son/finisher.wav").toURI().toString());
		AudioClip victorySound = new AudioClip(new File("son/victory.wav").toURI().toString());
		
		//Images libres de droits ou crées avec Aseprite.
		ImageView background = new ImageView(new File("images/background.png").toURI().toString());
		ImageView ending = new ImageView(new File("images/ending.png").toURI().toString());
		ImageView sea = new ImageView(new File("images/sea.png").toURI().toString());
		ending.relocate(20, 0);
		
		//Création du plateau de jeu
		for(int i = 0; i<Donnees.X_CELL; i++) {
			for(int j = 0; j<Donnees.X_CELL; j++) {
				Cell cell = data.new Cell(i,j);
				cell.getChildren().remove(0);
				Rectangle rect = new Rectangle(Donnees.CELL_SIZE,Donnees.CELL_SIZE);
				rect.setFill(Color.WHITE);
				rect.setStroke(Color.LIGHTGRAY);
				cell.getChildren().add(rect);
				data.BOARD[i][j] = cell;
				GameBoard.getChildren().add(cell);
			}
		}
		
		//Bateaux
		Ship ship1 = data.new Ship(2,null);
		Ship ship2 = data.new Ship(3,null);
		Ship ship3 = data.new Ship(3,null);
		Ship ship4 = data.new Ship(4,null);
		Ship ship5 = data.new Ship(5,null);
		ship1.relocate(500, 245);
		ship2.relocate(450, 205);
		ship3.relocate(450, 30);
		ship4.relocate(500, 30);
		ship5.relocate(550, 30);
		
		//Boutons
		Button reset = new Button("reset");
		Button start = new Button("Start");

		start.setStyle("-fx-padding: 10; -fx-background-color: linear-gradient(to bottom, #ff3737, #a23b3b); -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 50; ");
		reset.setStyle("-fx-padding: 10; -fx-background-color: linear-gradient(to bottom, #ff3737, #a23b3b); -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 50; ");
		reset.setTranslateX(430);reset.setTranslateY(430);
		start.setTranslateX(520);start.setTranslateY(430);
		
		
		//Groups
		Ship.getChildren().addAll(ship1,ship2,ship3,ship4,ship5);
		button.getChildren().add(reset);
		root.getChildren().addAll(background,GameBoard,Ship,button);
		
		//Son de bienvenue.
		startSound.play();
		
		
		//Simple changement de couleur du bouton
		start.addEventHandler(MouseEvent.MOUSE_PRESSED,
			    new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent e) {
			         start.setStyle("-fx-padding: 10; -fx-background-color: radial-gradient(radius 180%, #ff0000, #280707); -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 50; ");
			    }
			});
		
		//Enclenchement de la deuxième partie.
		start.addEventHandler(MouseEvent.MOUSE_RELEASED,
	            new EventHandler<MouseEvent>() {
	              @Override
	              public void handle(MouseEvent e) {
	            	  
	            	  start.setStyle("-fx-padding: 10; -fx-background-color: linear-gradient(to bottom, #ff3737, #a23b3b); -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 50; ");
	          		
	            	  
	            	  System.out.println("Que la partie commence");
	            	  button.getChildren().removeAll(start,reset);
	            	  
	            	  
	            	  //attribue une valeur aux cases comportant un bateau(valeurs variant pour chaque bateau).
	            	  for(int i=0; i<Ship.getChildren().size(); i++) {
	            		  
	            		  Ship ship_selected = (Ship) Ship.getChildren().get(i);
	            		  
	            		  int x = (int) (ship_selected.localToScene(ship_selected.getBoundsInLocal().getMinX(),ship_selected.getBoundsInLocal().getMinY()).getX()); 
	      		          int y = (int) (ship_selected.localToScene(ship_selected.getBoundsInLocal().getMinX(),ship_selected.getBoundsInLocal().getMinY()).getY());
	      		          ship_selected.setVisible(false);
	      		        
	            		  for(int j=0; j<ship_selected.length; j++){
	            			  
	            			  data.BOARD[x/40][y/40].value = i+1;
	            			  
	            			  if(ship_selected.rotation == 0) {
	            				  y = y+(40);
	            			  }
	            			  else {
	            				  x = x+(40);
	            			  }
	            		  }  
	            	  }
	            	  
	            	  //Re-création de nouvelles cases pour le plateau.
	            	  for(int i = 0; i<Donnees.X_CELL; i++) {
	            			for(int j = 0; j<Donnees.X_CELL; j++) {
	            				
	            				Cell current = (Cell) GameBoard.getChildren().get(Donnees.X_CELL*i+j);
	            				current.getChildren().remove(0);
	            				Rectangle rect = new Rectangle(Donnees.CELL_SIZE,Donnees.CELL_SIZE);
	            				rect.setFill(Color.BLACK);
	            				rect.setStroke(Color.LIGHTGRAY);
	            				current.getChildren().add(rect);
	            				
	            				//Rends les cases cliquables.
	            				current.addEventHandler(MouseEvent.MOUSE_PRESSED,
	            			            new EventHandler<MouseEvent>() {
	            			              @Override
	            			              public void handle(MouseEvent e) {
	            			            	  
	            			            	  //Si la case n'a pas était cliqué
	            			            	  if(current.value>=0) {
		          	            				
		          	            				current.getChildren().remove(0);
			          	            			Rectangle rect = new Rectangle(Donnees.CELL_SIZE,Donnees.CELL_SIZE);
			          	            			rect.setStroke(Color.LIGHTGRAY);
			          	            			
			          	            			if(current.value==0) {
			          	            				ploufSound.play();
			          	            				rect.setFill(Color.BLUE);Donnees.MISS+=1;
			          	            				System.out.println("loupé");
			          	            				current.value=-10;
			          	            			}
			          	            			else {
			          	            				Ship new_ship_selected = (Ship) Ship.getChildren().get(current.value-1);
			          	            				new_ship_selected.health-=1;rect.setFill(Color.ORANGE);
			          	            				
			          	            				if(new_ship_selected.health>0) {
			          	            					hitSound.play();
			          	            					System.out.println("Touché");
			          	            				}
			          	            				
			          	            				else {
			          	            					fatalSound.play();
			          	            					System.out.println("Coulé !");
			          	            					Donnees.SHIP_PLACED-=1;
			          	            					new_ship_selected.setVisible(true);
			          	            					//new_ship_selected.relocate(450+(50*(current.value%3)), 30);
			          	            					rect.setFill(Color.RED);
			          	            					Donnees.MISS+=1;
			          	            					
			          	            					//Impossibilité  de modifier la couleur du bateau directemennt car getChildrenUnmodifiable(),
			          	            					//� la place change les cells ayant la même valeur en rouges.
			          	            					for(int i = 0; i<Donnees.X_CELL; i++) {
			          	            						for(int j = 0; j<Donnees.X_CELL; j++) {
			          	            							Cell new_current = (Cell) GameBoard.getChildren().get(Donnees.X_CELL*i+j);
			          	            							if(new_current.value==-current.value) {
			          	            								new_current.getChildren().remove(0);
			          	            								Rectangle rect1 = new Rectangle(Donnees.CELL_SIZE,Donnees.CELL_SIZE);
			          	            								rect1.setFill(Color.RED);
			          	            								rect1.setStroke(Color.LIGHTGRAY);
			          	            								new_current.getChildren().add(rect1);
			          	            							}
			          	            						}
			          	            					}
			          	            				}
			          	            				current.value=-current.value;
			          	            				Donnees.HIT +=1;
			          	            			}
			          	            			
		          	            				current.getChildren().add(rect);
		          	            				
			          	            			
		          	            				
		          	            			}
		          	            			
		          	            			//Lance la fin du jeu
	          	            				if(Donnees.SHIP_PLACED==0) {
	          	            					System.out.println("jeu Terminé");
	          	            					System.out.println("Nombres de tirs : "+(Donnees.HIT+Donnees.MISS));
	          	            					System.out.println("Ratio : "+(int)(((double)Donnees.HIT/(double)(Donnees.HIT+Donnees.MISS))*100)+"%");
	          	            					current.removeEventHandler(MouseEvent.MOUSE_PRESSED, this);
	          	            					root.getChildren().removeAll(background,GameBoard,Ship);
	          	            					Text text = new Text();
	          	            					Text tir = new Text();
	          	            					Text ratio = new Text();
	          	            					text.setText("     Finish ");
	          	            					tir.setText("Nombres de tirs :"+(Donnees.HIT+Donnees.MISS));
	          	            					ratio.setText("Ratio : "+(int)(((double)Donnees.HIT/(double)(Donnees.HIT+Donnees.MISS))*100)+"%");
	          	            					text.relocate(150, 200);tir.relocate(150, 250);ratio.relocate(200, 290);
	          	            					text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
	          	            					tir.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
	          	            					ratio.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
	          	            					text.setFill(Color.BROWN);
	          	            					tir.setFill(Color.ORANGE);
	          	            					ratio.setFill(Color.ORANGE);
	          	            					
	          	            					root.getChildren().addAll(sea,ending,text,tir,ratio);
	          	            					victorySound.play();
	          	            				}
	            			              }
	            			            });
	            			}
	              	  }
	            	  
	            	  //Start.start();
	            	  
	              }
	              
	            });
		
		//Simple changement de couleur du bouton
		reset.addEventHandler(MouseEvent.MOUSE_PRESSED,
	            new EventHandler<MouseEvent>() {
	              @Override
	              public void handle(MouseEvent e) {
	            	  reset.setStyle("-fx-padding: 10; -fx-background-color: radial-gradient(radius 200%, #ff0000, #280707); -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 50; ");
	              }
	    	});
		
		//Bouton défaillant pour l'instant car remmettre les bateaux à leur emplacement d'origine avec relocate
		//rendant le repositionnement dans le plateau complétemment bugé
		reset.addEventHandler(MouseEvent.MOUSE_RELEASED,
	            new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						reset.setStyle("-fx-padding: 10; -fx-background-color: linear-gradient(to bottom, #ff3737, #a23b3b); -fx-background-radius: 50; -fx-border-color: black; -fx-border-width: 3; -fx-border-radius: 50; ");

		          		Donnees.SHIP_PLACED=0;
		          		
		          		for(int i=0;i<5;i++) {
		          			Bounds ship = Ship.getChildren().get(i).localToScene(Ship.getChildren().get(i).getBoundsInLocal());
		          			//getRotate() n'ayant pas march� ici on compare la longueur et la largeur.
		          			if(ship.getMaxX()-ship.getMinX()>ship.getMaxY()-ship.getMinY()) {
		          				//data.selected_ship = ((Ship) Ship.getChildren().get(i));
		          				//data.selected_ship.rotation=90;
		          				//System.out.println("euuuh");
		          				Ship.getChildren().get(i).setRotate(90);
		          			}
		          			System.out.println((Ship) Ship.getChildren().get(i));
		          		}
		          		
		          		for(int i=0;i<Donnees.SHIP_POSITION.size();i++) {
		          			Donnees.SHIP_POSITION.remove(0);
		          		}
		          		
		          		ship1.relocate(500, 245);
		          		ship2.relocate(450, 205);
		          		ship3.relocate(450, 30);
		          		ship4.relocate(500, 30);
		          		ship5.relocate(550, 30);
		          		
		          		/*for(int i=0;i<Ship.getChildren().size();i++) {
		            		  Ship.getChildren().remove(0);
		            	  }
		            	  for(int i=0;i<button.getChildren().size();i++) {
		            		  button.getChildren().remove(0);
		            	  }
		            	  for(int i=0;i<root.getChildren().size();i++) {
		            		  root.getChildren().remove(0);
		            	  }*/
					}
	            });

		root.setOnMousePressed(evt -> {
			
	    	Node target = (Node) evt.getTarget();

	        if (target.getClass().getName() == "javafx.scene.layout.VBox" && evt.getX()>Donnees.X_CELL*40) { // Si c'est bien le bateau.
	        	
	        	//Différentes valeur permettant de placer le bateau en suivant la souris.
	        	data.selected = target;
	        	data.offset = new Point2D(evt.getX(), evt.getY());
	        	data.translateStart = new Point2D(data.selected.getTranslateX(), data.selected.getTranslateY());
	            
	        	data.STARTX = (evt.getX() - data.offset.getX() + data.translateStart.getX()); // Servent au cas ou
	        	data.STARTY = (evt.getY() - data.offset.getY() + data.translateStart.getY()); // d'un mauvais positionnement.

	        } else {
	        	data.selected = null;
	        }
	        evt.consume();
	    });
		
		//Mouvement du bateau.
		root.setOnMouseDragged(evt -> {
	        if (data.selected != null) {
	        	data.selected.setTranslateX(evt.getX() - data.offset.getX() + data.translateStart.getX());
	        	data.selected.setTranslateY(evt.getY() - data.offset.getY() + data.translateStart.getY());
	            
	        }
	        evt.consume();
	    });
		
		root.setOnMouseReleased(evt -> {
			
		
			if (data.selected != null) {
				
				//R�cup�re le bateau li� � la vbox afin de pouvoir appeler ses propri�t�s.
				data.selected_ship = (Ship) data.selected.getParent();

		        if (data.STARTX == (evt.getX() - data.offset.getX() + data.translateStart.getX()) && data.STARTY == (evt.getY() - data.offset.getY() + data.translateStart.getY()) && data.selected.getClass().getName() == "javafx.scene.layout.VBox") {
		        	
		    		if(data.selected.getRotate()==90) {
		    			data.selected_ship.rotation=0;
		    		}
		        	
		    		else {
		    			data.selected_ship.rotation=90;
		    		}
		        	
		    		data.selected.setRotate(data.selected_ship.rotation);
		    		System.out.println(data.selected_ship);
		        }
		        
		        else {
		        	
	        		double xreel;
	        		double yreel;
	        		//Récupère la positon du bateau dans la fenètre en fonction de sa rotation.
	        		if(data.selected_ship.rotation==0) {
	        			xreel = data.selected.localToScene(data.selected.getBoundsInLocal().getMinX(),data.selected.getBoundsInLocal().getMinY()).getX();
	        			yreel = data.selected.localToScene(data.selected.getBoundsInLocal().getMinX(),data.selected.getBoundsInLocal().getMinY()).getY();
	        		}
	        		else {
	        			xreel = data.selected.localToScene(data.selected.getBoundsInLocal().getMinX(),data.selected.getBoundsInLocal().getMinY()).getX()-40*data.selected_ship.length;
		        		yreel = data.selected.localToScene(data.selected.getBoundsInLocal().getMinX(),data.selected.getBoundsInLocal().getMinY()).getY();
	        		}
	        		int flag = 0;
	        		
	        		Bounds x = data.selected.localToScene(data.selected.getBoundsInLocal()); //Données gèométriques du bateau.
	        		
	        		//Vérifie les collisions entre les bateaux grâce à la liste des coordonnées des bateaux .
	        		for(int i =0; i<Donnees.SHIP_POSITION.size(); i++) {
	        			BoundingBox y = Donnees.SHIP_POSITION.get(i);
	        			//Ce for est important car il faut le faire pour chaque carré formant le bateau.
	        			for(int j =0; j<=data.selected_ship.length;j++) {
	        				if(data.selected_ship.rotation==0 &&((y.getMinX()<x.getMinX() && x.getMinX()<y.getMaxX()-20 && y.getMinY()<x.getMinY()+(38*j) && x.getMinY()+(38*j)<y.getMaxY()) || (y.getMinX()+20<x.getMaxX() && x.getMaxX()<y.getMaxX() && y.getMinY()<x.getMinY()+(38*j) && x.getMinY()+(38*j)<y.getMaxY()))) {
		        				flag =1;
		        			}
	        			
	        			
	        				else if(data.selected_ship.rotation==90 &&((y.getMinX()<x.getMinX()+(38*j) && x.getMinX()+(38*j)<y.getMaxX() && y.getMinY()<x.getMinY() && x.getMinY()<y.getMaxY()-20) || (y.getMinX()<x.getMinX()+(38*j) && x.getMinX()+(38*j)<y.getMaxX() && y.getMinY()+20<x.getMaxY() && x.getMaxY()<y.getMaxY()))) {
	        					flag =1;
	        				}
	        			}
	        		}
	        		
	        		/*ObservableList<Node> y = selected_ship.getParent().getChildrenUnmodifiable(); //r�cup�re liste des bateaux
	        		for(int i =0; i<y.size(); i++) {
	        			if(y.get(i)!=selected_ship) {
	        				Bounds z = y.get(i).localToScene(selected.getBoundsInLocal());//donn�es g�om�triques des autres bateaux
	        				
	        				System.out.println("les autres: "+z);
	        				
	        				if(x.intersects(z)) { //si 2 bateaux se croisent... ne marche pas?
	        					flag =1;
	        					System.out.println("coucou");
	        				
		        			//Ancienne solution, ne marche pas car intersect prend les valeurs exactes, il faut que le bateau soit pile au m�me endroit
		        			//sinn il ne les consid�re pas comme se chevauchant mais surtout ont peut voir avec les print que les coordonn�es des
		        			//bateaux reviennent � leur valeur de d�part alors que les bateaux sont bien plac�s dans le jeu... myst�re? 
		        			
	        				}
	        			}
	        		}*/
	        		
	        		// Positionne le bateau si il respecte les conditions demandées.
	        		if((flag==0 && ((data.selected_ship.rotation==0 && xreel>0 && xreel<Donnees.X_CELL*40-20 && yreel>0 && yreel<Donnees.X_CELL*40+20-40*data.selected_ship.length) || (data.selected_ship.rotation==90 && xreel>0 && xreel<Donnees.X_CELL*40+20-40*data.selected_ship.length && yreel>0 && yreel<Donnees.X_CELL*40-20)))) {
		        		
	        			//Positionne dans la case la plus proche.
	        			if(xreel%40>20 && yreel%40>20) {data.selected.relocate(40-xreel%40,40-yreel%40);}
		        		else if(xreel%40>20 && yreel%40<20) {data.selected.relocate(40-xreel%40,-yreel%40);}
		        		else if(xreel%40<20 && yreel%40>20) {data.selected.relocate(-xreel%40,40-yreel%40);}
		        		else{data.selected.relocate(-xreel%40,-yreel%40);}
	        			
	        			// Rempli la liste des positions des bateaux placés pour les collisions.
		        		Donnees.SHIP_POSITION.add((BoundingBox) data.selected.localToScene(data.selected.getBoundsInLocal()));
		        		
		        		Donnees.SHIP_PLACED+=1;
		        		
		        		//Démarre le jeu quand tous les bateaux sont placés.
		        		if(Donnees.SHIP_PLACED==5) {
		        			button.getChildren().add(start);
		        		
		        		}
		        	}
		        	
		        	else {
		        		// Si les conditions de positionnement ne sont pas respect�es, retour  à la case départ.
		        		flag = 0;
		        		data.selected.setTranslateX(data.STARTX);
		        		data.selected.setTranslateY(data.STARTY);
		        	}
		        	
		        }
		        
		        evt.consume();
			}
		});
		
		return root;
		
	}
	
	//Création de la scène qui accueille le plateau
	public void start(Stage primaryStage) {

        Image icon = new Image(new File("images/icon.png").toURI().toString());
		Scene scene = new Scene(CreateBoard());
		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("battleship");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		}
}
