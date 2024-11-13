package ca.jrvs.apps.grep;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {
    final static Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

    private String regex;
    private String rootPath;
    private String outFile;

public static void main(String[] args) {
    if (args.length !=3) {
        throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
    BasicConfigurator.configure();
    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);


    logger.debug("Input regular expression is " + javaGrepImp.getRegex() );
    logger.debug("Output file is " + javaGrepImp.getOutFile());
    logger.debug("Path name is " + javaGrepImp.getRootPath());
    try{
        javaGrepImp.process();
       }
    catch (Exception ex) {
        logger.error("Error: Unable to process", ex);
        }
}
/*
* 3 input parameters (pathName, pattern and output file) are given to main method
* All the files under the path name has to be read and searched for the pattern
* create and file and write the pattern line once identified including the file path.

 * Create a method called process from which all the executions are done.
 * Write a method called list files that returns the list of files.
 * Write a method called read lines that returns the list of lines inside the file.
 * Write a method that verifies the pattern that is given.
 * Write a method to write the file.
 */
@Override
public void process() throws IOException {
    logger.debug("Searching for text");
    long kb = 0;
    long numberOfLinesProcessed = 0;
    List<File> liftOfFile = listFiles(rootPath);
    logger.info("The number of file pattern is searching " + liftOfFile.size());

    List<String> matchedStrings = new ArrayList<>();
    for (File file : liftOfFile) {
        logger.debug("Processing file " + file.getAbsolutePath());
        Path path = Paths.get(file.getAbsolutePath());
        kb += Files.size(path);
        List<String> listOfString = readLines(file);
        numberOfLinesProcessed += listOfString.size();
        for (int i = 0; i < listOfString.size(); i++) {
            logger.trace("Processing line " + i);
            boolean containsPattern = containsPattern(listOfString.get(i));
            if (containsPattern) {
                matchedStrings
                        .add("File Name --> " + file + " --> Line Number --> " + i + 1 + " " + listOfString.get(i));
            }
        }
    }

    logger.info("----------- Stats --------------");
    logger.info("Number of Files processed " + liftOfFile.size());
    logger.info("Volume of Data Processed in KB " + kb);
    logger.info("Number of Lines processed " + numberOfLinesProcessed);
    logger.info("----------- Stats --------------");

    matchedStrings.add("");
    matchedStrings.add("----------- Stats Start --------------");
    matchedStrings.add("Number of Files processed " + liftOfFile.size());
    matchedStrings.add("Number of Lines processed " + numberOfLinesProcessed);
    matchedStrings.add("Volume of Data Processed in KB " + kb);
    matchedStrings.add("----------- Stats End --------------");

    writeToFile(matchedStrings);
    logger.debug("Total number of pattern matched" + matchedStrings.size());
}

    /*  listfiles() lists the files as well as files inside directory with full path
    *   @input for listfiles() is the path (from which files will be listed)
    *   @returns is the list of files from the given path.
    */

    @Override
    public List<File> listFiles(String rootDir) {
    File dir = new File(rootDir);
    File[] filesArray= dir.listFiles();
    List<File> files = new ArrayList<>();
    if (filesArray != null) {
        for (int i = 0; i < filesArray.length; i++) {
            if (filesArray[i].isDirectory()) {
                String path = filesArray[i].getAbsolutePath();
                files.addAll(listFiles(path));
                logger.debug("path print " +path);
                logger.debug("file directory");
            }
            else
            {
                files.add(filesArray[i]);
                logger.debug("The path is "+filesArray[i]);
            }
        }
    }
        return files;
    }

    /*  list the lines from the file
     *  @input is the file
     *  @returns list of lines
    */

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lineList = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
               lineList.add(line);
            }
        }
        catch (Exception e){
            logger.error("Exception while reading line", e);
            }
        return lineList;
    }

    /*  containsPattern() checks the if the given pattern is present in a line
        @input is the lines from files
        @returns boolean.
     */

    @Override
    public boolean containsPattern(String line) {
        Pattern pattern =  Pattern.compile(regex.toUpperCase());
        Matcher matcher = pattern.matcher(line.toUpperCase());
        boolean isMatches = matcher.find();
        return isMatches;
    }

    /*  writeToFile() writes lines which are matched in a outfile
     *  @input file is lines.
     *  @returns write in outfile.
     */

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        logger.debug("write to outfile" +outFile);
        FileWriter fileWriter = new FileWriter(outFile);
        BufferedWriter writer = new BufferedWriter(fileWriter);

        for (String line : lines) {
            logger.debug("The line is "+line);
            writer.write(line);
            writer.newLine();

        }
        writer.close();
    }


    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}