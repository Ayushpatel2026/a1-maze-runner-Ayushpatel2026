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

    /*Based off current point, look at surroundings and determine nextpoint
    Throws an exception if nextPoint is not found
    */
    public Point nextPoint(Point currentPoint){
        int current_row_number = currentPoint.row_number;
        int current_column_number = currentPoint.column_number;

        int top_row_number = current_row_number + 1;
        int bottom_row_number = current_row_number - 1;
        int left_column_number = current_column_number - 1;
        int right_column_number = current_column_number + 1;

        Point potentialNextPoint = null;

        if (right_column_number < maze.getNumColumns() && maze.getValueAt(current_row_number, right_column_number).equals("PASS")) {
            potentialNextPoint = new Point(current_row_number, right_column_number);
        } else if (top_row_number < maze.getNumRows() && maze.getValueAt(top_row_number, current_column_number).equals("PASS")) {
            potentialNextPoint = new Point(top_row_number, current_column_number);
        } else if (bottom_row_number >= 0 && maze.getValueAt(bottom_row_number, current_column_number).equals("PASS")) {
            potentialNextPoint = new Point(bottom_row_number, current_column_number);
        } else if (left_column_number >= 0 && maze.getValueAt(current_row_number, left_column_number).equals("PASS")) {
            potentialNextPoint = new Point(current_row_number, left_column_number);
        }

        return potentialNextPoint;      
    }


    // the path will be of the form FLR, where F means moveforward, L means turn left and R means turn right
    public Point nextPoint(String path, Point currentPoint, Direction currentDirection){
        System.out.println(path);
        for (int i = 0; i < path.length(); i++) {
            char move = path.charAt(i);
            System.out.println(move);
            if (move == 'F') {
                currentPoint = moveForward(currentPoint, currentDirection);
                if (currentPoint == null){
                    //System.out.println("null");
                    break;
                }
                //System.out.println(currentPoint.column_number+","+currentPoint.row_number);
            } else if (move == 'L') {
                currentDirection = turnLeft(currentDirection);
                //System.out.println(currentDirection);
            } else if (move == 'R') {
                currentDirection = turnRight(currentDirection);
                //System.out.println(currentDirection);
            } else if (move == ' ') {
                // Do nothing
            }else{
                System.out.println("Invalid move character: " + move);
                return null;
            }
        }
        //System.out.println(currentPoint.column_number+","+currentPoint.row_number);
        return currentPoint;
    }

    //returns the path to be taken FLR to get from current point to next point given the direction, returns the path and appends it to stringbuilder
    public String pathString(Point currentPoint,Direction currentDirection, Point nextPoint){
        int deltaX = nextPoint.column_number - currentPoint.column_number;
        int deltaY = nextPoint.row_number - currentPoint.row_number;
        System.out.println(deltaX);
        System.out.println(deltaY);

        StringBuilder pathBuilder = new StringBuilder();

        if (deltaX == 0 && deltaY == 0){
            pathBuilder.append("L");
        }

        // this code functions under the assumption that only one of deltaX and deltaY will be non-zero (no diaganal movement)
        if (deltaX != 0) {
            if (deltaX == 1 && currentDirection == Direction.NORTH || deltaX == -1 && currentDirection == Direction.SOUTH) {
                pathBuilder.append("R");
            } else if(deltaX == 1 && currentDirection == Direction.EAST || deltaX == -1 && currentDirection == Direction.WEST) {
                //skip 
            }
            else if (deltaX == 1 && currentDirection == Direction.WEST || deltaX == -1 && currentDirection == Direction.EAST){
                pathBuilder.append("LL");
            }
            else if (deltaX == -1 && currentDirection == Direction.NORTH || deltaX == 1 && currentDirection == Direction.SOUTH){
                pathBuilder.append("L");
            }
        } else if (deltaY != 0) {
            if (deltaY == 1 && currentDirection == Direction.WEST || deltaY == -1 && currentDirection == Direction.EAST) {
                pathBuilder.append("L");
            } else if(deltaY == 1 && currentDirection == Direction.SOUTH || deltaY == -1 && currentDirection == Direction.NORTH) {
                //skip 
            }
            else if (deltaY == 1 && currentDirection == Direction.NORTH || deltaY == -1 && currentDirection == Direction.SOUTH){
                pathBuilder.append("LL");
            }
            else if (deltaY == -1 && currentDirection == Direction.WEST || deltaY == 1 && currentDirection == Direction.EAST){
                pathBuilder.append("R");
            }
        }

        pathBuilder.append("F");

        return pathBuilder.toString();
    }



    /* Using the methods above, explore the maze until final Path is found or not
    Once nextpoint is found, it calls pathString and then currentPoint:= nextPoint and nextPoint:= null
    then it finds an empty string
    */
    public String explore() {
        Direction direction = this.direction;
        
        while (!currentPoint.equals(maze.getExitPoint())) {
            Point potentialNextPoint = nextPoint(currentPoint);
    
            if (potentialNextPoint != null) {
                String path = pathString(currentPoint, direction, potentialNextPoint);
                outputPath.append(path);
                currentPoint = potentialNextPoint;
            } else {
                // No valid next point found, break out of the loop
                break;
            }
        }
    
        if (currentPoint.equals(maze.getExitPoint())) {
            System.out.println(outputPath);
            return outputPath.toString();
        } else {
            // Handle the case where the exit point is not reached
            System.out.println("No valid path to the exit.");
            return null;
        }
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
