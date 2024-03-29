package ca.mcmaster.se2aa4.mazerunner;

public class RightHandRule implements PathFinder {

    private MazeRepresenter maze;
    private MazeExplorer explorer;
    Point currentPoint;
    MazeExplorer.Direction direction;
    StringBuilder outputPath = new StringBuilder();

    RightHandRule(MazeRepresenter maze, MazeExplorer explorer){
        this.maze = maze;
        this.explorer = explorer;
        this.currentPoint = maze.getEntryPoint();
        this.direction = explorer.direction;
    }

    // outputs the path through the maze from entry to exit
    @Override
    public String findOutputPath(MazeRepresenter maze) {
        String outputString;
        while (!currentPoint.equals(maze.getExitPoint())) {
            Point nextPoint = nextPoint(currentPoint, explorer.direction);
    
            if (nextPoint != null) {
                currentPoint = nextPoint;
            } else {
                System.out.println("No Path Found");
                break;
            }
        }
        outputString = factor(outputPath.toString());
        return outputString;
    }

    // determines nextPoint based on the right hand algorithm and throws exception if next point not found
    public Point nextPoint(Point currentPoint, MazeExplorer.Direction currentDirection) throws IllegalStateException{
        int current_row_number = currentPoint.row_number;
        int current_column_number = currentPoint.column_number;

        int top_row_number = current_row_number - 1;
        int bottom_row_number = current_row_number + 1;
        int left_column_number = current_column_number - 1;
        int right_column_number = current_column_number + 1;

        Point potentialNextPoint = null;

        switch (currentDirection) {
            case EAST:
                // Check if there is a wall to the right
                if (maze.getValueAt(bottom_row_number, current_column_number).equals("PASS")) {
                    explorer.direction = explorer.turnRight(currentDirection);
                    potentialNextPoint = explorer.moveForward(currentPoint, explorer.direction);
                    outputPath.append("RF");
                } else {
                    // If there's a wall to the right, try moving forward
                    if (maze.getValueAt(current_row_number, right_column_number).equals("PASS")) {
                        potentialNextPoint = explorer.moveForward(currentPoint, currentDirection);
                        outputPath.append("F");
                    }
                    else{
                        explorer.direction = explorer.turnLeft(currentDirection);
                        potentialNextPoint = new Point(currentPoint.row_number, currentPoint.column_number);
                        outputPath.append("L");
                    }
                }
                break;
            case NORTH:
                if (maze.getValueAt(current_row_number, right_column_number).equals("PASS")) {
                    explorer.direction = explorer.turnRight(currentDirection);
                    potentialNextPoint = explorer.moveForward(currentPoint, explorer.direction);
                    outputPath.append("RF");
                } else {
                    if (maze.getValueAt(top_row_number, current_column_number).equals("PASS")) {
                        potentialNextPoint = explorer.moveForward(currentPoint, currentDirection);
                        outputPath.append("F");
                    }
                    else{
                        explorer.direction = explorer.turnLeft(currentDirection);
                        potentialNextPoint = new Point(currentPoint.row_number, currentPoint.column_number);
                        outputPath.append("L");
                    }
                }
                break;
            case SOUTH:
                if (maze.getValueAt(current_row_number, left_column_number).equals("PASS")) {
                    explorer.direction = explorer.turnRight(currentDirection);
                    potentialNextPoint = explorer.moveForward(currentPoint, explorer.direction);
                    outputPath.append("RF");
                } else {
                    if (maze.getValueAt(bottom_row_number, current_column_number).equals("PASS")) {
                        potentialNextPoint = explorer.moveForward(currentPoint, currentDirection);
                        outputPath.append("F");
                    }
                    else{
                        explorer.direction = explorer.turnLeft(currentDirection);
                        potentialNextPoint = new Point(currentPoint.row_number, currentPoint.column_number);
                        outputPath.append("L");
                    }
                }
                break;  
            case WEST:
                if (maze.getValueAt(top_row_number, current_column_number).equals("PASS")) {
                    explorer.direction = explorer.turnRight(currentDirection);
                    potentialNextPoint = explorer.moveForward(currentPoint, explorer.direction);
                    outputPath.append("RF");
                } else {
                    if (maze.getValueAt(current_row_number, left_column_number).equals("PASS")) {
                        potentialNextPoint = explorer.moveForward(currentPoint, currentDirection);
                        outputPath.append("F");
                    }
                    else{
                        explorer.direction = explorer.turnLeft(currentDirection);
                        potentialNextPoint = new Point(currentPoint.row_number, currentPoint.column_number);
                        outputPath.append("L");
                    }
                }
                break;          
            
        }

        if (potentialNextPoint == null){
            throw new IllegalStateException("Path Not Found");
        }

        return potentialNextPoint;      
    }

    // factors the output path
    public String factor(String outputPath) throws IllegalArgumentException{

        if (outputPath.isEmpty()){
            throw new IllegalArgumentException("Output Path is Empty!");
        }

        StringBuilder canonizedPath = new StringBuilder();
        char currentMove = 0;  
        int moveCount = 0;        

        for (char move : outputPath.toCharArray()) {
            if (currentMove == 0) {
                currentMove = move;
                moveCount = 1;
            } else if (currentMove == move) {
                moveCount++;
            } else {
                if (moveCount > 0) {
                    if (moveCount == 1) {
                        canonizedPath.append(currentMove);
                        canonizedPath.append(" ");
                    } else {
                        canonizedPath.append(moveCount).append(currentMove);
                        canonizedPath.append(" ");
                    }
                }
                currentMove = move;
                moveCount = 1;
            }
        }

        if (moveCount > 0) {
            if (moveCount == 1) {
                canonizedPath.append(currentMove);
            } else {
                canonizedPath.append(moveCount).append(currentMove);
            }
        }

        return canonizedPath.toString();
    }



}
