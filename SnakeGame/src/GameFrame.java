
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	
	GamePanel game;

	public GameFrame()  //constructor
	{  
		
         //instead of creating an instance of gp, we're using this shortcut.	    
	    this.add(new GamePanel());

	    
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	    
	}

		
	}

