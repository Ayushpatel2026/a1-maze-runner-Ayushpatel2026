package ca.mcmaster.se2aa4.mazerunner;

public class Point {
    public int row_number;
    public int column_number;

    Point(int row_number, int column_number){
        this.row_number = row_number;
        this.column_number = column_number;
    }

    public boolean equals(Point point2){
        if ((this.row_number == point2.row_number) && (this.column_number == point2.column_number)){
            return true;
        }
        return false;
    }
}
