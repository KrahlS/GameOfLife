import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class implements all of the methods required to simulate Conway's Game of Life, a cellular
 * automaton, in the console.
 * 
 * @author Scott
 *
 */
public class GameOfLife {

  static GameOfLife currentGame; // Current game being displayed
  int height; // Height of the board
  int width; // Width of the board
  int[][] board; // 2D int array representing the board's current state

  /**
   * This method serves as a constructor for a new simulation given a specified height and width.
   * 
   * @param height Desired height of board
   * @param width  Desired width of board
   */
  public GameOfLife(int height, int width) {
    this.board = new int[height][width];
    this.height = height;
    this.width = width;
  }

  /**
   * This method serves as a constructor for a new simulation given a specified board preset.
   * 
   * @param boardPreset A preset board template
   */
  public GameOfLife(int[][] boardPreset) {
    this.board = boardPreset;
    this.height = boardPreset.length;
    this.width = boardPreset[0].length;
  }

  /**
   * This method serves to prompt the user for a height and width, then generate a random board
   * state. Returns a new GameOfLife object representing the new simulation.
   * 
   * @return GameOfLife randomBoard containing the new current simulation
   */
  public static GameOfLife generateRandomBoard() {
    int height;
    int width;
    Scanner in = new Scanner(System.in);

    System.out.println("Enter board height:");
    height = in.nextInt();
    System.out.println("Enter board width:");
    width = in.nextInt();

    GameOfLife randomBoard = new GameOfLife(height, width);
    randomBoard.fillRandomState();
    return randomBoard;
  }

  /**
   * This method serves to fill the current simulation's board with all 0's (dead states)
   */
  public void fillDeadState() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.board[i][j] = 0;
      }
    }
  }

  /**
   * This method serves to fill the current simulation's board with a random selection of 0's (dead
   * cells) and 1's (alive cells).
   */
  public void fillRandomState() {
    Random rand = new Random();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        this.board[y][x] = rand.nextInt(2);
      }
    }
  }

  /**
   * This method serves to count all of the alive cells adjacent to a specified (x,y) coordinate.
   * 
   * @param x Specified x coordinate
   * @param y Specified y coordinate
   * @return numAlive count of all alive neighbors
   */
  public int countAliveNeighbors(int x, int y) {
    int numAlive = 0;

    numAlive += checkStatus(x - 1, y - 1);
    numAlive += checkStatus(x, y - 1);
    numAlive += checkStatus(x + 1, y - 1);

    numAlive += checkStatus(x - 1, y);
    numAlive += checkStatus(x + 1, y);

    numAlive += checkStatus(x - 1, y + 1);
    numAlive += checkStatus(x, y + 1);
    numAlive += checkStatus(x + 1, y + 1);

    return numAlive;
  }

  /**
   * This method serves to handle the edge cases of cells on the borders of the board to prevent
   * Array Index Out Of Bounds exceptions. Given a specific (x,y) coordinate, this method checks if
   * it is on the edge of the board.
   * 
   * @param x Specified x coordinate
   * @param y Specified y coordinate
   * @return 0 if cell is on the edge, else the true value of the cell
   */
  public int checkStatus(int x, int y) {
    if (x >= width || x < 0) {
      return 0;
    }
    if (y >= height || y < 0) {
      return 0;
    }
    return this.board[y][x];
  }

  /**
   * This method serves to calculate the next state of the board. First the method makes a temporary
   * board, then iterates through the current board, counting all the alive neighbors of each cell.
   * The method then applies the 3 rules of Conway's Game of Life to assign new values to the
   * temporary board. The current game is then assigned to the reference to the temporary board.
   */
  public void nextState() {
    int[][] tempBoard = new int[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int numAliveNeighbors = countAliveNeighbors(x, y);
        if (this.board[y][x] == 1) {
          if (numAliveNeighbors < 2) {
            tempBoard[y][x] = 0;
          } else if (numAliveNeighbors == 2 || numAliveNeighbors == 3) {
            tempBoard[y][x] = 1;
          } else {
            tempBoard[y][x] = 0;
          }
        } else {
          if (numAliveNeighbors == 3) {
            tempBoard[y][x] = 1;
          }
        }
      }
    }
    this.board = tempBoard;
  }

  /**
   * This method serves to provide the user 3 ways to generate a board: generate from a template
   * given by the CONFIG file, generate a custom board from the customBoard.txt file, or generate a
   * random board.
   * 
   * @param in Scanner for user input
   */
  public static void generateBoard(Scanner in) {
    printBoardMenu();
    char userIn = in.nextLine().charAt(0);

    switch (userIn) {
      case '1': // Generate from a template
        printTemplateMenu();
        userIn = in.nextLine().toUpperCase().charAt(0);
        switch (userIn) {
          case 'A': // Beehive
            currentGame = new GameOfLife(Config.BEEHIVE);
            System.out.println("You chose: Beehive");
            System.out.println("Returning to main menu...");
            break;
          case 'B': // Toad
            currentGame = new GameOfLife(Config.TOAD);
            System.out.println("You chose: Toad");
            System.out.println("Returning to main menu...");
            break;
          case 'C': // Glider
            currentGame = new GameOfLife(Config.GLIDER);
            System.out.println("You chose: Glider");
            System.out.println("Returning to main menu...");
            break;
          default:
            System.out.println("Invalid input!");
            System.out.println("Returning to main menu...");
            break;
        }
        break;
      case '2': // Generate a custom board from txt
        try {
          String boardTxt = "";
          File txtBoard = new File("customBoard.txt");
          Scanner scnr = new Scanner(txtBoard);

          int width = scnr.nextLine().length();
          int height = 0;
          scnr.close();

          scnr = new Scanner(txtBoard);

          while (scnr.hasNextLine()) {
            boardTxt += scnr.nextLine() + "\n";
            height++;
          }
          scnr.close();

          scnr = new Scanner(boardTxt);
          currentGame = new GameOfLife(height, width);

          for (int y = 0; y < height; y++) {
            String currentLine = scnr.nextLine();
            if (currentLine.length() != width) {
              scnr.close();
              throw new IndexOutOfBoundsException();
            }
            for (int x = 0; x < width; x++) {
              if (currentLine.charAt(x) == 'O') {
                currentGame.board[y][x] = 1;
              } else {
                currentGame.board[y][x] = 0;
              }

            }
          }
          scnr.close();
        } catch (FileNotFoundException e) {
          System.out.println("File not found!");
          System.out.println("Returning to main menu...");
        } catch (IndexOutOfBoundsException e) {
          System.out.println("Index out of bounds! Make sure the custom board is rectangular!");
          System.out.println("Returning to main menu...");
        }
        break;
      case '3': // Generate a random board
        currentGame = generateRandomBoard();
        System.out.println("Generated a random board");
        System.out.println("Returning to main menu...");
        break;
      default:
        System.out.println("Invalid input!");
        System.out.println("Returning to main menu...");
        break;
    }
  }

  /**
   * This method serves to print the main menu.
   */
  public static void printMainMenu() {
    System.out.println("\nWelcome to Conway's Game of Life!\n======================");
    System.out.println("A) Generate board");
    System.out.println("B) Render board");
    System.out.println("C) Step simulation by 1 frame");
    System.out.println("D) Quit");
    System.out.print("> ");
  }

  /**
   * This method serves to print the menu for generating a board.
   */
  public static void printBoardMenu() {
    System.out.println("\nGenerate A Board\n======================");
    System.out.println("1) Generate from a template");
    System.out.println("2) Generate a custom board from text");
    System.out.println("3) Generate a random board");
    System.out.print("> ");
  }

  /**
   * This method serves to print the menu for generating from a template.
   */
  public static void printTemplateMenu() {
    System.out.println("\nGenerate Board From Template\n======================");
    System.out.println("A) Beehive (Still Life)");
    System.out.println("B) Toad (Simple Oscillator)");
    System.out.println("C) Glider (Simple Spaceship)");
    System.out.print("> ");
  }

  /**
   * This method serves to print the current board state, automatically adjusting border size based
   * on the simulation's given width and height parameters.
   */
  public void render() {
    System.out.print(" ");
    for (int y = 0; y < width; y++) {
      System.out.print("---");
    }
    System.out.println();

    for (int y = 0; y < height; y++) {
      System.out.print("|");
      for (int x = 0; x < width; x++) {
        if (this.board[y][x] == 1) {
          System.out.print(" O ");
        } else {
          System.out.print(" - ");
        }
      }
      System.out.println("|");
    }
    System.out.print(" ");

    for (int y = 0; y < width; y++) {
      System.out.print("---");
    }
    System.out.println();
  }

  /**
   * This is the main method. It serves to set up, run, and exit the simulation.
   * 
   * @param args
   */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    char userIn = '\0';
    while (true) {
      try {
        // TOP MENU
        printMainMenu();
        userIn = in.nextLine().toUpperCase().charAt(0);
        switch (userIn) {
          case 'A': // Generate a board
            generateBoard(in);
            break;
          case 'B': // Render board
            if (currentGame == null) {
              System.out.println("No active simulation!");
              System.out.println("Returning to main menu...\n");
              break;
            }
            currentGame.render();
            break;
          case 'C': // Step by one frame
            if (currentGame == null) {
              System.out.println("No active simulation!");
              System.out.println("Returning to main menu...\n");
              break;
            }
            currentGame.nextState();
            currentGame.render();
            break;
          case 'D': // Quit
            System.out.println("Goodbye!");
            in.close();
            System.exit(0);
          default:
            System.out.println("Invalid input!\n");
            break;
        }
      } catch (Exception e) {
        System.out.println("An error occured!");
        System.out.println("Returning to main menu...");
      }
    }
  }
}
