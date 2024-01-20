package ca.mcmaster.se2aa4.mazerunner;

public interface PathFinder {

    String findOutputPath(MazeRepresenter maze);
    
    boolean outputPathFound();
}
