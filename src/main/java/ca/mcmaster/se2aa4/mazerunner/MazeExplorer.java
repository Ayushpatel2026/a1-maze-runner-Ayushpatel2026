package ca.mcmaster.se2aa4.mazerunner;

public class MazeExplorer {
    private MazeRepresenter maze;
    public Point nextPoint;
    public StringBuilder outputPath;
    public Point currentPoint;
    public Direction intialDirection = Direction.EAST;

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
        int left_column_number = current_column_number -1;
        int right_column_number = current_column_number + 1;

        if (maze.getValueAt(current_row_number, right_column_number).equals("PASS")){
            Point point = new Point(current_row_number, right_column_number);
            nextPoint = point;
        }
        else{
            nextPoint = null;
        }

        // if (top_row_number >= 0 && top_row_number <= maze.getNumRows() - 1){
        //     if (maze.getValueAt(top_row_number, current_column_number).equals("PASS")){
        //         Point point = new Point(top_row_number, current_column_number);
        //         nextPoint = point;
        //     }
        // }
        // else if (bottom_row_number >= 0 && bottom_row_number <= maze.getNumRows() - 1){
        //     if (maze.getValueAt(bottom_row_number, current_column_number).equals("PASS")){
        //         Point point = new Point(bottom_row_number, current_column_number);
        //         nextPoint = point;
        //     }
        // }
        // else if (left_column_number >= 0 && left_column_number <= maze.getNumColumns() - 1){
        //     if (maze.getValueAt(current_row_number, left_column_number).equals("PASS")){
        //         Point point = new Point(current_row_number, left_column_number);
        //         nextPoint = point;
        //     }
        // }
        // else if (right_column_number >= 0 && right_column_number <= maze.getNumColumns() - 1){
        //     if (maze.getValueAt(current_row_number, right_column_number).equals("PASS")){
        //         Point point = new Point(current_row_number, right_column_number);
        //         nextPoint = point;
        //     }
        // }
        // else {
        //     nextPoint = null;
        // }
        
        return nextPoint;
    }

    //returns the path to be taken FLR to get from current point to next point given the direction, returns the path and appends it to stringbuilder
    public String pathString(Point currentPoint,Direction direction, Point nextPoint){
        int deltaX = nextPoint.column_number - currentPoint.column_number;
        int deltaY = nextPoint.row_number - currentPoint.row_number;
        
        if (deltaX == 1){
            return "F";
        }
        return "null";
    }



    /* Using the methods above, explore the maze until final Path is found or not
    Once nextpoint is found, it calls pathString and then currentPoint:= nextPoint and nextPoint:= null
    then it finds an empty string
    */
    public String explore() {
        Direction direction = intialDirection;
        
        while (!currentPoint.equals(maze.getExitPoint())){
            Point potentialNextPoint = nextPoint(currentPoint);
            String path = pathString(currentPoint, direction, potentialNextPoint);
            outputPath.append(path);
            currentPoint = potentialNextPoint;
            potentialNextPoint = null;
        }
        // while (currentPoint != maze.getExitPoint()) {
        //     Point potentialNextPoint = nextPoint(currentPoint);
        //     if (potentialNextPoint != null) {
        //         nextPoint = potentialNextPoint;
        //         String path = pathString(currentPoint, direction, nextPoint);
        //         System.out.print(path);
        //         outputPath.append(path);
        //         currentPoint = nextPoint;
        //         nextPoint = null;
        //         break;
        //     }
        //     System.out.print("f"); 
        // }
        if (currentPoint == null){
            outputPath = null;
        }
        else{
            System.out.println(outputPath);
        }
        return outputPath.toString();
    }

    public void moveForward(Point currentPoint, Direction direction){

    }

    public void turnRight(Point currentPoint, Direction direction){

    }

    public void turnLeft(Point currentPoint, Direction direction){
        
    }
}
