package algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimFunction {


    /* Levenshtein Distance */
    private static int min(int one, int two, int three) {
        int min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            min = three;
        }
        return min;
    }

    public static int Levenld(String stri1, String stri2) {
        //所有大写转化为小写
        String str1 = stri1.toLowerCase();
        String str2 = stri2.toLowerCase();
        int d[][]; // 矩阵
        int n = str1.length();
        int m = str2.length();
        int i; // 遍历str1的
        int j; // 遍历str2的
        char ch1; // str1的
        char ch2; // str2的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) { // 遍历str1
            ch1 = str1.charAt(i - 1);
            // 去匹配str2
            for (j = 1; j <= m; j++) {
                ch2 = str2.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1]+ temp);
            }
        }
        return d[n][m];
    }
    public static double Levensim(String str1, String str2) {
        try {
            double ld = (double)Levenld(str1, str2);
            return (1-ld/(double)Math.max(str1.length(), str2.length()));
        } catch (Exception e) {
            return 0.1;
        }
    }


    /*
    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "zac";
        System.out.println("ld=" + Levenld(str1, str2));
        System.out.println("sim=" + Levensim(str1, str2));
    }   */

    //Jaccard Similarity
    public static double Jaccardsim(String stri1, String stri2) {
        String str1 = stri1.toLowerCase();
        String str2 = stri2.toLowerCase();
        Set<Character> s1 = new HashSet<>();//set元素不可重复
        Set<Character> s2 = new HashSet<>();

        for (int i = 0; i < str1.length(); i++) {
            s1.add(str1.charAt(i));//将string里面的元素一个一个按索引放进set集合
        }
        for (int j = 0; j < str2.length(); j++) {
            s2.add(str2.charAt(j));
        }

        float mergeNum = 0;//并集元素个数
        float commonNum = 0;//相同元素个数（交集）

        for (Character ch1 : s1) {
            for (Character ch2 : s2) {
                if (ch1.equals(ch2)) {
                    commonNum++;
                }
            }
        }

        mergeNum = s1.size() + s2.size() - commonNum;

        float jaccard = commonNum / mergeNum;
        return jaccard;

    }



    //Cosine Similarity
    Map<Character, int[]> vectorMap = new HashMap<Character, int[]>();
    int[] tempArray = null;

    public SimFunction(String stri1, String stri2 ) {
        //所有大写转化为小写
        String source = stri1.toLowerCase();
        String target = stri2.toLowerCase();

        for (Character sch : source.toCharArray()) {

            if (vectorMap.containsKey(sch)) {

                vectorMap.get(sch)[0]++;
            }
            //用将字符转化为向量
            else {

                tempArray = new int[2];
                tempArray[0] = 1;
                tempArray[1] = 0;
                vectorMap.put(sch, tempArray);
            }

        }


        for (Character tch : target.toCharArray()) {

            if (vectorMap.containsKey(tch)) {

                vectorMap.get(tch)[1]++;
            }
            //用将字符转化为向量
            else {

                tempArray = new int[2];
                tempArray[0] = 0;
                tempArray[1] = 1;
                vectorMap.put(tch, tempArray);
            }

        }
    }

        // 求余弦相似度
        public double Cosinesim() {
            double result = 0;
            result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
            return result;
        }
        // 求平方和
        private double squares (Map < Character,int[]>paramMap){
            double result1 = 0;
            double result2 = 0;
            Set<Character> keySet = paramMap.keySet();
            for (Character character : keySet) {
                int temp[] = paramMap.get(character);
                result1 += (temp[0] * temp[0]);
                result2 += (temp[1] * temp[1]);
            }
            return result1 * result2;
        }

        // 点乘法
        private double pointMulti (Map < Character,int[]>paramMap){
            double result = 0;
            Set<Character> keySet = paramMap.keySet();
            for (Character character : keySet) {
                int temp[] = paramMap.get(character);
                result += (temp[0] * temp[1]);
            }
            return result;
        }

        private double sqrtMulti (Map < Character,int[]>paramMap){
            double result = 0;
            result = squares(paramMap);
            result = Math.sqrt(result);
            return result;
        }






}







