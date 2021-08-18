import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.Graphics;

import java.util.*;


public class GamePanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	int screen_width = 500;
	int screen_height = 500;
	int unit_size = 25;
	int game_units = (screen_width * screen_height) / unit_size;
	int delay = 85;
	
	int x[]=new int[game_units];
	int y[]=new int[game_units];
	
	
	int snake_body=5;
	int food_eaten = 0;  //no food eating in the beginning so 0
	int food_x, food_y; //x and y coords (random)
	
	char direction = 'R'; //snake goes right by default in the start.
	boolean running = false;
	Timer timer;
	Random random;
	
	public GamePanel() //constructor
	{
		
		random = new Random();
		this.setPreferredSize(new Dimension(screen_width, screen_height));
		this.setBackground(Color.white);
		this.setFocusable(true);
		MyKeyAdapter key = new MyKeyAdapter();
		this.addKeyListener(key);
		
		startGame();
		
	}
	
	public void startGame() {
		
			
		newFood(); //creating a new food at the start of game.
		running =true;
		timer = new Timer(delay,this);
		timer.start();
		
		
	}
	
	public void resetGame() {
		food_eaten=0;
		snake_body=5;
		
		
		
		repaint();
		startGame();		
		timer.restart();
		

	}
	
	public void pauseGame() { //pause
		
		if(running=true) {
			
		    running=false;
		    timer.stop();
		}
	}
	
	public void resumeGame() { //resume
		
		    running=true;
		    timer.start();
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) {
	    
	    if(running) {
	    	
	    	/*
		
		    for(int i=0; i<screen_height/unit_size; i++) {
	
			    g.drawLine(i*unit_size, 0, i*unit_size, screen_height);
			    g.drawLine(0, i*unit_size, screen_width, i*unit_size);	
		    }
		    
		    */
		
		    g.setColor(Color.pink);
		    g.fillOval(food_x, food_y, unit_size, unit_size);
		
		    for(int i=0; i<snake_body; i++) {
			    if(i==0) {
				    g.setColor(Color.red);
				    g.fillRect(x[i], y[i], unit_size, unit_size);
			    }
			    else {
				    g.setColor(Color.orange);
				    g.fillRect(x[i], y[i], unit_size, unit_size);

			    }
		  }
		    
		    g.setColor(Color.magenta);
			g.setFont(new Font("Ink Free", Font.BOLD,35));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " +food_eaten, (screen_width-metrics.stringWidth("Score: " +food_eaten))/2, g.getFont().getSize());
			
			g.setColor(Color.green);
			g.setFont(new Font("Sans Serif", Font.ITALIC,20));
			g.drawString("0 - Pause", 380, 450);
			g.drawString("1 - Resume", 380 , 480);
		    
	    }
	    else {
	    	gameOver(g);
	    }
	
	}
	
	public void newFood() {
		
		food_x = random.nextInt((int)(screen_width/unit_size))*unit_size;	
		food_y = random.nextInt((int)(screen_height/unit_size))*unit_size;	

	}
	
	public void move() {
		
		for(int i=snake_body; i>0; i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		
		switch(direction) {
		case 'U' :
			y[0]=y[0]-unit_size;
			break;
			
		case 'D' :
			y[0]=y[0]+unit_size;
			break;
			
		case 'L':
			x[0]=x[0]-unit_size;
			break;
		
		case 'R':
			x[0]=x[0]+unit_size;
			break;
		}
		
	}
	
	public void checkFood() {
		
		if((x[0]==food_x) && (y[0]==food_y)) {
			snake_body++;
			food_eaten++;
			newFood();
		}
		
	}
	
	public void checkCollisions() {
		
		//checks if head collides with body
		for(int i=snake_body; i>0; i--) {
			if((x[0]==x[i] && y[0]==y[i])) {
				running = false;
			}
		}
		
		//checks if head touches left border
		if(x[0]<0) {
			running=false;
		}
		//checks if head touches right border
		if(x[0]>screen_width) {
			running=false;
		}
		//checks if head touches top border
		if
		(y[0]<0) {
			running=false;
		}
		if(y[0]>screen_height) {
			running=false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		
		//score display
		g.setColor(Color.gray);
		g.setFont(new Font("Ink Free", Font.BOLD,35));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Your score is: " +food_eaten, (screen_width - metrics1.stringWidth(" Your score is: " +food_eaten))/2, g.getFont().getSize());
		
		//game over text
		g.setColor(Color.magenta);
		g.setFont(new Font("Ink Free", Font.BOLD,50));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (screen_width-metrics.stringWidth("Game Over!"))/2, screen_height/2);
				
		
				
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkFood();
			checkCollisions();
		}
		repaint();
		
	}
	

	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent ev) {
			
			switch(ev.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction='L';
				}
					break;
					
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction='R';
			    }
				break;
				
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction='U';
			
		        }
				break;
				
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction='D';
	            }
				break;
				
			case KeyEvent.VK_SPACE: //restart
				resetGame();
				break;
				
			case KeyEvent.VK_0: //pause
				pauseGame();
				break;
				
			case KeyEvent.VK_1: //resume
				resumeGame();
				break;
				
			}

}
		
		
		}
	
		
	}
	
