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

    @Override
    public String findOutputPath(MazeRepresenter maze) {
        
        while (!currentPoint.equals(maze.getExitPoint())) {
            Point nextPoint = nextPoint(currentPoint, explorer.direction);
    
            if (nextPoint != null) {
                currentPoint = nextPoint;
            } else {
                System.out.println("Explorer is blocked!");
                break;
            }
        }
        System.out.println("Final Output Path: " + factor(outputPath.toString()));
    
        return outputPath.toString();
    }

    @Override
    public boolean outputPathFound() {

        return true;
    }

    public Point nextPoint(Point currentPoint, MazeExplorer.Direction currentDirection){
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

        return potentialNextPoint;      
    }

    public String factor(String outputPath){
        StringBuilder canonizedPath = new StringBuilder();
        char currentMove = 0;  // Placeholder for the current command
        int moveCount = 0;        // Counter for the current command's repetition

        for (char move : outputPath.toCharArray()) {
            if (currentMove == 0) {
                // First command encountered
                currentMove = move;
                moveCount = 1;
            } else if (currentMove == move) {
                // Same command encountered again
                moveCount++;
            } else {
                // Different command encountered
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

        return canonizedPath.toString();
    }



}
