package ca.mcmaster.se2aa4.mazerunner;

public class MazeExplorer {
    private MazeRepresenter maze;
    public Point nextPoint;
    public StringBuilder outputPath;
    public Point currentPoint;
    public Direction direction = Direction.EAST;

    public enum Direction {
        NORTH, //(0,1)
        SOUTH, //(0,-1)
        EAST, //(1,0)
        WEST //(-1,0)
    }

    MazeExplorer(MazeRepresenter maze){
        this.maze = maze;
        this.currentPoint = maze.getEntryPoint();
        this.nextPoint = null;
        this.outputPath = new StringBuilder();
    }


    // the path will be of the form FLR, where F means moveforward, L means turn left and R means turn right
    public Point nextPoint(String path, Point currentPoint, Direction currentDirection){
        for (int i = 0; i < path.length(); i++) {
            char move = path.charAt(i);
            if (move == 'F') {
                currentPoint = moveForward(currentPoint, currentDirection);
                if (currentPoint == null){
                    break;
                }
            } else if (move == 'L') {
                currentDirection = turnLeft(currentDirection);
            } else if (move == 'R') {
                currentDirection = turnRight(currentDirection);
            } else if (move == ' ') {
                // Do nothing
            }else{
                System.out.println("Invalid move character: " + move);
                return null;
            }
        }
        return currentPoint;
    }

    public Point moveForward(Point currentPoint, Direction currentDirection){
        Point nextPoint = new Point(currentPoint.row_number, currentPoint.column_number);


        if (currentDirection == Direction.EAST && currentPoint.column_number < maze.getNumColumns() - 1) {
            if (maze.getValueAt(currentPoint.row_number, currentPoint.column_number + 1).equals("PASS")){
                nextPoint.column_number = currentPoint.column_number + 1;
            }
            else{
                return null;
            }
        } else if (currentDirection == Direction.NORTH && currentPoint.row_number < maze.getNumRows() - 1) {
            if (maze.getValueAt(currentPoint.row_number - 1, currentPoint.column_number).equals("PASS")){
                nextPoint.row_number = currentPoint.row_number - 1;
            }
            else{
                return null;
            }
        } else if (currentDirection == Direction.SOUTH && currentPoint.row_number > 0) {
            if (maze.getValueAt(currentPoint.row_number + 1, currentPoint.column_number).equals("PASS")){
                nextPoint.row_number = currentPoint.row_number + 1;
            }
            else{
                return null;
            }
        } else if (currentDirection == Direction.WEST && currentPoint.column_number > 0) {
            if (maze.getValueAt(currentPoint.row_number, currentPoint.column_number - 1).equals("PASS")){
                nextPoint.column_number = currentPoint.column_number - 1;
            }
            else{
                return null;
            }
            
        }
        
        return nextPoint;
    }

    public Direction turnRight(Direction currentDirection){
        switch (currentDirection) {
            case NORTH:
                return Direction.EAST;
            case EAST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.WEST;
            case WEST:
                return Direction.NORTH;
            default:
                throw new IllegalArgumentException("Invalid direction: " + currentDirection);
        }
    }

    public Direction turnLeft(Direction currentDirection){
        switch (currentDirection) {
            case NORTH:
                return Direction.WEST;
            case EAST:
                return Direction.NORTH;
            case SOUTH:
                return Direction.EAST;
            case WEST:
                return Direction.SOUTH;
            default:
                throw new IllegalArgumentException("Invalid direction: " + currentDirection);
        }
    }
}
