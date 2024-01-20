package ca.mcmaster.se2aa4.mazerunner;

public interface PathFinder {

    String findPath(MazeRepresenter maze);
    
    boolean pathFound();
}
