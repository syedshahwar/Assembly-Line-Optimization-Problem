import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class AssemblyLinesGUI extends JFrame {
  
	 int[] path;			//optimal path from entry to exit
	 int n;  				//number of stations on single line
	 int cost;				//total optimal cost
	 int[][] a;				//time spent on stations
	 int[][] t;				//switching times
	 int[] e;				//entry times
	 int[] x;				//exit times
	
	// Name-constants for the various dimensions
   public static  int CANVAS_WIDTH ;
   public static  int CANVAS_HEIGHT;
  
   private DrawCanvas canvas; // the custom drawing canvas (extends JPanel)
   private Sprite sprite;     // the moving object
   private static Timer timer;
   int count=0;
  
   int savedX = 0;			//to keep record of previous x-axis value while updating sprite 
   int savedY = 0;			//to keep record of previous y-axis value while updating sprite 
   
   
   /** Constructor to set up the GUI */
   public AssemblyLinesGUI(int[] shortPath, int stations, int cost, int [][]sTimes,int[][] swT,int[] enT ,int[] exT) {
      // Construct a sprite given x, y, width, height, color
	 
	  n=stations;
	  
	  a = new int [2][n];
	  t = new int [2][n];
	  e = new int[2];
	  x = new int[2];
	  
	  for(int i=0; i<2; i++)
	  {
		  for(int j=0; j<n;j++)
		  {
			  a[i][j]=sTimes[i][j];
			  t[i][j]=swT[i][j];
		  }
		  e[i] = enT[i];
		  x[i] = exT[i];
	  }
	  
	  
	  
	  path = new int[n];
	  this.cost=cost;
	  
      for(int i=0;i<n;i++)
      {
    	  path[i] = shortPath[i];
      }
      
      CANVAS_WIDTH=400;
      CANVAS_HEIGHT=190+(n*100);
      
      
      sprite = new Sprite(185,65,25, 40, Color.RED);
      

      // Set up the custom drawing canvas (JPanel)
      canvas = new DrawCanvas();
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
 
      
      // Add both panels to this JFrame
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(canvas, BorderLayout.CENTER);
      JScrollPane jscrollpane=new JScrollPane(canvas,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      cp.add(jscrollpane); 
      setVisible(true);
      setSize(600,600);
      timer = new Timer();
      
      timer.schedule(new Move(), 3000,3000);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("Assembly Line Problem");
      pack();            // pack all the components in the JFrame
      setVisible(true);  // show it
      requestFocus();    // "this" JFrame requests focus to receive KeyEvent
   }
   
   class Move extends TimerTask{
		 public void run() 
		 { 
			   count++; 
			   if(count<n+1)
			   {
				   if(path[count-1]==1){
					   Move1(count-1);  
				   }
				   else{
					   Move2(count-1);
				   } 
				  
			   }
			   
			   if(count==n+1)
			   {
				   MoveF(); 
			   }
		       
		       if(count>=n+2)   
		       {
		    	   timer.cancel();
		       }
			 
		  }
	}
 
   // Helper method to move to line 1 
   private void Move1(int counter) 
   {
	      // Save the current dimensions for repaint to erase the sprite
	     savedX = sprite.x;
	     savedY = sprite.y;
	      // update sprite
	      
	      sprite.x=70;
	      
	      if (counter==0)
	      {
	    	  sprite.y=savedY+50;
	      }
	      else
	      {
	    	  sprite.y=savedY+100;
	      }
	      
	      // Repaint only the affected areas, not the entire JFrame, for efficiency
	      canvas.repaint(savedX , savedY, sprite.width, sprite.height); // Clear old area to background
	      canvas.repaint(sprite.x, sprite.y, sprite.width, sprite.height); // Paint new location
   	}
 
   // Helper method to move to line 2 
   private void Move2(int counter) 
   {
	      // Save the current dimensions for repaint to erase the sprite
	     savedX = sprite.x;
	     savedY = sprite.y;
	      // update sprite
	      
	      sprite.x=300;
	      
	      if (counter==0)
	      {
	    	  sprite.y=savedY+50;
	      }
	      else
	      {
	    	  sprite.y=savedY+100;
	      }
	      
	      // Repaint only the affected areas, not the entire JFrame, for efficiency
	      canvas.repaint(savedX , savedY, sprite.width, sprite.height); // Clear old area to background
	      canvas.repaint(sprite.x, sprite.y, sprite.width, sprite.height); // Paint new location
	      
   }
   
// Helper method to move to exit
   private void MoveF() 
   {
	   savedX = sprite.x;
	   savedY = sprite.y;
	   	  
	   sprite.x=185;
	   sprite.y=CANVAS_HEIGHT-110;
	   	  
	   canvas.repaint(savedX ,savedY, sprite.width, sprite.height); // Clear old area to background
	   canvas.repaint(sprite.x, sprite.y, sprite.width, sprite.height); // Paint new location
	 
   	}
 
   /** DrawCanvas (inner class) is a JPanel used for custom drawing */
   class DrawCanvas extends JPanel {
     
      public void paintComponent(Graphics g) 
      {
         super.paintComponent(g);

         g.setColor(Color.darkGray);
         g.setFont(new Font("Monospaced", Font.BOLD, 20));
         g.drawString("Entry", 95, 30);
         g.drawString("Exit", 105 , CANVAS_HEIGHT-20);
         g.drawString("Line 1", 5, 85);
         g.drawString("Line 2", 320, 85);
         g.drawString("Cost: "+cost, 285 , CANVAS_HEIGHT-20);		//writing optimal cost
        
    	 g.fillRect(160, 10, 75, 50);				//Entry Station
  	    
    	 // drawing stations
  	     for(int i=1; i<n+1; i++)
  	     {
  	    	 
  	     	 g.fillRect(15, i*100, 50, 75);					//Line 1 stations
  	    	 g.fillRect(330, i*100, 50, 75);				//Line 2 stations
  	    	 
  	     }
  	     
  	     g.fillRect(160, CANVAS_HEIGHT-60, 75, 50);    //Exit Station 
  	     
  	     g.setColor(Color.white);
  	     
  	     //writing entry and exit times on corresponding sides
	     for(int i=0; i<2; i++)
	     {
	  	     g.drawString(""+e[i], 165 +i*55, 50);
	  	     g.drawString(""+x[i], 165 +i*55, CANVAS_HEIGHT-35);  
	     }
  	     
  	     //writing cost on stations on corresponding stations
  	     for(int i=0; i<n; i++)
	     {
	  	     g.drawString(""+a[0][i], 35, 140+i*100);
	  	     g.drawString(""+a[1][i], 350,140+i*100);
	  	     
	     }
  	     
  	  	 g.setColor(Color.black);
  	     // writing switch times
  	      
  	     for(int i=0; i<n-1; i++)
	     {
	  	     g.drawString(""+t[0][i], 80, 180+i*100);
	  	     g.drawString(""+t[1][i], 300,180+i*100);
	  	     
	     }
  	     
         sprite.paint(g); 								 // the sprite paints itself
         
      }
   }
 
   /** The entry main() method */
   public static void main(String[] args) 
   {
	   // final int sTimes[][]={{5,6,5,7,4,3,3,6,7,2},{3,2,8,2,6,4,9,8,3,4}}; 
	   // final int sTimes[][]={{1,2,3,4,5},{5,4,3,2,1}}; 
	   			
	   final int sTimes[][]={{5,6,3,2,4},{3,2,8,2,6,}}; 			 // Time spent at single station
	   final int swT[][] = {{1,1,1,1,1},{1,1,1,1,1}};   							//time to switch between assembly lines 
	   
	   // final int swT[][] = {{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1}};
	   
	   final int enT[] = {2,2}; 													// entry time of chasis in a line 1 and 2 respectively
	   final int exT[] = {2,2};														// exit time of complete car from line 1 and 2 respectively
	  
	   final int stations =5;														// total number stations in single line 
			
	   AssemblyLines al= new AssemblyLines(sTimes,swT,enT,exT,stations);
			
	   final int shortPath[];
	   final int cost;
	   shortPath= al.AssemblyLineOptimization();
	   cost=al.f;
	   
	   System.out.print("Optimal Path : ");
	   for(int i=0; i<stations; i++)
	   {
		   System.out.print(shortPath[i]+" ");
	   }
			
	   //al.Display_Inputs(); 
	   
	   
      // Run GUI construction on the Event-Dispatching Thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new AssemblyLinesGUI(shortPath,stations,cost,sTimes,swT,enT,exT); // Let the constructor do the job
         }
      });
   }
}