import javax.swing.JFrame;

public class App{
    public static void main(String[] args) throws Exception{
        //definir taille de la fenetre
    int rowCount =21;
    int columnCount =19;
    int tileSize = 32;
    int boardWidth =columnCount * tileSize;
    int boardHeight =rowCount * tileSize;
    //creation fenetre avec proprietes
    
    JFrame frame = new JFrame("Pac-Man");
    
  

    frame.setSize(608,672);
    frame.setResizable(false);
   
    frame.setLocationRelativeTo(null);

    // ferme application quand la fenetre est fermee
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    PacMan pacManGame = new PacMan(); //Etape 3
    frame.add(pacManGame);  //Etape 3
    frame.setVisible(true);
    pacManGame.requestFocus();  //etape 11
    }
}