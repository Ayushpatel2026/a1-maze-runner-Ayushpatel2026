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

        //char currentMove = 0;  
        StringBuilder countBuilder = new StringBuilder();
        int count = 0;

        for (char c : factoredPath.toCharArray()) {
            if (c == ' '){
                continue;
            }
            if (Character.isDigit(c)) {
                needToFactor = true;
                countBuilder.append(c);

            } else {
                if (needToFactor == true){
                    count = Integer.parseInt(countBuilder.toString());
                    countBuilder.setLength(0);
                    for (int i = 0; i < count; i++) {
                        path.append(c);
                    }
                    needToFactor = false;
                }
                else{
                    path.append(c);
                }   
            }
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
