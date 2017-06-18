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
	double h = 50;
	double w = 20;
	double r = 30;
	double angle;
	Plocha p;
	Panel panel;
	double curHp;
	double xpToNextLevel = 1000;
	
	int upgrades = 0;
	int attack_speed = 200;
	double bullet_speeed;
	double movement_speed = 5;
	double dmg;
	double maxHp = 500;
	double HpRegen = 0.5;
	double xp = 0;
	

	public Hrac(Plocha plocha){
		x = 400;
		y = 320;
		dmg = 150;
		curHp = maxHp;
		bar_x = 345;
		bar_y = 610;
		angle = 0;
		p = plocha;
		panel = new Panel();

		
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
        delo.setStroke(Color.BLACK);
        delo.getTransforms().addAll(rotate); 
        
        Circle c = new Circle(x,y,r);
        c.setFill(Color.BLUEVIOLET);
        c.setStroke(Color.BLACK);
        p.getChildren().add(c);
        
        ProgressBar pb = new ProgressBar(curHp/maxHp);
        pb.setLayoutX(bar_x);
        pb.setLayoutY(bar_y);
        if (curHp > maxHp/2)
        	pb.setStyle("-fx-accent: green;"); 
        else if (curHp < maxHp/8)
        	pb.setStyle("-fx-accent: red;"); 
        else
        	pb.setStyle("-fx-accent: orange;"); 
        pb.setScaleX(1.8);
        pb.setScaleY(1.5);
        p.getChildren().add(pb);
        
        ProgressBar pb2 = new ProgressBar(xp/xpToNextLevel);
        pb2.setLayoutX(bar_x);
        pb2.setLayoutY(bar_y-30);
        pb2.setStyle("-fx-accent: blue;"); 
        pb2.setScaleX(1.8);
        pb2.setScaleY(1.5);
        p.getChildren().add(pb2);
        
        panel.paint(p);
	}
	
	public void dmg(double d){
		if (curHp > 0){
			curHp -= d;
		}
		else {
			curHp = 0;
		}
	}

	public void addXp(double pocet){
		xp += pocet;
		
		if (xp > xpToNextLevel){
			xp = xp - xpToNextLevel;
			xpToNextLevel = xpToNextLevel *1.2;
			upgrades++;
			
		}
	}
	@Override
	public void run() {
		while (true){
			
			try {
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
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
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