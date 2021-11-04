package com.jd.sort;

public class MergeSort {

    public static void main(String[] args) {
        int[] arr1 = new int[]{1,4,6,8,10,12,14};
        int[] arr2 = new int[]{2,5,7,9,11,13,15,18};
        int[] resArr = mergeSort(arr1, arr2);
        for(int e:resArr){
            System.out.print(e+"\t");
        }
    }

    public static int[] mergeSort(int[] arr1,int arr2[]){

        int len1 = arr1.length;
        int len2 = arr2.length;
        int[] retArr = new int[len1 + len2];

        int i=0,j=0,k=0;

        for(; i<len1 && j< len2 ; ){
            if(arr1[i] <= arr2[j]){
                retArr[k++] = arr1[i++];
            }else {
                retArr[k++] = arr2[j++];
            }
        }

        if(i==len1){
            while (j < len2){
                retArr[k++] = arr2[j++];
            }
        }else {
            while (i < len1){
                retArr[k++] = arr2[i++];
            }
        }
        return retArr;
    }
}
