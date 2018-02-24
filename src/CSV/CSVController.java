package CSV;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * CSV Controller class. Holds onto a single input file. Can write to any
 * specified output file.
 */
public class CSVController {

    private String fileName;
    private String seperator;

    public CSVController(String fileName) {
        this.fileName = fileName;
        this.seperator = ",";
    }

    public CSVController(String fileName, String seperator) {
        this.fileName = fileName;
        this.seperator = seperator;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void extractColumns(String outputFile) throws IOException {
        CSVParser dataParser;
        String[] columnNames;
        Boolean addCR = false;

        File dataFile = new File(this.fileName);
        File outFile = new File(outputFile);

        try (FileReader reader = new FileReader(outFile);
        BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line = bufferedReader.readLine();
            columnNames = line.split(this.seperator);
        } catch (IOException e) {
            System.out.println("Could not find output file.");
            throw e;
        }

        try (RandomAccessFile rFile = new RandomAccessFile(outFile, "r")) {
            rFile.seek(outFile.length() - 1);
            if (! rFile.readLine().isEmpty()) {
                addCR = true;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find output file.");
            throw e;
        }
        try (FileWriter writer = new FileWriter(outFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        PrintWriter out = new PrintWriter(bufferedWriter)){
            if (addCR) {
                out.println();
            }
            dataParser = CSVParser.parse(dataFile, StandardCharsets.ISO_8859_1, CSVFormat.RFC4180.withFirstRecordAsHeader().withDelimiter(this.seperator.charAt(0)));
            for (CSVRecord record : dataParser) {
                for (int i = 0; i != columnNames.length; i ++) {
                    String val = record.get(columnNames[i]);
                    out.print(val);
                    if (i == columnNames.length - 1) {
                        out.println();
                    } else {
                        out.print(this.seperator);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOError");
            throw e;
        }
    }
}
