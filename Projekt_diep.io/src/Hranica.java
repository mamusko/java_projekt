import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Hranica extends Object{
	double w;
	double h;
	Color color;
	Rectangle r;
	Plocha p;
	
	public Hranica(Plocha p,double x, double y, double h, double w,Color color){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
		this.p = p;
	}
	
	public void paint(){
		 Rectangle r = new Rectangle(w, h, color);
	     r.setFill(color);
	     r.setX(x);
	     r.setY(y);
	     
	     this.r = r;
	     p.getChildren().add(r);
	     

	}
	
public boolean collidesWPlayer(Hrac hrac){
	    return r.contains(hrac.x,hrac.y);
	}
}
