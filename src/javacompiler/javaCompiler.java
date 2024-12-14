
package javaCompiler;

import java.io.*;
import java.util.*;

/*
THIS PROJECT WAS MADE BY OUR LOVLY TEAM:

GROUP:1
 */

import java.io.*;
import java.util.*;

public class javaCompiler {

    private static Map<String, String> symbolTable;

//***************************************************main***************************************************
    public static void main(String[] args) throws IOException {
        try {
            symbolTable = new HashMap<>();
            List<String> lines = readLinesFromFile("Input.java\\");
            readAndTokenizeLines(lines);
            createSymbolTable(lines); // Perform lexecal analysis
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------------");
            ImpelementSyntaxAnalysis(lines); // Perform syntax analysis
            System.out.println("-------------------------------------------------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//***********************************************end of main************************************************

    
    
    
    /**
     * This function Reads lines from a file and returns them as a list.
     *
     * @param filename The name of the file to read from.
     * @return A list containing the lines read from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    private static List<String> readLinesFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!(line.startsWith("//") || line.startsWith("/*") || line.startsWith("*/"))) {
                lines.add(line);
            }
        }
        reader.close();
        return lines;
    }

    
    
    
    /**
     * This function tokenizes each line of text, extracting individual words
     * (tokens) and adding them to the data structures: symbolTable.
     *
     * @param lines The list of lines to tokenize.
     */
    private static void readAndTokenizeLines(List<String> lines) {
//        int lineNumber = 1;
        for (String line : lines) {
            String[] Lexemes = line.split("\\s+");
            for (String lexeme : Lexemes) {
                String token = getToken_For_Lexeme(lexeme);
//                symbolTable.put(lexeme, lineNumber + ":" + token);
                symbolTable.put(lexeme, token);

            }
//            lineNumber++;
        }
    }

    
    
    /**
     * This function create a symbol table and print it with all its deatails
     *
     * @param lines it contain the code line.
     * 
     */
    private static void createSymbolTable(List<String> lines) {
        System.out.println("Lexeme And Token Table:");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("| Token                      | Lexeme                      | Position");
        System.out.println("-------------------------------------------------------------------------------------");

        Map<String, List<Integer>> commonLexemes = new HashMap<>();

        int lineNumber = 1;

        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (symbolTable.containsKey(word)) {
                    int position = lineNumber * 1000 + Arrays.asList(words).indexOf(word) + 1;
                    if (!commonLexemes.containsKey(word)) {
                        commonLexemes.put(word, new ArrayList<>());
                    }
                    commonLexemes.get(word).add(position);
                }
            }
            lineNumber++;
        }

        for (Map.Entry<String, String> entry : symbolTable.entrySet()) {
//            String[] info = entry.getValue().split(":");
            String lexeme = entry.getKey();
            String token = entry.getValue();
//          String lineNumberEntry = info[0];
//          String token = info[1];

            List<Integer> positions = commonLexemes.getOrDefault(lexeme, new ArrayList<>());
            String positionString="";
            for (int position : positions) {
                positionString=position+","+positionString;
            }


            System.out.printf("| %-25s  | %-25s   |lines&Index  : %-20s \n", token, lexeme, positionString);
        }
    }

    
    
    
    
    
    
    /**
     * This function Checks the lexeme type.
     *
     * @param lexeme The lexeme.
     * @return The type of the lexeme.
     *
     */
    private static String getToken_For_Lexeme(String lexeme) {
        String[] tokens = {"IDENTIFIER", "KEYWORD", "LITERAL", "OPERATOR", "SEPARATOR", "DATATYPE", "ACCESS_MODIFIER", "METHOD", "STRING", "SIMI_COLON", "COLON", "PERIOD", "UNKNOWN", "INCREMENT", "DECREMENT", "PRINT_STATEMENT"};
        // Check if the lexeme is a reserved keyword
        if (lexeme.equals("if") || lexeme.equals("else") || lexeme.equals("for")
                || lexeme.equals("while") || lexeme.equals("do")
                || lexeme.equals("switch") || lexeme.equals("case")
                || lexeme.equals("break") || lexeme.equals("continue")
                || lexeme.equals("return") || lexeme.equals("try")
                || lexeme.equals("catch") || lexeme.equals("true")
                || lexeme.equals("false") || lexeme.equals("null")) {
            return tokens[1]; // Return KEYWORD
            // Check if the lexeme is a numeric literal
        } else if (lexeme.matches("[0-9]+") || lexeme.matches("[0-9]*\\.[0-9]+")) {
            return tokens[2]; // Return LITERAL
            // Check if the lexeme is an operator
        } else if (lexeme.equals("+") || lexeme.equals("-") || lexeme.equals("*")
                || lexeme.equals("/") || lexeme.equals("%")
                || lexeme.equals("++") || lexeme.equals("--")
                || lexeme.equals("=") || lexeme.equals("+=")
                || lexeme.equals("-=") || lexeme.equals("*=")
                || lexeme.equals("/=") || lexeme.equals("%=")
                || lexeme.equals("==") || lexeme.equals("!=")
                || lexeme.equals(">") || lexeme.equals("<")
                || lexeme.equals(">=") || lexeme.equals("<=")
                || lexeme.equals("&&") || lexeme.equals("||")
                || lexeme.equals("!") || lexeme.equals("&")
                || lexeme.equals("|") || lexeme.equals("^")
                || lexeme.equals("~") || lexeme.equals("<<")
                || lexeme.equals(">>") || lexeme.equals(">>>")
                || lexeme.equals(">>>=") || lexeme.equals("<<=")
                || lexeme.equals(">>=") || lexeme.equals("&=")
                || lexeme.equals("|=") || lexeme.equals("^=")) {
            return tokens[3]; // Return OPERATOR
            // Check if the lexeme is a separator
        } else if (lexeme.equals("(") || lexeme.equals(")")
                || lexeme.equals("{") || lexeme.equals("}")
                || lexeme.equals("[") || lexeme.equals("]")) {
            return tokens[4]; // Return SEPARATOR
            // Check if the lexeme is a data type
        } else if (lexeme.equals("int") || lexeme.equals("char")
                || lexeme.equals("boolean") || lexeme.equals("byte")
                || lexeme.equals("short") || lexeme.equals("long")
                || lexeme.equals("float") || lexeme.equals("double")
                || lexeme.equals("String") || lexeme.equals("void")
                || lexeme.equals("BigInteger") || lexeme.equals("BigDecimal")
                || lexeme.equals("Date") || lexeme.equals("Time")
                || lexeme.equals("Timestamp") || lexeme.equals("Calendar")
                || lexeme.equals("Object") || lexeme.equals("Class")) {
            return tokens[5]; // Return DATATYPE
            // Check if the lexeme is an access modifier
        } else if (lexeme.equals("public") || lexeme.equals("private")
                || lexeme.equals("protected") || lexeme.equals("default")
                || lexeme.equals("final") || lexeme.equals("abstract")
                || lexeme.equals("static") || lexeme.equals("synchronized")
                || lexeme.equals("transient") || lexeme.equals("volatile")) {
            return tokens[6]; // Return ACCESS_MODIFIER
            // Check if the lexeme is a method
        } else if (lexeme.equals("void") || lexeme.equals("return")
                || lexeme.equals("this") || lexeme.equals("super")
                || lexeme.equals("new") || lexeme.equals("instanceof")
                || lexeme.equals("throws") || lexeme.equals("throw")
                || lexeme.equals("assert") || lexeme.equals("final")
                || lexeme.equals("abstract") || lexeme.equals("static")) {
            return tokens[7]; // Return METHOD
            // Check if the lexeme is a string
        } else if (lexeme.startsWith("\"") && lexeme.endsWith("\"")
                && lexeme.length() > 1) {
            return tokens[8]; // Return STRING
            // Check if the lexeme is a semicolon
        } else if (lexeme.equals(";")) {
            return tokens[9]; // Return SIMI_COLON
            // Check if the lexeme is a colon
        } else if (lexeme.equals(",") || lexeme.equals(".")
                || lexeme.equals(":")) {
            return tokens[10]; // Return COLON
            // Check if the lexeme is a period
        } else if (lexeme.equals(".")) {
            return tokens[11]; // Return Period
        } // Check if the lexeme is an identifier
        else if (lexeme.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
            return tokens[0]; // Return IDENTIFIER
            // Check if the lexeme is an increment
        } else if (lexeme.endsWith("++")) {
            return tokens[13]; // Return INCREMENT
            // Check if the lexeme is print statement
        } else if (lexeme.equals("System.out.print") || lexeme.equals("System.out.println") || lexeme.equals("System.out.printf")) {
            return tokens[15]; // Return PRINT_STATEMENT
            // Check if the lexeme is decrement
        } else if (lexeme.endsWith("--")) {
            return tokens[14]; // Return DECREMENT
            // Check if the lexeme is unknown
        } else {
            return tokens[12]; // Return UNKNOWN
        }
    }

    
    
    
    
    
    /**
     * This function performs syntax analysis on a list of lines of code (It
     * checks for matching braces.
     *
     * @param lines The list of lines of code to analyze.
     */
    private static void ImpelementSyntaxAnalysis(List<String> lines) {
        // Print statement indicating the start of syntax analysis
        System.out.println("\n\nAll The Syntax Analysis:");
        System.out.println("-------------------------------------------------------------------------------------");

        // Initialize line number and stack for storing opening braces and keywords
        int lineNumber = 1;
        Stack<String> stack = new Stack<>();

        // Iterate over each line in the list of lines
        for (String line : lines) {
            // Split the line into lexemes
            String[] lexemes = line.split("\\s+");
            // Iterate over each lexeme in the line
            for (String lexeme : lexemes) {

                // Check if the lexeme is an opening or closing brace
                if (lexeme.equals("{") || lexeme.equals("(")) {
                    // If it's an opening brace or parenthesis, push it onto the stack
                    stack.push(lexeme);
                } else if (lexeme.equals("}") || lexeme.equals(")")) {
                    // If it's a closing brace or parenthesis, handle it
                    handleClosing(stack, lineNumber, lexeme);
                }
            }
            // Increment line number
            lineNumber++;
        }

        // Handle any remaining opening braces or parentheses in the stack
        handleRemaining(stack, lineNumber);
    }

    
    
    
    
    
    /**
     * This function help the function ImpelementSyntaxAnalysis to check for any missing / mismatched
     * curly brace or parenthesis.
     *
     * @param stack contine the if/for structure.
     * @param lineNumber The number of the line.
     * @param lexeme The lexeme.
     */
    private static void handleClosing(Stack<String> stack, int lineNumber, String lexeme) {
        // Check if the stack is empty
        if (stack.isEmpty()) {
            // If the stack is empty, print an error message for a missing opening curly brace or parenthesis
            System.out.println("Syntax error at line " + lineNumber + ": Missing '" + getMatchingSeparator(lexeme) + "' for '" + lexeme + "'.");
            System.out.println("The solution: Try to add '" + getMatchingSeparator(lexeme) + "'.");
        } else {
            // If the stack is not empty, pop the top element from the stack
            String top = stack.pop();
            // Check if the popped element matches the expected opening brace or parenthesis
            if (!top.equals(getMatchingSeparator(lexeme))) {
                // If it doesn't match, print an error message for a mismatched curly brace or parenthesis
                System.out.println("Syntax error at line " + lineNumber + ": Mismatched between '" + lexeme + "' and '" + top + "'.");
                System.out.println("The solution: Try to match the curly brackets'{}' / parenthesis'()'.");
            }
        }
    }

    
    
    /**
     * This function check for and return matching curly brace or parenthesis for the given 
     * closing/opening curly brace or parenthesis.
     *
     * @param separator curly brace or parenthesis.
     * @return The matching brace or parenthesis .
     */
    private static String getMatchingSeparator(String separator) {
        switch (separator) {
            case "}":
                return "{";
            case ")":
                return "(";
            case "(":
                return ")";
            case "{":
                return "}";
            default:
                return "";
        }
    }

    
    
    /**
     * This function check for any handle remaining errors
     * 
     * 
     * @param stack contains the if/for structure.
     * @param lineNumber The number of the line.
     */
    private static void handleRemaining(Stack<String> stack, int lineNumber) {
        // Iterate over the stack until it's empty
        while (!stack.isEmpty()) {
            // Pop the top element from the stack
            String top = stack.pop();
            // Print an error message for each remaining brace or parenthesis
            System.out.println("Syntax error at line " + lineNumber + ": Missing '" + getMatchingSeparator(top) + "' for '" + top + "'.");
            System.out.println("The solution: Try to add '" + getMatchingSeparator(top) + "'.");

        }
    }

}
