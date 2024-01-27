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

    public boolean isFactored(String inputPath){
        String pattern = "^[FLR ]*$";
        return inputPath.matches(pattern);
    }

    public String unFactor(String factoredPath){
        StringBuilder path = new StringBuilder();

        boolean needToFactor = false;

        char currentMove = 0;  
        StringBuilder countBuilder = new StringBuilder();
        int count = 0;

        for (char c : factoredPath.toCharArray()) {
            if (Character.isDigit(c)) {
                countBuilder.append(c);
            } else {
                if (countBuilder.length() > 0){
                    count = Integer.parseInt(countBuilder.toString());
                }
                else{
                    count = 1;
                }

                // Append the move character 'count' times to the path
                for (int i = 0; i < count; i++) {
                    path.append(currentMove);
                }

                // Reset for the next command
                currentMove = c;
                countBuilder.setLength(0);
            }
        }

        // Process the last command
        count = (countBuilder.length() > 0) ? Integer.parseInt(countBuilder.toString()) : 1;
        for (int i = 0; i < count; i++) {
            path.append(currentMove);
        }

        return path.toString();

    }

    public boolean isValidPath(String inputPath){
        Point currentPoint = explorer.currentPoint;
        MazeExplorer.Direction currentDirection = explorer.direction;

        Point potentialNextPoint = explorer.nextPoint(inputPath, currentPoint, currentDirection);
        if (potentialNextPoint != null && potentialNextPoint.equals(maze.getExitPoint())){
            return true;
        }
        else{
            return false;
        }

    }



}
