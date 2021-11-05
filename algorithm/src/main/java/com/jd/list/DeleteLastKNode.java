package com.jd.list;

public class DeleteLastKNode {
    public static void main(String[] args) {

        Node header = new Node(1);
        Node n1 = new Node(2);
        Node n2 = new Node(3);
        Node n3 = new Node(4);
        Node n4 = new Node(5);
        Node n5 = new Node(6);
        Node n6 = new Node(7);
        Node n7 = new Node(8);
        Node n8 = new Node(9);

        header.setNext(n1);
        n1.setNext(n2);
        n2.setNext(n3);
        n3.setNext(n4);
        n4.setNext(n5);
        n5.setNext(n6);
        n6.setNext(n7);
        n7.setNext(n8);
        n8.setNext(null);

        printListFromFirst(header);
        deleteLastKNode(header,3);
        System.out.println();
        printListFromFirst(header);

    }

    public static void deleteLastKNode(Node header,
                                int k){
        Node fir = header;
        Node nodek = fir;
        while (k >= 1){
            nodek = nodek.getNext();
            k--;
        }
        while (nodek.getNext() != null){
            nodek = nodek.getNext();
            fir = fir.getNext();
        }
        Node nnNode = fir.getNext().getNext();
        fir.setNext(nnNode);
    }

    public static void printListFromFirst(Node header){
        while (header!=null){
            System.out.print(header.getData()+"\t");
            header = header.getNext();
        }
    }

    public static class Node {
        private int data;
        private Node next;
        public Node(int data) {
            this.data = data;
        }
        public int getData() {
            return data;
        }
        public void setData(int data) {
            this.data = data;
        }
        public Node getNext() {
            return next;
        }
        public void setNext(Node next) {
            this.next = next;
        }
    }
}
