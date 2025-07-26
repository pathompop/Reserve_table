import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class reserve {

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        List<List<String>> orderlist = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        List<String> arr[] = new ArrayList[10]; //เก็บชื่อลูกค้า
        int aii[] = new int[10];    //เก็บหมายเลขโต๊ะ
        List<Integer> aq = new ArrayList<>();   //เก็บลำดับโต๊ะ
        int a = 0;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        while(true) {
            System.out.print("command : ");
            String command = input.nextLine();

            switch (command) {
                case "reserve":
                    retable(set);
                    System.out.print("order : ");
                    int order = input.nextInt() - 1;
                    if (!set.contains(order) && order >= 0 && order < 10) {
                        set.add(order);
                        input.nextLine();

                        List<String> list = new ArrayList<>();

                        aii[order] = a;
                        aq.add(order);
                        a++;
                        for (int i = 0; i < 4; i++) {
                            LocalDateTime now = LocalDateTime.now();
                            System.out.print("customer " + (i + 1) + ": ");
                            String customer = input.nextLine();
                            if (customer.contains("exit")) { break; }
                            list.add(now.format(formatter) + " " + customer);
                        }
                        arr[order] = list;
                        orderlist.add(list);
                    }
                    break;

                case "status":
                    retable(set);
                    break;

                case "cancel":
                    System.out.print("Cancel order : ");
                    int nums = input.nextInt() - 1;
                    cancel(set, orderlist, nums, aii[nums], arr);
                    input.nextLine();
                    aii[nums] = 0;
                    aq.removeIf(n -> n == nums);
                    a--;
                    break;

                case "queue":
                    int i = 0;
                    while (i < orderlist.size()) {
                        System.out.println("table :" + (aq.get(i) + 1) + " ");
                        for (String ctm : orderlist.get(i)) {
                            System.out.println("    " + ctm);
                        }
                        i++;
                    }
                    break;

                case "exit":
                    System.out.println("Thank you");
                    input.close();
                    return;

                default:
                    System.out.println("Something is wrong, try again?");
            }
        }
    }

    static void retable(Set<Integer> table) {
        for (int i = 0; i < 10; i++) {
            String status = table.contains(i) ? "[x]" : "[ ]";
            System.out.print("table " + (i+1) + ": " + status + " ");
        }
        System.out.println();
    }

    static void cancel(Set<Integer> table, List<List<String>> list, int c, int order, List<String>[] arr) {
        table.remove(c);
        list.remove(order);
        arr[c] = null;
    }

}