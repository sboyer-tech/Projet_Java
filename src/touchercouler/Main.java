package touchercouler;



import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	//On récupère la scène crée dans Board
	public void start(Stage primaryStage) {
		Board board = new Board();
		board.start(primaryStage);
		
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Board.launch(args);
		

	}

}
