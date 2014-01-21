import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends JPanel implements ActionListener{
	public static void main(String a []){
		
		Main m = new Main();
		JFrame j = new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setSize(600, 600);
		j.setVisible(true);
		j.setLocationRelativeTo(null);
		j.setResizable(false);
		j.add(m);
	}
	
	ArrayList<Point> points = new ArrayList<Point>();
	ArrayList<Vector> vectors = new ArrayList<Vector>();
	double x;
	double y;
	double savex;
	double savey;
	Timer t;
	double m;
	double c;
	double xxx;
	double yyy;
	ArrayList<Boolean> detect = new ArrayList<Boolean>();
	double ly1;
	double hy1;
	double ly2;
	double hy2;
	double lx1;
	double hx1;
	double lx2;
	double hx2;
	boolean duplicate = false;
	Font f;
	Random rand;
	int r;
	
	public Main(){
		t = new Timer(5, this);
		t.start();
		setFocusable(true);
		addMouseListener(new ML());
		addMouseMotionListener(new ML());
		points.add(new Point(300, 80));
		points.add(new Point(500, 200));
		points.add(new Point(300, 520));
		points.add(new Point(100, 400));
		points.add(new Point(500, 400));
		points.add(new Point(100, 200));
		f = new Font("Sherif", Font.BOLD, 32);
		rand = new Random();
		createvectors();
		detect();
	}
	
	private class ML implements MouseListener, MouseMotionListener{
		public void mousePressed(MouseEvent e){
			x = e.getX();
			y = e.getY();
			for(int i = 0; i < points.size(); i++){
				if(x >= points.get(i).x - 10 && x <= points.get(i).x + 10 && y >= points.get(i).y - 10 && y <= points.get(i).y + 10){
					points.get(i).s = true;
					savex = points.get(i).x;
					savey = points.get(i).y;
				}
			}
		}
		public void mouseReleased(MouseEvent e){
			x = e.getX();
			y = e.getY();
			for(int i = 0; i < points.size(); i++){
				if(!points.get(i).s){
					if(Math.sqrt((points.get(i).x - x) * (points.get(i).x - x) + (points.get(i).y - y) * (points.get(i).y - y)) < 20
						|| x < 10 || x > 584 || y < 10 || y > 562){
						for(int j = 0; j < points.size(); j++){
							if(points.get(j).s){
								points.get(j).x = savex;
								points.get(j).y = savey;
							}
						}
					}
				}
			}
			for(int i = 0; i < points.size(); i++){
				points.get(i).s = false;
			}
			createvectors();
			detect();
		}
		public void mouseMoved(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseClicked(MouseEvent e){}
		public void mouseDragged(MouseEvent e){
			x = e.getX();
			y = e.getY();
			for(int i = 0; i < points.size(); i++){
				if(points.get(i).s){
					points.get(i).x = x;
					points.get(i).y = y;
				}
			}
		}
	}
	
	public void createvectors(){
		vectors.clear();
		for(int i = 0; i < points.size(); i++){
			for(int j = 0; j < points.size(); j++){
				if(j != i){
					m = (points.get(j).y - points.get(i).y) / (points.get(j).x - points.get(i).x);
					c = points.get(i).y - (m * points.get(i).x);
					duplicate = false;
					if(vectors.size() > 0){
						for(int z = 0; z < vectors.size(); z++){
							if((vectors.get(z).p1 == i && vectors.get(z).p2 == j ) || (vectors.get(z).p1 == j && vectors.get(z).p2 == i)){
								duplicate = true;
							}
						}
						if(!duplicate){
							vectors.add(new Vector(m, c, i,	j));
						}
					}
					else{
						vectors.add(new Vector(m, c, i,	j));
					}
				}
			}
		}
		vectors.remove(3);
		vectors.remove(8);
		vectors.remove(6);
		vectors.remove(2);
	}
	
	public void detect(){
		detect.clear();
		for(int i = 0; i < vectors.size(); i++){
			for(int j = i + 1; j < vectors.size(); j++){
				if(j != i){
					if(points.get(vectors.get(i).p1).x < points.get(vectors.get(i).p2).x){
						lx1 = points.get(vectors.get(i).p1).x;
						hx1 = points.get(vectors.get(i).p2).x;			
					}
					else{
						lx1 = points.get(vectors.get(i).p2).x;
						hx1 = points.get(vectors.get(i).p1).x;
					}
					if(points.get(vectors.get(j).p1).x < points.get(vectors.get(j).p2).x){
						lx2 = points.get(vectors.get(j).p1).x;
						hx2 = points.get(vectors.get(j).p2).x;			
					}
					else{
						lx2 = points.get(vectors.get(j).p2).x;
						hx2 = points.get(vectors.get(j).p1).x;
					}
					if(points.get(vectors.get(i).p1).y < points.get(vectors.get(i).p2).y){
						ly1 = points.get(vectors.get(i).p1).y;
						hy1 = points.get(vectors.get(i).p2).y;			
					}
					else{
						ly1 = points.get(vectors.get(i).p2).y;
						hy1 = points.get(vectors.get(i).p1).y;
					}
					if(points.get(vectors.get(j).p1).y < points.get(vectors.get(j).p2).y){
						ly2 = points.get(vectors.get(j).p1).y;
						hy2 = points.get(vectors.get(j).p2).y;			
					}
					else{
						ly2 = points.get(vectors.get(j).p2).y;
						hy2 = points.get(vectors.get(j).p1).y;
					}
					xxx = -((vectors.get(i).c - vectors.get(j).c) / (vectors.get(i).m - vectors.get(j).m));
					yyy = vectors.get(i).m * xxx + vectors.get(i).c;
					
					if((vectors.get(i).m * xxx + vectors.get(i).c) >= (vectors.get(j).m * xxx + vectors.get(j).c) - 1
						&& (vectors.get(i).m * xxx + vectors.get(i).c) <= (vectors.get(j).m * xxx + vectors.get(j).c) + 1
								&& xxx > lx1 + .1 && xxx < hx1 - .1 && xxx > lx2 + .1 && xxx < hx2 - .1
								&& yyy > ly1 + .1 && yyy < hy1 - .1 && yyy > ly2 + .1 && yyy < hy2 - .1){
							detect.add(true);
					}
					else{
						detect.add(false);
					}
				}
			}
		}	
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawString("Untangle the string so none of the lines are crossing", 20, 20);
		for(int i = 0; i < vectors.size(); i++){
			g.drawLine((int)points.get(vectors.get(i).p1).x, (int)points.get(vectors.get(i).p1).y, (int)points.get(vectors.get(i).p2).x, (int)points.get(vectors.get(i).p2).y);
		}
		for(int i = 0; i < points.size(); i++){
			g.fillOval((int)points.get(i).x - 10, (int)points.get(i).y - 10, 20, 20);
		}
		for(int i = 0; i < detect.size(); i ++){
			if(i == detect.size() - 1){
				g.setFont(f);
				g.drawString("WELL DONE!" , 200, 300);
				break;
			}
			if(detect.get(i)){
				break;
			}
		}
	}
}
