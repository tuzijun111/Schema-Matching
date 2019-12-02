package algorithm;

import java.util.ArrayList;
import java.util.Vector;

import static algorithm.Cluster.CenterCluster;

public class DSframe {
    public static void main(String[] args) {
        /*
        String s1= "Abc";
        String s2= "abe";
        SimFunction t = new SimFunction(s1, s2);
        System.out.println("Cosine="+ t.Cosinesim());
         */

        String fileName1= "/Users/binbingu/Documents/Datasets/DySM/source1.txt";
        String fileName2= "/Users/binbingu/Documents/Datasets/DySM/source2.txt";
        String fileName3= "/Users/binbingu/Documents/Datasets/DySM/source3.txt";
        String fileName4= "/Users/binbingu/Documents/Datasets/DySM/source4.txt";
        String fileName5= "/Users/binbingu/Documents/Datasets/DySM/source5.txt";
        String fileName6= "/Users/binbingu/Documents/Datasets/DySM/source6.txt";
        String fileName7= "/Users/binbingu/Documents/Datasets/DySM/source7.txt";
        String fileName8= "/Users/binbingu/Documents/Datasets/DySM/source8.txt";

        int i=0;
        int j=0;
        int k=0;
        double threshold=0.745;

        Vector<Vector<String>> source1 = SaveToArray.SaveVector(fileName1);
        ArrayList <String> source2  = SaveToArray.SaveArray(fileName2);
        ArrayList <String> source3  = SaveToArray.SaveArray(fileName3);
        ArrayList <String> source4  = SaveToArray.SaveArray(fileName4);
        ArrayList <String> source5  = SaveToArray.SaveArray(fileName5);
        ArrayList <String> source6  = SaveToArray.SaveArray(fileName6);
        ArrayList <String> source7  = SaveToArray.SaveArray(fileName7);
        ArrayList <String> source8  = SaveToArray.SaveArray(fileName8);

        ArrayList <ArrayList <String>> source= new ArrayList<ArrayList<String>>();

        source.add(source3);
        source.add(source4);
        source.add(source5);
        source.add(source6);
        source.add(source7);
        source.add(source8);

        long startTime = System.currentTimeMillis();
        System.out.println("The~"+ 1+ "-th round:");

        Vector<Vector<String>> inter = Cluster.IncrementalDB(source1, source2, threshold);
        System.out.println("---------------------------------------");


        for (i=0;i<6;i++){
            System.out.println("The~"+ (i+2)+ "-th round:");
            inter = Cluster.IncrementalDB(inter, source.get(i), threshold);
            System.out.println("---------------------------------------");
        }
       // for (i=0;i<inter.size();i++) {
        //    System.out.println(inter.get(i));
        //}




        long endTime = System.currentTimeMillis();
        System.out.println("Running time：" + (endTime - startTime) + "ms");



    }


}
