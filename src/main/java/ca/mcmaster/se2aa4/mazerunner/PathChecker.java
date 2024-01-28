package ca.mcmaster.se2aa4.mazerunner;

public class PathChecker {
    MazeRepresenter maze;
    MazeExplorer explorer;
    String inputPath;
    Point entryPoint;
    Point exitPoint;

    PathChecker(MazeRepresenter maze, MazeExplorer explorer, String inputPath){
        this.maze = maze;
        this.explorer = explorer;
        this.inputPath = inputPath;
        this.entryPoint = maze.getEntryPoint();
        this.exitPoint = maze.getExitPoint();
    }

    public boolean isFactored(String inputPath){
        String pattern = "^[FLR ]*$";
        return inputPath.matches(pattern);
    }

    public String unFactor(String factoredPath){
        StringBuilder path = new StringBuilder();

        boolean needToFactor = false;

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

    public boolean isValidPath(String inputPath, Point currentPoint, Point exitPoint){
        MazeExplorer.Direction currentDirection = explorer.direction;

        Point potentialNextPoint = explorer.nextPoint(inputPath, currentPoint, currentDirection);
        if (potentialNextPoint != null && potentialNextPoint.equals(exitPoint)){
            return true;
        }
        else{
            return false;
        }

    }
}
