package algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Test {
    public static void main(String[] args){
        String fileName1= "/Users/binbingu/Documents/Datasets/DySM/source1.txt";
        File file = new File(fileName1);
        BufferedReader reader = null;
        var list = new Vector<Vector<String>>();
        for (int i=0; i<5; i++)
        {
            list.add( new Vector<String>());
        }

        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束

            int i = 0;
            int j = 0;

            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                list.get(i).add(tempString);
                System.out.println(list.get(i));
                //System.out.println("line " + line + ": " + str[i]);
                line++;
                i++;
            }

            reader.close();
        } catch (IOException e) {
            ;
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

    }


}