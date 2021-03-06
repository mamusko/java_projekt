import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rect extends Object{
	double size;
	Color col;
	Plocha p;
	
	public boolean destroy = false;
	
	public Rect(Plocha plocha,double x,double y, Color col, double size){
		this.x = x;
		this.y = y;
		this.col = col;
		this.size = size;
		p = plocha;
		dmg = 2;
		maxHp = 100;
		curHp = maxHp;
		bar_x = x-size -4;
		bar_y = y + size + size*0.5;
	}
	
	public void paint(){
		 Rectangle r = new Rectangle(size, size, col);
	     r.setFill(col);
	     r.setX(x);
	     r.setY(y);
	     p.getChildren().add(r);
	     if (curHp == maxHp){
	    	 return;
	     }
	 
	     ProgressBar pb = new ProgressBar(curHp/maxHp);
	     pb.setLayoutX(bar_x);
	     pb.setLayoutY(bar_y);
	     pb.setStyle("-fx-accent: orange;"); 
	     pb.setScaleX(0.38);
	     pb.setScaleY(0.4);
	     p.getChildren().add(pb);
	     
	     
	     
	     
	}
	
	public void update(){
		
	}
	
	public boolean destroy(){	
		return destroy;
	}
	
	public boolean collidesWPlayer(Hrac hrac){
		
		double distance = Math.sqrt(Math.pow(hrac.x - (x + size/2),2) + Math.pow(hrac.y - (y + size/2),2));
	    if ((distance - size/2) < hrac.r){
	    	return true;
	    } 
	    return false;
	}
	
	public boolean collidesWStrela(Strela strela){
		double distance = Math.sqrt(Math.pow(strela.x - (x + size/2),2) + Math.pow(strela.y - (y + size/2),2));
		if ((distance - size/2) < strela.r){
	    	return true;
	    }
		return false;
	}
	
	public void dmg(double d){	
		curHp -= d;
		if (curHp <= 0){
			curHp = 0;
			destroy = true;
		}
	}
	

}
