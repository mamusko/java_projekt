import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

class Hrac implements Runnable{
	double x;
	double y;
	double bar_x;
	double bar_y;
	double dmg;
	double maxHp = 50;
	double curHp;
	double h = 50;
	double w = 20;
	double r = 30;
	int attack_speed = 300;
	double angle;
	Plocha p;
	double bullet_speeed;
	double movement_speed = 0.5;

	public Hrac(Plocha plocha){
		x = 400;
		y = 320;
		dmg = 10;
		curHp = maxHp;
		bar_x = 345;
		bar_y = 600;
		angle = 0;
		p = plocha;

		
	}
	
	public void paint(){
		
		
		Rectangle delo = new Rectangle(w, h, Color.BLACK);
		delo.setX(x-10);
		delo.setY(y);
		delo.setFill(Color.GRAY);
		p.getChildren().add(delo);
		Rotate rotate = new Rotate(); 
        rotate.setAngle(Math.toDegrees(angle)+180); 
        rotate.setPivotX(x); 
        rotate.setPivotY(y); 
        delo.getTransforms().addAll(rotate); 
        
        Circle c = new Circle(x,y,r);
        c.setFill(Color.BLUEVIOLET);
        p.getChildren().add(c);
        
        ProgressBar pb = new ProgressBar(curHp/maxHp);
        pb.setLayoutX(bar_x);
        pb.setLayoutY(bar_y);
        pb.setStyle("-fx-accent: green;"); 
        pb.setScaleX(1.8);
        pb.setScaleY(1.5);
        p.getChildren().add(pb);
	}

	@Override
	public void run() {
		while (true){
			
			ArrayList<Object> to_destroy = new ArrayList<Object>();
			
			for (int i = 0;i < p.other_objects.size();i++){
				if (p.other_objects.get(i).collidesWPlayer(this)){
					p.other_objects.get(i).update();
					if (this.curHp > 0)
						this.curHp -= p.other_objects.get(i).dmg;
					else {
						curHp = 0;
					}
						
					if (p.other_objects.get(i).curHp > 0)
						p.other_objects.get(i).curHp -= this.dmg;
					else 
						p.other_objects.get(i).curHp = 0;
					
					if (p.other_objects.get(i).destroy()){
						to_destroy.add(p.other_objects.get(i));
						
					}
					p.other_objects.removeAll(to_destroy);
					
				}
			}
			
			synchronized(p) {
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).update();
					if (p.strely.get(i).destroy()){
						to_destroy.add(p.strely.get(i));
					}
				}
				p.strely.removeAll(to_destroy);
			}
			
			if (p.UP && p.RIGHT){
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).y += movement_speed*0.7;
					p.strely.get(i).x -= movement_speed*0.7;
				}
				for (int i = 0;i < p.other_objects.size();i++){
					p.other_objects.get(i).y += movement_speed*0.7;
					p.other_objects.get(i).x -= movement_speed*0.7;
					p.other_objects.get(i).bar_y += movement_speed*0.7;
					p.other_objects.get(i).bar_x -= movement_speed*0.7;
				}
			}
			else if (p.UP && p.LEFT){
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).y += movement_speed*0.7;
					p.strely.get(i).x += movement_speed*0.7;
				}
				for (int i = 0;i < p.other_objects.size();i++){
					p.other_objects.get(i).y += movement_speed*0.7;
					p.other_objects.get(i).x += movement_speed*0.7;
					p.other_objects.get(i).bar_y += movement_speed*0.7;
					p.other_objects.get(i).bar_x += movement_speed*0.7;
				}
			}
			else if (p.DOWN && p.RIGHT){
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).y -= movement_speed*0.7;
					p.strely.get(i).x -= movement_speed*0.7;
				}
				for (int i = 0;i < p.other_objects.size();i++){
					p.other_objects.get(i).y -= movement_speed*0.7;
					p.other_objects.get(i).x -= movement_speed*0.7;
					p.other_objects.get(i).bar_y -= movement_speed*0.7;
					p.other_objects.get(i).bar_x -= movement_speed*0.7;
					
				}
			}
			else if (p.DOWN && p.LEFT){
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).y -= movement_speed*0.7;
					p.strely.get(i).x += movement_speed*0.7;
				}
				for (int i = 0;i < p.other_objects.size();i++){
					p.other_objects.get(i).y -= movement_speed*0.7;
					p.other_objects.get(i).x += movement_speed*0.7;
					p.other_objects.get(i).bar_y -= movement_speed*0.7;
					p.other_objects.get(i).bar_x += movement_speed*0.7;
				}
			}
			else if (p.UP){
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).y += movement_speed;
				}
				for (int i = 0;i < p.other_objects.size();i++){
					p.other_objects.get(i).y += movement_speed;
					p.other_objects.get(i).bar_y += movement_speed;
				}
			}
			else if (p.DOWN){
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).y -= movement_speed;
				}
				for (int i = 0;i < p.other_objects.size();i++){
					p.other_objects.get(i).y -= movement_speed;
					p.other_objects.get(i).bar_y -= movement_speed;
				}
			}
			else if (p.LEFT){
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).x += movement_speed;
				}
				for (int i = 0;i < p.other_objects.size();i++){
					p.other_objects.get(i).x += movement_speed;
					p.other_objects.get(i).bar_x += movement_speed;
				}
			}
			else if (p.RIGHT){
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).x -= movement_speed;
				}
				for (int i = 0;i < p.other_objects.size();i++){
					p.other_objects.get(i).x -= movement_speed;
					p.other_objects.get(i).bar_x -= movement_speed;
				}
				
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					p.repaint();
					
				}
			});
		}
		
	}
}