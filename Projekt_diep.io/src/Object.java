import java.io.Serializable;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.event.EventHandler;

public abstract class Object implements Serializable {

	public double x;
	public double y;
	public double dmg;
	public double curHp;
	public double bar_x;
	public double bar_y;
	private static final long serialVersionUID = -5579367124701664959L;
	
	public void paint(){
		throw new java.lang.UnsupportedOperationException("Not implemented.");
	}
	
	public void update(){
		throw new java.lang.UnsupportedOperationException("Not implemented.");
	}
	
	public boolean destroy(){
		throw new java.lang.UnsupportedOperationException("Not implemented.");
	}
	
	public boolean collidesWPlayer(Hrac hrac){
		return false;
	}

}


