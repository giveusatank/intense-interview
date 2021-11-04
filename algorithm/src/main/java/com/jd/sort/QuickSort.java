package com.jd.sort;

public class QuickSort {

    public static void main(String[] args) {

        int[] arrs = new int[]{7,6,4,3,1,2,-1,33,66,-33};

        quickSort(arrs,0,arrs.length-1 );

        for(int e : arrs){
            System.out.println(e+"\t");
        }
    }

    public static void quickSort( int[] arrArr ,int start, int end ){

        int i = start;
        int j = end;

        if(i < j){
            int target = arrArr[start];
            while (i < j){
                while (arrArr[j] > target && j>i ){j--;}
                //从后往前第一个小于target
                arrArr[i] = arrArr[j] ;

                while (arrArr[i] < target && i<j ){i++;}
                //从前往后第一个大于target
                arrArr[j] = arrArr[i];
            }
            arrArr[i] = target;
            quickSort(arrArr,start,i);
            quickSort(arrArr,i+1,end);
        }

    }

}
