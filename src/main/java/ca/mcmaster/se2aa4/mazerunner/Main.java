package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;
import java.nio.file.Paths;


public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        try {
            Configuration config = configure(args);
            MazeRepresenter maze = new MazeRepresenter(config.filePath);
            MazeExplorer explorer = new MazeExplorer(maze);
            if (config.inputPath.equals("null")){
                logger.info("**** Computing path");
                PathFinder algorithm = new RightHandRule(maze, explorer);
                String outputPath = algorithm.findOutputPath(maze);
                System.out.println(outputPath);
            }
            else if (!config.inputPath.equals("null") && !config.filePath.equals("null")){
                PathChecker checker = new PathChecker(maze, explorer, config.inputPath);
                if (!checker.isFactored(checker.inputPath)){
                    checker.inputPath = checker.unFactor(checker.inputPath);
                }
                if (checker.isValidPath(checker.inputPath, checker.entryPoint, checker.exitPoint)){
                    System.out.println("Correct path");
                }else if (checker.isValidPath(checker.inputPath, checker.exitPoint, checker.entryPoint)){
                    System.out.println("correct path");
                }
                else{
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
        
        logger.info("** End of MazeRunner");
    }

     private static Configuration configure(String[] commandLineArguments) throws ParseException {
        logger.info("**** Reading Command-Line Arguments");
        Options options = new Options();
        options.addOption("i", true, "File Path of the Maze File");
        options.addOption("p", true, "Potential Path to verify");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, commandLineArguments);
        String filepath = cmd.getOptionValue("i","null");
        logger.info("****** The file being used is " + filepath);
        String inputPath = cmd.getOptionValue("p","null");
        logger.info("****** The path being used to verify is " + inputPath);
        return new Configuration(filepath, inputPath);
    }


    private record Configuration(String filePath, String inputPath){
        Configuration{
            final String allowedCharactersInPath = "FLR123456789 ";
        
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
