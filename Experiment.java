package algorithm;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class Experiment {

    public static void main(String[] args) {
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
        double threshold=0.6;

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

        //Vector<Vector<String>> Source = SaveToArray.SaveVector(fileName1);

        long startTime = System.currentTimeMillis();
        System.out.println("The~"+ 1+ "-th round:");
        //Matrix(source1, source2, threshold);
        //System.out.println("The~"+ 2+ "-th round:");
        //Matrix(Matrix(source1, source2, threshold), source3, threshold);

        Vector<Vector<String>> inter = Matrix(source1, source2, threshold);
        System.out.println("---------------------------------------");

        for (i=0;i<6;i++){
            System.out.println("The~"+ (i+2)+ "-th round:");
            inter = Matrix(inter, source.get(i), threshold);
            System.out.println("---------------------------------------");
        }

        //System.out.println(Cluster.DBindex(inter));
        double temp[]= new double[10];
        Arrays.fill(temp,0);
        for (i=0;i<10;i++) {
            for (j = 0; j < inter.size(); j++) {
                Cluster.DBGreedy(inter, j);
            }
            temp[i] = Cluster.DBindex(inter);
            if (i>0 && temp[i]== temp[i-1]) {
                System.out.println("The~"+ (i+1)+"th~" +"iteration converges");
                break;
            }
        }

        //for (i=0;i<10;i++) {
            //System.out.println("The DBindex"+ temp[i]);
        //}

        for (i=0;i<inter.size();i++) {
            System.out.println(inter.get(i));
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Running time：" + (endTime - startTime) + "ms");



    }


    //获取数组的长度，因为该数组存储的是数据库中的文件，所以我们一开始并不知道其长度
    public static int VLarray(ArrayList<String> array){
        int s =0;
        int k= 0;
        for (k=0; k< array.size(); k++) {
            if (array.get(k) != null) {
                s=k+1;
            }
            else {
                //System.out.println("s"+ + s);
                break;
            }
        }
        return s;
    }

    public static int VLvector(Vector<Vector<String>> vector){
        int s =0;
        int k= 0;
        for (k=0; k< vector.size(); k++) {
            if (vector.get(k) != null) {
                s=k+1;
            }
            else {
                //System.out.println("s"+ + s);
                break;
            }
        }
        return s;
    }

    // m n 分别为数组长度，输出相似度二维矩阵,对于Jaccardsim，这里已经将大写全部转换为了小写
    public static Vector<Vector<String>> Matrix(Vector<Vector<String>> vector1, ArrayList<String> array1, double k) {
        int m= VLvector(vector1);
        int n = VLarray(array1);
        int i=0;
        int j=0;
        int p=0;
        int q2=0;
        int q3=0;
        int q4=0;
        ArrayList<String> label= new ArrayList<String>();
        label=new ArrayList<String>(array1);  //将array1的值重新存到label中
        double matrix[] [] = new double [m][n];
        for (i=0; i< m; i++) {      //两两比较相似度
            for (j=0; j< n; j++) {
                double sum= 0;
                for (p = 0; p < vector1.get(i).size(); p++) {
                    sum = sum + SimFunction.Jaccardsim(vector1.get(i).get(p), array1.get(j));
                    //System.out.println("ld=" + SimFunction.Levenld(source1[k], source2[k]));
                    //System.out.println("Jaccardsim"+ "("+array1[i]+"," +array2[j] +") = " + SimFunction.Jaccardsim(array1[i], array2[j]));
                    //+ matrix[i][j]);
                    //System.out.println("Jaccardsim=" + SimFunction.Jaccardsim(source1[k], source2[k]));
                    //SimFunction t = new SimFunction(source1[j], source2[k]);
                    //System.out.println("Cosinesim=" + t.Cosinesim());
                }
                matrix[i][j] = sum/vector1.get(i).size();
                //System.out.println(+i+"|"+j+"|"+matrix[i][j]);
            }
        }

        for (i=0; i< m; i++) {
            double q1 = 0;   //应该在该位置初始化m,而不是一开始
            for (j =0; j<n; j++) {
                if (matrix[i][j] > q1) {
                    q1 = matrix[i][j];
                    q2 = j;              //记录最大值的下标
                }
            }
            if (q1 > k) {
                vector1.get(i).add(array1.get(q2));
                System.out.println("Jaccardsim" + "(" + vector1.get(i)  + ") = " + q1);
                label.set(q2, null);
                //array1[q2]= String.valueOf(0);       //标记未匹配的属性值
            }

        }
        //array1中将不匹配的属性值加入vector1中
        for (j = 0; j < label.size(); j++) {
             if (label.get(j)!=null) {
                 vector1.add(new Vector<String>());  //添加新的vector
                 vector1.get(m + q3).add(label.get(j));
                 //System.out.println("j = " + j + ". Single attribute" + vector1.get(m+q3) );
                 q3++;
             }

        }


        for (int l = 0; l < vector1.size(); l++) {
            System.out.println(vector1.get(l));
        }
        return vector1;
        }

}
