package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;
import java.nio.file.Paths;


public class Main {

    private static final Logger logger = LogManager.getLogger();

    /*Check if a move is valid. 
    Move validity does not depend on whether wall on right side rule is met as we will be implementing new algorithms later
    Check if index is within range of the maze rows and columns and if the "value" is "PASS"
    */
    public static boolean valid(int[] next_coordinates){
        return true;
    }
    // Converting long form of path to factorized form and vice versa
    public static String pathConverter(String path){
        return path;
    }

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        try {
            Configuration config = configure(args);
            MazeRepresenter maze = new MazeRepresenter(config.filePath);
            MazeExplorer explorer = new MazeExplorer(maze);
            if (config.inputPath.equals("null")){
                logger.info("**** Computing path");
                String outputPath = explorer.explore();
                if (outputPath.equals("null")){
                    System.out.println("Path found: " + outputPath);
                    logger.error("PATH NOT COMPUTED");
                }else{
                    System.out.println("Path found: " + outputPath);
                }
                // PathFinder algorithm = new RightHandRule(maze);
                // String outputPath = algorithm.findOutputPath(maze);
                // if (algorithm.outputPathFound()) {
                //     System.out.println("Path found: " + outputPath);
                // } else {
                //     System.out.println("No path found.");
                // }
            }
            else if (!config.inputPath.equals("null") && !config.filePath.equals("null")){
                PathChecker checker = new PathChecker(maze, explorer, config.inputPath);
                if (checker.isValidPath(config.inputPath)){
                    System.out.println("Correct path");
                }else{
                    System.out.println("Incorrect path");
                }
            }
            else{
                logger.error("You have not provided the right flags and arguments in accordance with the instructions");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(0);
        }


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
        String inputPath = cmd.getOptionValue("p","null");
        logger.info("****** The path being used to verify is " + inputPath);
        //String system = cmd.getOptionValue("scorer", "rule");
        return new Configuration(filepath, inputPath);
    }


    private record Configuration(String filePath, String inputPath){
        Configuration{
            final String allowedCharactersInPath = "FLR123456789";
        
            if (!Paths.get(filePath).toFile().exists()){
                throw new IllegalArgumentException("This file does not exist");
            }

            for (int i = 0; i < inputPath.length(); i++) {
                char currentChar = inputPath.charAt(i);

                if (allowedCharactersInPath.indexOf(currentChar) == -1 && !inputPath.equals("null")) {
                    throw new IllegalArgumentException("Path can only contain the letters FLR and the digits");
                }
            }

            
        }
    }   
}
