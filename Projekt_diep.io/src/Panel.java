import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Panel {
	
	int maxValue = 10;
	String [] Text = {"1","2","3","4","5","6","7"};
	public void paint(Plocha p){
		Text t = new Text(10,370, "Upgrades avalible: " + p.hrac.upgrades);
	    t.setFont(new Font(20));
	    p.getChildren().add(t);
	    
		for (int i = 0; i < 6;i++){
			paintBar(p,0.1,i);
		}

		
	}
	
	public void paintBar(Plocha p, double hodnota, int i){
		ProgressBar pb = new ProgressBar(0);
	    pb.setLayoutX(20);
	    pb.setLayoutY(403 +40*i);
	    Text t = new Text(10, 400 +40*i, Text[i]);
	    t.setFont(new Font(20));
	    pb.setStyle("-fx-accent: blue;"); 
	    pb.setScaleX(1.2);
	    pb.setScaleY(1);
	    p.getChildren().add(pb);
	    p.getChildren().add(t);
	}
	
	public void AddPoint(){
		//Points++;
	}
}
