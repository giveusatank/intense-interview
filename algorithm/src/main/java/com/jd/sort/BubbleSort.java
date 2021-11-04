package com.jd.sort;

public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10};
        int[] bubbleSort = bubbleSort(arr);
        for(int e : bubbleSort){
            System.out.println(e);
        }
    }

    public static int[] bubbleSort(int[] array){
        int len = array.length - 1 ;
        int flag = 1;
        while ( len >= 1){
            if(flag == 1){
                flag = 0;
            }else {
                break;
            }
            for(int i=0;i<len;i++){
                if(array[i] >= array[i+1]){
                    swapElement(array,i,i+1);
                    flag = 1;
                }
            }
            len--;
        }
        return array;
    }

    public static void swapElement(int[] arr,int start,int end){
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
    }

}
