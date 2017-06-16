import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Plocha extends Pane implements Runnable{
	public Hrac hrac;
	public Strela strela;
	public ArrayList<Object> other_objects = new ArrayList<Object>();
	public ArrayList<Strela> strely = new ArrayList<Strela>();
	private boolean drag = false;
	private boolean klik = false;
	public Timer timer;
	public Timer timer_klik;
	double event_x;
	double event_y;
	Plocha p = this;
	public boolean UP = false;
	public boolean DOWN = false;
	public boolean RIGHT = false;
	public boolean LEFT = false;
	
	public Plocha(){
		hrac = new Hrac(this);
		Thread t1 = new Thread(hrac);
		Rect r = new Rect(this,300,200,Color.BLUE,30);
		other_objects.add(r);
        t1.start();  
        timer = new Timer();
        
        
        setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)){
				UP = true;
			}
			if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)){
				DOWN = true;
			}
			if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)){
				LEFT = true;
			}
			if (event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)){
				RIGHT = true;
			}
		});
		
		setOnKeyReleased(event -> {
			if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)){
				UP = false;
			}
			if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)){
				DOWN = false;
			}
			if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)){
				LEFT = false;
			}
			if (event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)){
				RIGHT = false;
			}
		});
		
		setOnMousePressed(event -> {	
			this.event_x = event.getX();
        	this.event_y = event.getY();
        	hrac.angle = Math.atan2(hrac.y - event.getY(), hrac.x - event.getX()) - Math.PI / 2;
        	if (!drag && !klik)
        	{		
        		resume();
        		resume_klik();
        	}
		});
		
		setOnMouseReleased(event -> {
				pause();
		});
        
        setOnMouseMoved(event -> {
        	hrac.angle = Math.atan2(hrac.y - event.getY(), hrac.x - event.getX()) - Math.PI / 2;
        });
        
        setOnMouseDragged(event -> {
        	this.event_x = event.getX();
        	this.event_y = event.getY();
        	hrac.angle = Math.atan2(hrac.y - event.getY(), hrac.x - event.getX()) - Math.PI / 2;
        	if (!drag && !klik)
        	{		
        		resume();
        		resume_klik();
        	}
        });
        
        setOnMouseDragReleased(event -> {
        	pause();
        });
	}
	
	public void generateObjects(){
	}
	
	public void repaint() {
		clearScreen();
		
		for (int i = 0;i < p.other_objects.size();i++){
			p.other_objects.get(i).paint();
		}
		for (int i = 0;i < p.strely.size();i++){
			p.strely.get(i).paint();
		}
		hrac.paint();
	}
	
	public void clearScreen(){
		getChildren().clear();
		Rectangle b= new Rectangle(3200, 2560, Color.BROWN);
		b.setX(-800);
		b.setY(-640);
		getChildren().add(b);
		Rectangle box = new Rectangle(800, 640, Color.WHITE);
		box.setX(0);
		box.setY(0);
		getChildren().add(box);
	}
	
	public void pause() {
	    this.timer.cancel();
	    drag = false;
	}

	
	public void resume_klik(){
		timer_klik = new Timer();
		klik = true;
		timer_klik.schedule(new TimerTask() {
		       @Override
		       public void run() {
		    	   klik = false;
		    	   this.cancel();
		       }
		}, hrac.attack_speed, hrac.attack_speed);

	}
	public void resume() {
	    timer = new Timer();
	    drag = true;
	    
	    timer.schedule(new TimerTask() {
		       @Override
		       public void run() {
		    	   Strela strela = new Strela(p,event_x,event_y);
		    	   p.strely.add(strela);
		       }
		}, 0, hrac.attack_speed);
	    
	}

	@Override
	public void run() {
		while (true){
			ArrayList<Object> to_destroy = new ArrayList<Object>();
			for (int i = 0;i < p.other_objects.size();i++){
				if (p.other_objects.get(i).collidesWPlayer(hrac)){
					hrac.dmg(p.other_objects.get(i).dmg);
					p.other_objects.get(i).dmg(hrac.dmg);
					if (p.other_objects.get(i).destroy()){
						to_destroy.add(p.other_objects.get(i));
					}
				}
				for (int j = 0;j < p.strely.size();j++){
					if (p.other_objects.get(i).collidesWStrela(p.strely.get(j))){
						p.other_objects.get(i).dmg(hrac.dmg);
						if (p.other_objects.get(i).destroy()){
							to_destroy.add(p.other_objects.get(i));
						}
						to_destroy.add(p.strely.get(i));
					}
				}
			}
			p.other_objects.removeAll(to_destroy);
			synchronized(p) {
				for (int i = 0;i < p.strely.size();i++){
					p.strely.get(i).update();
					if (p.strely.get(i).destroy()){
						to_destroy.add(p.strely.get(i));
					}
				}
				p.strely.removeAll(to_destroy);
			}
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
