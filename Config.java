/**
 * This class contains three preset board templates for use in the GameOfLife class file.
 * 
 * @author Scott
 *
 */
public class Config {
  
  public static final int[][] TOAD = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 1, 1, 1, 0},
      {0, 1, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
  
  public static final int[][] BEEHIVE = {{0, 0, 0, 0, 0, 0}, {0, 0, 1, 1, 0, 0}, {0, 1, 0, 0, 1, 0},
      {0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0}};
  
  public static final int[][] GLIDER = {{0, 1, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0}, {1, 1, 1, 0, 0, 0},
      {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
}

