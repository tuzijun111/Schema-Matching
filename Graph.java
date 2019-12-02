package algorithm;
import java.util.ArrayList;
import java.util.Scanner;//代码中使用了Scanner类
import java.util.Vector;


public class Graph {
        //定义一下的属性变量
        private int vertexSize;//顶点数量，虽然是私有的，但是可以用set get方法进行设置
        //问题1.eclipse提醒未使用vertexSize,原因是什么？

        private int [] vertex;//定义顶点数组
        private int [] [] matrix;//定义邻接矩阵，是一个二维数组
        private static final int MAX=50;//设置最大权重50代表无穷大,常量  因为是静态私有的常量，只能在本类中有效

        //图类的构造函数
        public Graph(int vertexSize){
            this.vertexSize = vertexSize;//接收传来的顶点数   在邻接矩阵的定义处可以知道，传的数是5
            matrix=new int [vertexSize] [vertexSize];//邻接绝阵是方阵，行列是定点数
            //创建顶点数组,其实就是用顶点个数定义顶点数组，最终定点数组的内容是0，1,2,3,4
            vertex=new int [vertexSize];
            for(int i=0;i<vertexSize;i++){
                vertex[i]=i;
            }
        }

        //创建vertex的set get方法   快捷键： alt+shift+s
        public int[] getVertex() {
            return vertex;
        }

        public void setVertex(int[] vertex) {
            this.vertex = vertex;//可以调用图的这个方法设置图的顶点数组
        }

        //---邻接矩阵
        public static double[][] AdjacentMatrix() {
            String fileName1 = "/Users/binbingu/Documents/Datasets/DySM/source1.txt";
            String fileName2 = "/Users/binbingu/Documents/Datasets/DySM/source2.txt";
            String fileName3 = "/Users/binbingu/Documents/Datasets/DySM/source3.txt";
            String fileName4 = "/Users/binbingu/Documents/Datasets/DySM/source4.txt";
            String fileName5 = "/Users/binbingu/Documents/Datasets/DySM/source5.txt";
            String fileName6 = "/Users/binbingu/Documents/Datasets/DySM/source6.txt";
            String fileName7 = "/Users/binbingu/Documents/Datasets/DySM/source7.txt";


            ArrayList<String> source1 = SaveToArray.SaveArray(fileName1);
            ArrayList<String> source2 = SaveToArray.SaveArray(fileName2);
            ArrayList<String> source3 = SaveToArray.SaveArray(fileName3);
            ArrayList<String> source4 = SaveToArray.SaveArray(fileName4);
            ArrayList<String> source5 = SaveToArray.SaveArray(fileName5);
            ArrayList<String> source6 = SaveToArray.SaveArray(fileName6);
            ArrayList<String> source7 = SaveToArray.SaveArray(fileName7);
            ArrayList<String> source = new ArrayList<String>();
            source.addAll(source1);   //将7个数组存到1个数组中
            source.addAll(source2);
            source.addAll(source3);
            source.addAll(source4);
            source.addAll(source5);
            source.addAll(source6);
            source.addAll(source6);
            source.addAll(source7);

            int i = 0;
            int j = 0;
            int Alength = 0;
            Alength = source.size();
            Graph graph = new Graph(Alength);//创建一个真正的图，顶点数是Alength，并输入邻接矩阵具体值
            //这里的5难道不是在使用vertexSize吗？
            double SimMatrix[][] = new double[Alength][Alength];
            for (i = 0; i < Alength; i++) {      //两两比较相似度,得到相似度矩阵
                for (j = 0; j < Alength; j++) {
                    if (j==i)
                    {
                        SimMatrix[i][j]=0;    //自己与自己的相似度为0
                    }
                    else
                    SimMatrix[i][j] = SimFunction.Levensim(source.get(i), source.get(j));
                }
            }

            return SimMatrix;
        }

            //将每行的数据赋值给图的邻接矩阵,邻接矩阵每行就是一个数组
            /*
            graph.matrix[0]=a1;
            graph.matrix[1]=a2;
            graph.matrix[2]=a3;
            graph.matrix[3]=a4;
            graph.matrix[4]=a5;//到此邻接矩阵创建完成

            //---使用输入的方式查询项要看的顶点，应该还加上判断顶点是否超出范围
            System.out.println("输入你想查看的顶点：");
            Scanner sc =new Scanner(System.in );
            int mm=0;
            if(sc.hasNext())
                mm=sc.nextInt();
            int num = graph.getOutDegree(mm);
            System.out.println("顶点"+mm+"的出度是："+num);
            sc.close();
        }

        //测试1：获取某个顶点的出度，即是邻接矩阵中这个顶点对应的行中大于0小于无穷大的数字
        public int getOutDegree(int index){
            int degree=0;//初始化出度为0
            for(int j=0;j<matrix[index].length;j++){
                if(matrix[index][j]>0 & matrix[index][j]<MAX)
                    degree++;
            }
            return degree;
        }

             */


}
