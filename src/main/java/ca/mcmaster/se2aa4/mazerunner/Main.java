package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;
import java.nio.file.Paths;


public class Main {

    private static final Logger logger = LogManager.getLogger();

    // Figure out what the next move is based on the right side wall hugging rule
    public static int[] next_move(int[] current_coordinates){
        int[] next_coordinates= {0,0};
        return next_coordinates;
        
    }

    /*Check if a move is valid. 
    Move validity does not depend on whether wall on right side rule is met as we will be implementing new algorithms later
    Check if index is within range of the maze rows and columns and if the "value" is "PASS"
    */
    public static boolean valid(int[] next_coordinates){
        return true;
    }

    /*Check if a path is valid, convert long form path into actual coordinate directions, check if a move is valid using
    the valid method and then calculate and update the new current coordinates 
    */ 
    public static int[] pathCheck(int[] current_coordinates, String path){
        return current_coordinates;
    }

    /*Take the next coordinates as input and figure out what the path will be for that move
    Check validity of move first and then append "path" here to a global path variable
    */
    public static String directions(int[] next_coordinates){
        String path = "FFFL";
        return path;
    }


    // Converting long form of path to factorized form and vice versa
    public static String pathConverter(String path){
        return path;
    }

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // String filePath = null;

        // // Check for -i or --input flag
        // for (int i = 0; i < args.length; i++) {
        //     if (args[i].equals("-i") || args[i].equals("--input")) {
        //         if (i + 1 < args.length) {
        //             filePath = args[i + 1];
        //         } else {
        //             System.err.println("Error: Missing file path after -i or --input flag.");
        //             System.exit(1);
        //         }
        //         break;
        //     }
        // }

        // if (filePath == null) {
        //     System.err.println("Error: Missing input file. Use -i or --input followed by the file path.");
        //     System.exit(1);
        // }

        try {
            Configuration config = configure(args);
            MazeRepresenter maze = new MazeRepresenter(config.filePath);
            if (config.path.equals("null")){
                PathFinder algorithm = new RightHandRule(maze);
                String path = algorithm.findPath(maze);
                if (algorithm.pathFound()) {
                    System.out.println("Path found: " + path);
                } else {
                    System.out.println("No path found.");
                }
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(0);
        }
        // catch (ParseException e) {
        //     // TODO Auto-generated catch block
        //     //e.printStackTrace();
        //     System.err.println(e.getMessage());
        // }catch(IllegalArgumentException e){
        //     System.err.println(e.getMessage());
        // }


        // try {
        //     Configuration config = configure(args);
        //     logger.info("**** Reading the maze from file " + config.filePath);
        //     BufferedReader reader = new BufferedReader(new FileReader(config.filePath));
        //     String line;
        //     while ((line = reader.readLine()) != null) {
        //         for (int idx = 0; idx < line.length(); idx++) {
        //             if (line.charAt(idx) == '#') {
        //                 System.out.print("WALL ");
        //             } else if (line.charAt(idx) == ' ') {
        //                 System.out.print("PASS ");
        //             }
        //         }
        //         System.out.print(System.lineSeparator());
        //     }
        // } catch(Exception e) {
        //     System.err.println("/!\\ An error has occured /!\\");
        // }
        logger.info("**** Computing path");
        logger.error("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }

     private static Configuration configure(String[] commandLineArguments) throws ParseException {
        logger.info("**** Reading Command-Line Arguments");
        Options options = new Options();
        options.addOption("i", true, "File Path of the Maze File");
        options.addOption("p", true, "Potential Path to verify");
        //options.addOption("method", true, "Score system to use (rule, fsm)");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, commandLineArguments);
        String filepath = cmd.getOptionValue("i","null");
        logger.info("****** The file being used is " + filepath);
        String path = cmd.getOptionValue("p","null");
        logger.info("****** The path being used to verify is " + path);
        //String system = cmd.getOptionValue("scorer", "rule");
        return new Configuration(filepath, path);
    }


    private record Configuration(String filePath, String path){
        Configuration{
            final String allowedCharactersInPath = "FLR123456789";
        
            if (!Paths.get(filePath).toFile().exists()){
                throw new IllegalArgumentException("This file does not exist");
            }

            for (int i = 0; i < path.length(); i++) {
                char currentChar = path.charAt(i);

                if (allowedCharactersInPath.indexOf(currentChar) == -1 && !path.equals("null")) {
                    throw new IllegalArgumentException("Path can only contain the letters FLR and the digits");
                }
            }

            
        }
    }   
}
