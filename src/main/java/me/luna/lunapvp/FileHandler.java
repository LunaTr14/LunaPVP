package me.luna.lunapvp;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class FileHandler {
    File fileObject;
    public void initialiseFile(String fileName){
        try{
            fileObject = new  File(fileName);
        }
        catch (Exception e){
            System.out.println("Error in File Creation");
        }
    }

    public void writeFile(String fileName, String data){
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(data);
            writer.close();
        }
        catch (Exception e){
            System.out.println("Error in File Writer");
        }
    }
    public LinkedList<String> readFile(String fileName){
        LinkedList<String> returnData = new LinkedList<>();
        try {
            if(fileObject.exists()) {
                Scanner reader = new Scanner(fileObject);
                while(reader.hasNextLine()){
                    returnData.add(reader.nextLine());
                }
                reader.close();
            }
        }
        catch (Exception e){
            System.out.println("Error in Read File");
        }
        return returnData;
    }
}
