package ca.mcmaster.se2aa4.mazerunner;

public interface PathFinder {

    String findOutputPath(MazeRepresenter maze);
    
    boolean outputPathFound();

    Point nextPoint(Point currentPoint, MazeExplorer.Direction currentDirection);

    String factor(String path);
}
