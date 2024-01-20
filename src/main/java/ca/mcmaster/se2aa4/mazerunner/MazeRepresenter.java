package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

public class MazeRepresenter {
    

    private final String filePath;
    private Point entryPoint = new Point(0, 0);
    private Point exitPoint = new Point(0, 0);
    public ArrayList<ArrayList<String>> maze = new ArrayList<>();

    MazeRepresenter(String filePath){
        this.filePath = filePath;
        this.matrix();
        this.entry_exit();
    }

    /* Converts the file into a 2D arraylist to make the maze easier to work with
    Throws error if there is problem with the file content
    */
    public void matrix(){

    }
    /*Determine the coordinates of the entry and exit points
    Throws an error if the maze does not have any valid entry-exit points
    */
    public void entry_exit(){
        entryPoint.row_number = 0;
        entryPoint.column_number = 0;
        exitPoint.row_number = 0;
        exitPoint.column_number = 0;
    }

    public Point getEntryPoint(){
        return entryPoint;
    }

    public Point getExitPoint(){
        return exitPoint;
    }

}
