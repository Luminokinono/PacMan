import java.awt.*;

import java.awt.event.*;
import java.util.*;
//import java.util.Timer;

import javax.swing.Timer;
import javax.swing.*;




//Etape 3
public class PacMan extends JPanel implements ActionListener, KeyListener {
       //Etape 7
    HashSet<Block> walls;
    HashSet<Block> foods; 
    HashSet<Block> ghosts;
    Block pacman;


    


    int rowCount =21;
    int columnCount =19;


    //etape 15

    char [] directions ={'U','D','L','R'};

    Random random = new Random();

    //Etape 3
    private int tileSize = 32;
    private int boardWidth =19 * tileSize;
    private int boardHeight =21 * tileSize;

    //images fantomes Etape 4
    private Image wallImage;
    private Image blueGhostImage;
    private Image pinkGhostImage;
    private Image redGhostImage;
    private Image orangeGhostImage;
    //images Pac-man Etape 4
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    //Etape 7
    private String[] tileMap ={
        "XXXXXXXXXXXXXX XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "XOOX X       X XOOX",
        "XXXX X XXrXX X XXXX",
        "        bpo         ",
        "XXXX X XXXXX X XXXX",
        "XOOX X       X XOOX",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X    PM     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXX XX X",
        "X                 X",
        "XXXXXXXXXXXXXX XXXX"
    };

    public class Block {    // Etape 6
    int x;
    int y;
    int width;
    int height;
    Image image;

    int startX;
    int startY;

    //etape 12
    char direction = 'U';  // 'U' pour Up, 'D' pour Down, 'L' pour Left, 'R' pour Right
    int velocityX = 0;
    int velocityY = 0;

    


    public Block(Image image, int x, int y, int width, int height){
        this.image =image;
        this.x =x;
        this.y =y;
        this.width =width;
        this.height =height;
        this.startX =x;
        this.startY =y;
    }
    
    // Etape 12
    public void updateVelocity() {
    int speed = tileSize / 4; // Par exemple 8 pixels par frame si tileSize = 32

    switch (this.direction) {
        case 'U': { this.velocityX = 0; this.velocityY = -speed; break; }
        case 'D': { this.velocityX = 0; this.velocityY = speed; break; }
        case 'L': { this.velocityX = -speed; this.velocityY = 0; break; }
        case 'R': { this.velocityX = speed; this.velocityY = 0; break; }
    }
}

    //etape 12 + 14


    void updateDirection(char direction) {
   // Sauvegarder la direction annuelle
    char prevDirection = this.direction;


// Tenter de changer de direction
    this.direction = direction;
    updateVelocity(); //Appliquer la vélocité liée à  la nouvelle direction




// Simuler un petit déplacement
    this.x += this.velocityX;
    this.y += this.velocityY;


    // Vérifier la collision avec les murs
        for (Block wall : walls) {
            if (collision(this, wall)) {
// Si collision détectée, revenir en arrière
                this.x -= this.velocityX;
                this.y -= this.velocityY;


// Restaurer la direction précédente
                 this.direction = prevDirection;
                updateVelocity(); //Restaurer la vélocité initiale
                break;
            }
        }
    
}


}


    



    public void loadMap(){                  //Etape 8
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for(int r=0;r< rowCount; r++){
            for(int c=0; c< columnCount;c++){
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x= c*tileSize;
                int y= r*tileSize;


                if (tileMapChar == 'X') { // block wall
                    Block wall = new Block(wallImage, x, y, tileSize, tileSize);
                    walls.add(wall);
                } else if (tileMapChar == 'b') { // blue ghost
                    Block ghost = new Block(blueGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'o') { // orange ghost
                    Block ghost = new Block(orangeGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'p') { // pink ghost
                    Block ghost = new Block(pinkGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'r') { // red ghost
                    Block ghost = new Block(redGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'P') { // pacman
                    pacman = new Block(pacmanRightImage, x, y, tileSize, tileSize);
                } else if (tileMapChar == ' ') { // food
                    Block food = new Block(null, x + 14, y + 14, 4, 4);
                    foods.add(food);
                }
            }
        }
    }

    

    public void draw(Graphics g) {
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        for (Block ghost : ghosts){
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }
        for (Block wall : walls){
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }
        g.setColor(Color.WHITE);
        for(Block food : foods){
            g.fillRect(food.x, food.y, food.width, food.height);
        }
    }

    public PacMan(){ //Etape 3
        
        //Etape 3
    setPreferredSize(new Dimension (boardWidth, boardHeight));
    setBackground( Color.BLACK);

    //images chargées //Etape 4
    wallImage = new ImageIcon(getClass().getResource("./images/wall.png")).getImage();
    blueGhostImage = new ImageIcon(getClass().getResource("./images/blueGhost.png")).getImage();
    orangeGhostImage = new ImageIcon(getClass().getResource("./images/orangeGhost.png")).getImage();
    pinkGhostImage = new ImageIcon(getClass().getResource("./images/pinkGhost.png")).getImage();
    redGhostImage = new ImageIcon(getClass().getResource("./images/redGhost.png")).getImage();

    pacmanUpImage = new ImageIcon(getClass().getResource("./images/pacmanUp.png")).getImage();
    pacmanDownImage = new ImageIcon(getClass().getResource("./images/pacmanDown.png")).getImage();
    pacmanLeftImage = new ImageIcon(getClass().getResource("./images/pacmanLeft.png")).getImage();
    pacmanRightImage = new ImageIcon(getClass().getResource("./images/pacmanRight.png")).getImage();
    
    loadMap ();

    addKeyListener(this);   //etape 11
    setFocusable(true);

    //etape 10
    Timer gameLoop = new Timer(50, this); //appelle actionPerformed toutes les 50 ms

    gameLoop.start(); //demarre la boucle de jeu



    }

@Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.updateDirection('U');
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.updateDirection('D');
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.updateDirection('L');
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.updateDirection('R');
        }

        if (pacman.direction == 'U') {
            pacman.image = pacmanUpImage;
        }
        else if (pacman.direction == 'D') {
            pacman.image = pacmanDownImage;
        }
        else if (pacman.direction == 'L') {
            pacman.image = pacmanLeftImage;
        }
        else if (pacman.direction == 'R') {
            pacman.image = pacmanRightImage;
        }
    }


        @Override
    public void actionPerformed(ActionEvent e){
        repaint(); //declenche paintComponent met a jour l'affichage
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }


    

    //etape 13
    public boolean collision(Block a, Block b) {
    return a.x < b.x + b.width &&
           a.x + a.width > b.x &&
           a.y < b.y + b.height &&
           a.y + a.height > b.y;
}

    //etape 12 + 13
    public void move() {
        // Mise à jour de la position horizontale (axe X)
    // Ajoute velocityX à la position actuelle,
    // puis applique un modulo pour revenir au début si on dépasse boardWidth
        int oldX = pacman.x;
        int oldY = pacman.y;


    // Met à jour de la position avec vélocité

    // Mise à jour de la position horizontale (axe X)
    // Ajoute velocityX à la position actuelle,
    // puis applique un modulo pour revenir au début si on dépasse boardWidth
        pacman.x = (pacman.x + pacman.velocityX + boardWidth) % boardWidth;
        // Mise à jour de la position verticale (axe Y)
    // Même principe : ajoute velocityY, puis applique le modulo avec boardHeight
        pacman.y = (pacman.y + pacman.velocityY + boardHeight) % boardHeight;


    // Vérifie les collisions avec tous les murs
        for (Block wall : walls) {
            if (collision(pacman, wall)) {
            // Annuler le déplacement si collision détectée
                pacman.x = oldX;
                pacman.y = oldY;
                break;
            }
        }
    }

    //etape 15
    

}