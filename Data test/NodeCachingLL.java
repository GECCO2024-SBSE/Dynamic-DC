class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class NodeCachingLL {
    private Node head;
    private int size;

    // Thêm ở đầu
    public void insertAtHead(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Thêm ở cuối
    public void insertAtTail(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Thêm ở vị trí bất kỳ
    public void insertAtPosition(int data, int position) {
        if (position < 0 || position > size) {
            System.out.println("Invalid position");
            return;
        }
        if (position == 0) {
            insertAtHead(data);
        } else if (position == size) {
            insertAtTail(data);
        } else {
            Node newNode = new Node(data);
            Node current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
            size++;
        }
    }

    // Xóa ở đầu
    public void deleteAtHead() {
        if (head != null) {
            head = head.next;
            size--;
        } else {
            System.out.println("List is empty");
        }
    }

    // Xóa ở cuối
    public void deleteAtTail() {
        if (head != null) {
            if (head.next == null) {
                head = null;
            } else {
                Node current = head;
                while (current.next.next != null) {
                    current = current.next;
                }
                current.next = null;
            }
            size--;
        } else {
            System.out.println("List is empty");
        }
    }

    // Xóa ở vị trí bất kỳ
    public void deleteAtPosition(int position) {
        if (position < 0 || position >= size) {
            System.out.println("Invalid position");
            return;
        }
        if (position == 0) {
            deleteAtHead();
        } else if (position == size - 1) {
            deleteAtTail();
        } else {
            Node current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
            size--;
        }
    }

    // Truy cập theo index
    public int accessByIndex(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Invalid index");
            return -1; // Return a sentinel value to indicate failure
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // Truy cập theo giá trị
    public int accessByValue(int value) {
        Node current = head;
        int index = 0;
        while (current != null) {
            if (current.data == value) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1; // Return a sentinel value to indicate failure
    }

    // Kiểm tra danh sách rỗng
    public boolean isEmpty() {
        return head == null;
    }

    // Kiểm tra kích thước danh sách
    public int size() {
        return size;
    }

    // Duyệt xuôi
    public void forwardTraversal() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Duyệt ngược
    public void backwardTraversal() {
        backwardTraversalRecursive(head);
        System.out.println();
    }

    private void backwardTraversalRecursive(Node current) {
        if (current == null) {
            return;
        }
        backwardTraversalRecursive(current.next);
        System.out.print(current.data + " ");
    }

    // Cập nhật giá trị
    public void updateValue(int oldValue, int newValue) {
        Node current = head;
        while (current != null) {
            if (current.data == oldValue) {
                current.data = newValue;
                return;
            }
            current = current.next;
        }
        System.out.println("Value not found in the list");
    }

    // Tìm kiếm theo giá trị
    public int searchByValue(int value) {
        return accessByValue(value);
    }

    // Xóa toàn bộ phần tử
    public void clearAllElements() {
        head = null;
        size = 0;
    }

    // Giải phóng bộ nhớ
    public void deallocateMemory() {
        clearAllElements();
    }

    // Đảo ngược danh sách
    public void reverseTheList() {
        Node prev = null;
        Node current = head;
        Node next;

        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        head = prev;
    }
}
