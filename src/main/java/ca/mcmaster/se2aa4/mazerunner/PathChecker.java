package ca.mcmaster.se2aa4.mazerunner;

public class PathChecker {
    MazeRepresenter maze;
    MazeExplorer explorer;
    String inputPath;
    PathChecker(MazeRepresenter maze, MazeExplorer explorer, String inputPath){
        this.maze = maze;
        this.explorer = explorer;
        this.inputPath = inputPath;
    }

    public boolean isValidPath(String inputPath){
        Point currentPoint = explorer.currentPoint;
        MazeExplorer.Direction currentDirection = explorer.intialDirection;

        Point potentialNextPoint = explorer.nextPoint(inputPath, currentPoint, currentDirection);
        if (potentialNextPoint != null && potentialNextPoint.equals(maze.getExitPoint())){
            return true;
        }
        else{
            return false;
        }

    }



}
