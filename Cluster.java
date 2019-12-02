package algorithm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Cluster {

    public static void main(String[] args) {

        String fileName1= "/Users/binbingu/Documents/Datasets/DySM/source1.txt";
        String fileName2= "/Users/binbingu/Documents/Datasets/DySM/source2.txt";
        String fileName3= "/Users/binbingu/Documents/Datasets/DySM/source3.txt";
        String fileName4= "/Users/binbingu/Documents/Datasets/DySM/source4.txt";
        String fileName5= "/Users/binbingu/Documents/Datasets/DySM/source5.txt";
        String fileName6= "/Users/binbingu/Documents/Datasets/DySM/source6.txt";
        String fileName7= "/Users/binbingu/Documents/Datasets/DySM/source7.txt";
        String fileName8= "/Users/binbingu/Documents/Datasets/DySM/source8.txt";

  /*
        String fileName1= "/Users/binbingu/Documents/Datasets/DySM/source7.txt";
        String fileName2= "/Users/binbingu/Documents/Datasets/DySM/source6.txt";
        String fileName3= "/Users/binbingu/Documents/Datasets/DySM/source1.txt";
        String fileName4= "/Users/binbingu/Documents/Datasets/DySM/source2.txt";
        String fileName5= "/Users/binbingu/Documents/Datasets/DySM/source5.txt";
        String fileName6= "/Users/binbingu/Documents/Datasets/DySM/source4.txt";
        String fileName7= "/Users/binbingu/Documents/Datasets/DySM/source3.txt";
        String fileName8= "/Users/binbingu/Documents/Datasets/DySM/source8.txt";
*/

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

        Vector<Vector<String>> inter = CenterCluster(source1, source2, threshold);
        System.out.println("---------------------------------------");
        for (i=0;i<6;i++){
            System.out.println("The~"+ (i+2)+ "-th round:");
            inter = CenterCluster(inter, source.get(i), threshold);
            System.out.println("---------------------------------------");
        }
        //for (i=0;i<inter.size();i++) {
           // System.out.println(inter.get(i));
        //}

        long endTime = System.currentTimeMillis();
        System.out.println("Running time：" + (endTime - startTime) + "ms");

       /*
        double interarray [][];
        interarray = Graph.AdjacentMatrix();   //pro-process to get similarity adjacent matrix
        ArrayList<Double> rank = new ArrayList<Double>();
        rank =Bubbletsort(interarray);
        //for (i=0;i<rank.size();i++) {
           // System.out.println(rank.get(i));
        //}

        ArrayList<Double> candidate = new ArrayList<Double>(); //store the candidate attributes in each round
       */

    }


    public static Vector<Vector<String>> CenterCluster(Vector<Vector<String>> vector, ArrayList<String> array, double k) {
        //k是阈值
        //该算法检测初始分类中的每个点是否符合到该类中剩余点的平均距离小于阈值，若不小于，则拿出来作为自由点重新加入计算
        //该方法似乎不一定能converge
        int m= Experiment.VLvector(vector);
        int n = Experiment.VLarray(array);
        int i=0;
        int j=0;
        int p=0;
        int q2=0;
        int q3=0;
        int q4=0;
        ArrayList<String> label= new ArrayList<String>();
        label=new ArrayList<String>(array);
        double matrix[] [] = new double [m][n]; //vector和array的平均相似度矩阵
        for (i=0; i< m; i++) {      //两两比较相似度
            for (j=0; j< n; j++) {
                double sum= 0;
                for (p = 0; p < vector.get(i).size(); p++) {
                    sum = sum + SimFunction.Jaccardsim(vector.get(i).get(p), array.get(j));
                    //SimFunction t = new SimFunction(source1[j], source2[k]);
                    //System.out.println("Cosinesim=" + t.Cosinesim());
                }
                matrix[i][j] = sum/vector.get(i).size();
                //System.out.println(+i+"|"+j+"|"+matrix[i][j]);
            }
        }
       //找出array与vector的每一行的最大值
        for (i=0; i< m; i++) {
            double q1 = 0;   //应该在该位置初始化m,而不是一开始
            for (j = 0; j < n; j++) {
                if (matrix[i][j] > q1) {
                    q1 = matrix[i][j];   //取最大值
                    q2 = j;              //记录最大值的下标
                }
            }
            if (q1 >= k) {    //判断是否大于阈值
                vector.get(i).add(array.get(q2));
                System.out.println("Jaccardsim" + "(" + vector.get(i) + ") = " + q1);
                label.set(q2, null);
            }
        }

        //array中将不匹配的属性值加入vector中，记为新的类
        for (j = 0; j < label.size(); j++) {
            if (label.get(j)!=null) {
                vector.add(new Vector<String>());  //添加新的vector
                vector.get(m + q3).add(label.get(j));
                //System.out.println("j = " + j + ". Single attribute" + vector1.get(m+q3) );
                q3++;
            }
        }

        //重新计算每个类中的点到其他类的距离，取最大值重新分类
        int fix = vector.size();

        for (p = 0; p < fix; p++) {
            labelB:
            for (i = 0; i < vector.get(p).size(); i++) {
                if (vector.get(p).size()==1)
                    continue;
            double temp = 0;
            String temp1 = vector.get(p).get(0);

            //增加一个新的空类，该操作对应将某个attribute分到一个新类
                for (j = 0; j < fix; j++) {
                    if (IsConnected(vector.get(p), vector.get(j), k)==false) //判断两个类有没有连接点
                    {
                       break labelB;
                    }
                    else {
                        if (AvgSim(temp1, vector.get(j)) > temp) {
                            temp = AvgSim(temp1, vector.get(j));
                            q4 = j;    //需要记录最大值所在的类
                        }
                   }
            }
                vector.get(p).remove(0);
            vector.get(q4).add(temp1); //该轮最好的位置
            }
        }


        for (int l = 0; l < vector.size(); l++) {
            System.out.println(vector.get(l));
        }


        return vector;
    }


    public static Vector<Vector<String>> IncrementalDB(Vector<Vector<String>> vector, ArrayList<String> array, double k) {
        //k是阈值
        //该算法检测初始分类中的每个点是否符合到该类中剩余点的平均距离小于阈值，若不小于，则拿出来作为自由点重新加入计算
        //该方法似乎不一定能converge
        int m= Experiment.VLvector(vector);
        int n = Experiment.VLarray(array);
        int i=0;
        int j=0;
        int p=0;
        int q2=0;
        int q4=0;
        boolean change =false;  //用来记录cluster是否改变
        Vector <Vector <String>> queue= new Vector <Vector<String>>();  //用来存储新的schema的队列
        Vector <String> abc= new Vector<String>();
        abc = new Vector<>(array);
        for (i=0; i< array.size(); i++) {
            queue.add(new Vector<String>());
            queue.get(i).add(array.get(i));
        }

      //判断queue中的点与vector是否有连接，即是否有大于阈值的
        while (queue.size()>0) {
            Vector<String> b111;
            b111= queue.get(0);   //记录该queue中此时的值方便后面操作
            queue.add(new Vector<String>());
            vector.add(b111);
            queue.remove(0);
            double temp = DBindex(vector);
            int q3=0;
            vector.remove(vector.size()-1);
            int q11= vector.size();
            //Merge 操作
            for (i=q11-1; i>= 0; i--) {
                if (IsConnected(b111, vector.get(i), k)==false)
                {
                    q3++; // 用来判断是否全部是不相连的
                }
                else {
                    int a111= vector.get(i).size();     //记录此时长度，方便remove
                    vector.get(i).addAll(b111);
                    if (DBindex(vector)< temp)
                    {
                        queue.add(vector.get(i));
                        vector.remove(i);
                        change= true;
                        break;
                    }
                    else {
                        removeFrom(vector.get(i), a111);
                        q3++;
                    }
                }
            }

            if (q3==q11) {
                vector.add(new Vector<String>());
                vector.add(b111);
            }


            for (i = vector.size()-1; i >=0; i--) {
                if (vector.get(i).isEmpty())
                {
                    vector.remove(i);
                }
            }
            for (i = queue.size()-1; i >=0; i--) {
                if (queue.get(i).isEmpty())
                {
                    queue.remove(i);
                }
            }




        }
        //以下为 Split 和 Move 操作 判断条件为change为true
        // /*
        if (change ==false){
            System.out.println("No change");
        }
        else{
            System.out.println("Changed");
        }


        // */


        for (int l = 0; l < vector.size(); l++) {
            System.out.println(vector.get(l));
        }


        return vector;
    }




        public static Vector<Vector<String>> DBGreedy(Vector <Vector<String>> array, int k) {
        //int k = 0;
        int i = 0;
        int j = 0;
        int q1 = 0; //用来记录temp最大值所在位置
        //vector为空值的情况需要解决因为两个类的inter值无法正确计算
        int fix = array.get(k).size();
        //for (k = 0; k < array.size(); k++) {
        for (i = 0; i < fix; i++) {
            double temp = Integer.MAX_VALUE;
            String temp1 = array.get(k).get(0);
            array.get(k).remove(0);
            //增加一个新的空类，该操作对应将某个attribute分到一个新类
            array.add(new Vector <String>());

            for (j = 0; j < array.size(); j++) {
                array.get(j).add(temp1); //将当前要移动的点依次加入其它类去计算DBindex值
                if (DBindex(array) < temp) {
                    temp = DBindex(array);
                    q1 = j;    //需要记录最大值所在的类
                }
                array.get(j).remove(array.get(j).size() - 1);
            }
            array.get(q1).add(temp1); //该轮最好的位置
        }
        //}
        //释放空链表的内存
        for (i = array.size()-1; i >=0; i--) {
            if (array.get(i).isEmpty())
            {
                array.remove(i);
            }
        }
        return array;
    }


    public static double DBindex(Vector <Vector<String>> array1) {
        double array2[] = new double[array1.size()]; //存储n个cluster的separation measure最大值
        Arrays.fill(array2, 0); //初始化所有元素为0
        int i = 0;
        int j = 0;
        //遇到空类直接continue跳过
        for (i = 0; i < array1.size(); i++) {
            if (array1.get(i).isEmpty())
                continue;
            else {
                double temp = 0;
                for (j = 0; j < array1.size(); j++) {
                    if (j == i) continue;
                    else {
                        if (array1.get(j).isEmpty())
                            continue;
                        else {
                            double a= Intra_Cluster(array1.get(i));
                            double b= Intra_Cluster(array1.get(j));
                            double c= Inter_Cluster(array1.get(i), array1.get(j));
                            if (temp < Fraction( a+b + 0.01, c + 0.001))
                            {
                                temp = Fraction(a+b + 0.01, c+ 0.001);

                            }
                        }
                    }
                }
                array2[i] = temp;
            }
        }
        double sum = 0;
        for (i = 0; i < array2.length; i++) {
            sum = sum + array2[i];
        }
        return sum;
    }



    public static double Intra_Cluster(Vector <String> array1){
        double avg=0;
        double sum=0;
        int i=0;
        int j=0;
        if (array1.size()==1){
            avg=0;
        }
        else {
            for (i = 0; i < array1.size()-1; i++) {

                for (j = i + 1; j < array1.size(); j++) {
                    // if (i==j) continue;
                    sum = sum + SimFunction.Jaccardsim(array1.get(i), array1.get(j));
                }

            }
            avg = 1 - sum / (array1.size() * (array1.size() - 1) / 2);
        }
        return avg;
    }

    public static double Inter_Cluster(Vector <String> array1, Vector <String> array2)
    {
        double avg=0;
        double sum=0;
        int i=0;
        int j=0;
        for (i=0; i<array1.size(); i++)
        {
            for (j=0; j< array2.size(); j++)
            {
                sum= sum + SimFunction.Jaccardsim(array1.get(i), array2.get(j));
            }
        }
        avg=1- sum/(array1.size()*array2.size());
        return avg;
    }

    public static ArrayList<Double> Bubbletsort (double array[][]) {
        //对二维数组进行排序
        ArrayList<Double> a1 = new ArrayList<Double>();  //用一维数组链表重新存数据
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j <array[0].length; j++){
                a1.add(array[i][j]);
            }
        }
        double temp=0;
        for (int i = 0; i < a1.size(); i++) {
            //外层循环，遍历次数
            for (int j = 0; j < a1.size() - i - 1; j++) {
                //内层循环，升序（如果前一个值比后一个值大，则交换）
                //内层循环一次，获取一个最大值
                if (a1.get(j) < a1.get(j + 1)) {
                    temp = a1.get(j + 1);
                    a1.set(j+1, a1.get(j));
                    a1.set(j, temp);
                }
            }
        }
        return a1;


    }

    public static double Fraction(double a, double b){
        double c= 0;
        c = a/b;
        return c;


    }

    public static double AvgSim(String s, Vector <String> array2)
    {
        double avg=0;
        double sum=0;
        int j=0;
            for (j=0; j< array2.size(); j++)
            {
                sum= sum + SimFunction.Jaccardsim(s, array2.get(j));
            }

        avg= sum/array2.size();
        return avg;

    }

    //判断是否连通，即判断两个类中是否有两个点的相似度大于 k

    public static boolean IsConnected( Vector<String> array1,  Vector<String> array2, double k)
    {
        int i=0;
        int j=0;
        boolean a= false;
        labelA:
        for (i=0; i< array1.size(); i++){
            for (j=0; j< array2.size(); j++){
                if (SimFunction.Jaccardsim(array1.get(i), array2.get(j))>=k )
                {
                    a=true;
                    break labelA;  //跳出多重循环
                }

            }

    }
        return a;
    }

    public static void removeFrom(Vector list, int pos) {
        List sublist = list.subList(pos, list.size());
        list.removeAll(sublist);
    }




    }
