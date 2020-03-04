package invoiceParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class main {
    public static void main(String[] args) {
        String filepath = "/home/rico/Desktop/inport.txt";
        String[] importText = fileReader(filepath);
        String[][] processedText = textInterpreter(importText, true);
        print2dArray(processedText, importText.length, 6);
        export(processedText, importText.length, 6);

    }

    public static String[] fileReader(String filePath) {
        BufferedReader reader;
        String[] Q = new String[200];
        int i = 0;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                Q[i] = line;
                i++;
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Q;
    }

    public static String[][] textInterpreter(String[] input, boolean AH) {
        String[][] Q = new String[200][6];
        int i = 0;
        boolean endFlag = false;
        if (AH) {

            while (!endFlag) {
                String[] tempString = input[i].split(" ");
                if (tempString.length < 4) {
                    Q[i] = new String[] {"-", "-", "-", "-", "-", "-"};
                    endFlag = true;
                }
                else if (tempString.length == 4) {
                    Q[i] = new String[]{"ENTER TITLE", tempString[tempString.length -4], tempString[tempString.length -3], tempString[tempString.length -2], tempString[tempString.length -1], ""};
                }
                else {
                    String titleString = "";
                    int c = 0;
                    while (c + 4 < tempString.length) {
                        if (!tempString[c].equals("+")) {
                            titleString += tempString[c] + " ";
                        }
                        c++;
                    }
                    Q[i] = new String[] {titleString, tempString[tempString.length -4], tempString[tempString.length -3], tempString[tempString.length -2], tempString[tempString.length -1], ""};
                }
                i++;
                //System.out.println(Q[i]);
            }
            endFlag = false;
            while (input[i].length() > 0) {
                if (input[i].equals("overige")) {
                    Q[i] = new String[] {"EOF"};
                    return Q;
                }
                else {
                    String[] tempString = input[i].split(" ");
                    String titleString = "";
                    System.out.println(i);
                    int c = 0;
                    while (c + 5 < tempString.length) {
                        if (!tempString[c].equals("+")) {
                            titleString += tempString[c] + " ";
                        }
                        c++;
                    }
                    Q[i] = new String[]{titleString, tempString[tempString.length - 5], tempString[tempString.length - 4], tempString[tempString.length - 3], tempString[tempString.length - 2], tempString[tempString.length - 1]};
                }
                i++;
            }
        }
        Q[i] = new String[] {"EOF"};
        return Q;
    }

    public static void print2dArray(String[][] I, int length, int width) {
        int a = 0;
        while (a < length) {
            int b = 0;
            String print = "";
            while (b < width) {
                if (I[a][b].equals("EOF")) {
                    return;
                }
                print += I[a][b] + "|";
                b++;
            }
            System.out.println(print);
            a++;
        }
    }

    public static void export(String[][] I, int length, int width) {
        try {
            PrintWriter writer = new PrintWriter("/home/rico/Desktop/export.txt", "UTF-8");
            int a = 0;
            while (a < length) {
                int b = 0;
                String print = "";
                while (b < width) {
                    if (I[a][b].equals("EOF")) {
                        writer.close();
                        return;
                    }
                    print += I[a][b] + "\t";
                    b++;
                }
                writer.println(print);
                a++;
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
