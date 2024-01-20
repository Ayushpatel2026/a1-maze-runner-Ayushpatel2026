package ca.mcmaster.se2aa4.mazerunner;

public class MazeExplorer {
    private MazeRepresenter maze;
    public Point nextPoint;
    public StringBuilder outputPath;

    public enum Direction {
        NORTH, //(0,1)
        SOUTH, //(0,-1)
        EAST, //(1,0)
        WEST //(-1,0)
    }

    MazeExplorer(MazeRepresenter maze){
        this.maze = maze;
        this.nextPoint = maze.currentPoint;
    }

    /*Based off current point, look at surroundings and determine nextpoint
    Throws an exception if nextPoint is not found
    */
    public Point nextPoint(Point currentPoint, Direction direction){
        return currentPoint;
    }


    public void moveForward(Point currentPoint, Direction direction){

    }

    public void turnRight(Point currentPoint, Direction direction){

    }

    public void turnLeft(Point currentPoint, Direction direction){
        
    }


    // Using the methods above, explore the maze until final Path is found or not
    public void explore() {
        
    }
}
