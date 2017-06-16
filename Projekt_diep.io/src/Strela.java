import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class Strela extends Object implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double target_x;
	double target_y;
	double speed;
	double angle;
	double r = 10;
	double dx;
	double dy;
	double distance;
	int lifetime = 0;
	int max_lifetime = 250;
	Plocha p;
	

	public Strela(Plocha plocha,double clicked_x,double clicked_y){
		p = plocha;
		dmg = 10;
		target_x = clicked_x;
		target_y = clicked_y;
		angle = Math.atan2(p.hrac.x - clicked_x, p.hrac.y - clicked_y) - Math.PI / 2;
		angle = Math.toDegrees(angle);
		angle -= 90;
		angle = Math.toRadians(angle);
		x = p.hrac.x + p.hrac.h * Math.sin(angle);
		y = p.hrac.y + p.hrac.h * Math.cos(angle);
		
		dx = target_x - x;
		dy = target_y - y;
		distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}
	
	public void update(){
		
		
		double vx = 100 * dx / distance;
		double vy = 100 * dy / distance;
		x = x +  vx/100;
		y = y +  vy/100;
		lifetime++;
		
		
	}
	
	public boolean destroy(){
		if (lifetime > max_lifetime){
			return true;
		}
		
		return false;
	}
	
	public void paint(){
		 Circle c = new Circle(x,y,r);
	     c.setFill(Color.ORANGERED);
	     p.getChildren().add(c);
	}
	
	@Override
	public boolean collidesWPlayer(Hrac hrac) {
		return super.collidesWPlayer(hrac);
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
