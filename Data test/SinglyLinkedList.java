import java.util.* ;

public class SinglyLinkedList {

    private static class ListNode {

        private final int data;
        private  ListNode next;

        public  ListNode(int data) {

            this.data = data ;
            this.next = null ;
        }

    }

    private  static ListNode head;

    public void length() {
        int counter = 0;
        ListNode current = head;
        while(current != null) {

            counter++;
            current = current.next;

        }

        System.out.println("\n"+"Length of list is :" + counter);
    }

    public void Search(int SearchKey) {

        if(head == null) {

            System.out.println("SearchKey not found!!");
        }

        ListNode current = head ;
        int counter = -1;

        while (current != null) {

            counter ++ ;
            if(current.data == SearchKey) {

                System.out.println("SearchKey Found at " + counter + " node" );
                return;
            }

            current = current.next ;

        }
        System.out.println("SearchKey not Found!!");
    }

    public void display(ListNode head) {

        ListNode current = head ;
        while(current != null ) {

            System.out.print(current.data + "-->");
            current = current.next;
        }
        System.out.print("null");
    }

    public void insertFirst(int value) {

        ListNode newNode = new ListNode(value);
        newNode.next = head ;
        head = newNode ;

    }

    public void insertEnd(int value) {

        ListNode newNode = new ListNode(value);
        if (head == null) {

            head = newNode;
            return;
        }

        ListNode current = head ;
        while(current.next != null){

            current = current.next;
        }

        current.next = newNode;
    }

    public void findMiddleNode() {

        ListNode fastPtr = head ;
        ListNode slowPtr = head ;

        while (fastPtr != null && fastPtr.next != null) {

            slowPtr = slowPtr.next ;
            fastPtr = fastPtr.next.next;

        }

        assert slowPtr != null;
        System.out.println(slowPtr.data);

    }

    public ListNode insertInSortedList(int value) {
        ListNode newNode = new ListNode(value);

        if(head == null) {
            return newNode;
        }

        ListNode current = head;
        ListNode temp = null;

        while (current != null && current.data < newNode.data) {

            temp = current ;
            current = current.next;
        }

        newNode.next = current;
        assert temp != null;
        temp.next = newNode ;

        return head;
    }

    public void getNthNodeFromEnd(int n) {

        if(head == null) {

            return ;
        }

        if(n <= 0) {

            throw  new IllegalArgumentException("Invalid Value:"+n);
        }

        ListNode mainPtr = head;
        ListNode refPtr = head ;

        int count = 0;
        while (count < n) {

            if(refPtr == null) {
                throw new IllegalArgumentException(n + " is greater than number of nodes in the list");
            }

            refPtr = refPtr.next;
            count++;
        }

        while (refPtr != null) {

            refPtr = refPtr.next;
            mainPtr = mainPtr.next;
        }

        System.out.println(mainPtr.data);
    }

    public ListNode reverseList() {

        ListNode current = head;
        ListNode previous = null;
        ListNode next ;

        while (current != null) {

            next = current.next ;
            current.next = previous ;
            previous = current ;
            current = next ;

        }

        return  previous;
    }

    public void deleteNode(int Key) {

        ListNode current = head;
        ListNode temp = null;

        if(current != null && current.data == Key) {

            head = current.next;
            return;
        }

        while (current != null && current.data != Key) {

            temp = current ;
            current = current.next;

        }

        if(current == null) {
            return;
        }

        temp.next = current.next;

    }

    public boolean detectLoop() {

        ListNode fastPtr = head ;
        ListNode slowPtr = head ;

        while (fastPtr != null && fastPtr.next !=null) {

            fastPtr = fastPtr.next.next ;
            slowPtr = slowPtr.next;

            if(slowPtr == fastPtr) {
                return true;
            }
        }

        return false;
    }
    // Thêm phần tử vào đầu danh sách
    public void insertAtHead(int value) {
        ListNode newNode = new ListNode(value);
        newNode.next = head;
        head = newNode;
    }

    // Thêm phần tử vào cuối danh sách
    public void insertAtTail(int value) {
        ListNode newNode = new ListNode(value);
        if (head == null) {
            head = newNode;
            return;
        }

        ListNode current = head;
        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    // Thêm phần tử vào vị trí bất kỳ trong danh sách
    public void insertAtPosition(int value, int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }

        ListNode newNode = new ListNode(value);

        if (position == 0) {
            newNode.next = head;
            head = newNode;
            return;
        }

        ListNode current = head;
        int count = 0;

        while (count < position - 1 && current != null) {
            current = current.next;
            count++;
        }

        if (current == null) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }

        newNode.next = current.next;
        current.next = newNode;
    }

    // Xóa phần tử ở đầu danh sách
    public void deleteAtHead() {
        if (head != null) {
            head = head.next;
        }
    }

    // Xóa phần tử ở cuối danh sách
    public void deleteAtTail() {
        if (head == null || head.next == null) {
            head = null;
            return;
        }

        ListNode current = head;
        ListNode previous = null;

        while (current.next != null) {
            previous = current;
            current = current.next;
        }

        previous.next = null;
    }

    // Xóa phần tử ở vị trí bất kỳ trong danh sách
    public void deleteAtPosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }

        if (position == 0) {
            head = head.next;
            return;
        }

        ListNode current = head;
        int count = 0;

        while (count < position - 1 && current != null) {
            current = current.next;
            count++;
        }

        if (current == null || current.next == null) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }

        current.next = current.next.next;
    }


}
