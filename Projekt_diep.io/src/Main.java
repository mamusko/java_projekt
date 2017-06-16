import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Plocha canvas = new Plocha();
		Scene scene = new Scene(canvas,800,640,Color.WHITE);
		canvas.repaint();
		Thread t1 = new Thread(canvas);
        t1.start();
        
       
		primaryStage.setScene(scene);
		primaryStage.show();
		canvas.requestFocus();
		
	}

}
