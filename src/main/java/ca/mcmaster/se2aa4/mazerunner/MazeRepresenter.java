package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MazeRepresenter {
    

    private final String filePath;
    private Point entryPoint = new Point(0, 0);
    private Point exitPoint = new Point(0, 0);
    //public Point currentPoint = entryPoint;
    public ArrayList<ArrayList<String>> maze = new ArrayList<>();

    private static final Logger logger = LogManager.getLogger();

    MazeRepresenter(String filePath){
        this.filePath = filePath;
        this.matrix();
        this.entry_exit();
    }

    /* Converts the file into a 2D arraylist to make the maze easier to work with
    Throws error if there is problem with the file content
    */
    private void matrix(){
        try {
            //logger.info("**** Reading the maze from file " + config.filePath);
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                ArrayList<String> row = new ArrayList<>();

                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        row.add("WALL");
                    } else if (line.charAt(idx) == ' ') {
                        row.add("PASS");
                    }
                }
                maze.add(row);
            }
            reader.close();

        } catch(Exception e) {
            System.err.println("/!\\ An error has occured /!\\");
        }
    }
    /*Determine the coordinates of the entry and exit points
    Throws an error if the maze does not have any valid entry-exit points
    */
    private void entry_exit(){
        entryPoint.row_number = -1;
        entryPoint.column_number = -1;
        exitPoint.row_number = -1;
        exitPoint.column_number = -1;

        for (int row = 0; row < maze.size(); row++) {
            if (maze.get(row).get(0).equals("PASS")) {
                entryPoint.row_number = row;
                entryPoint.column_number = 0;
                break;
            }
        }

        for (int row = 0; row < maze.size(); row++) {
            if (maze.get(row).get(maze.get(row).size() - 1).equals("PASS")) {
                exitPoint.row_number = row;
                exitPoint.column_number = maze.get(row).size() - 1;
                break;
            }
        }

        if (entryPoint.row_number == -1 || exitPoint.row_number == -1){
            logger.info("*** No valid entry/exits points found in the maze.");
            throw new RuntimeException("The maze given is invalid. It has no entry and exit points on its sides.");
        }


    }

    public Point getEntryPoint(){
        Point point = new Point(entryPoint.row_number, entryPoint.column_number);
        return point;
    }

    public Point getExitPoint(){
        Point point = new Point(exitPoint.row_number, exitPoint.column_number);
        return point;
    }

    public int getNumRows(){
        int size = maze.size();
        return size;
    }

    public int getNumColumns(){
        int size = maze.get(0).size();
        return size;
    }

    public String getValueAt(int row_number, int column_number){
        String result = maze.get(row_number).get(column_number);
        return result;
    }

}
