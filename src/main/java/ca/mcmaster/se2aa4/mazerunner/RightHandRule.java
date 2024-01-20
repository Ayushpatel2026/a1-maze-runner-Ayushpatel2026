package ca.mcmaster.se2aa4.mazerunner;

public class RightHandRule implements PathFinder {

    private MazeRepresenter maze;

    RightHandRule(MazeRepresenter maze){
        this.maze = maze;
    }

    @Override
    public String findPath(MazeRepresenter maze) {

        return null;
    }

    @Override
    public boolean pathFound() {

        return true;
    }
}
