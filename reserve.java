import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class reserve {

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        List<List<String>> orderlist = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // while (list.size() <= 40) {
        //     list.add("");
        // }
        
        while(true) {
            System.out.print("command : ");
            String command = input.nextLine();
            
            if (command.equals("reserve")) {
                retable(set);
                System.out.print("order : ");
                    int order = input.nextInt() - 1;
                    if (!set.contains(order)) {
                        set.add(order);
                        input.nextLine();

                        List<String> list = new ArrayList<>();
                        list.add(String.valueOf(order+1));

                    for (int i = 0; i < 4; i++) {
                        LocalDateTime now = LocalDateTime.now();
                        System.out.print("custommer "  + (i+1) + ": ");
                        String custommer = input.nextLine();
                        if (custommer.contains("exit")) {break;}
                        list.add(now.format(formatter) + " " + custommer);
                    }
                    orderlist.add(list);
                }
            } else if (command.startsWith("status")) {
                retable(set);
                try {
                    String c =  command.substring(7).trim();
                    int num = Integer.parseInt(c) - 1;
                    System.out.println(orderlist);
                } catch (Exception e) {}
            } else if (command.equals("cancel")) {
                System.out.print("Cancel order : ");
                int nums = input.nextInt()-1;
                cancel(set, orderlist, nums);
                input.nextLine();
            } else if (command.equals("exit")) {
                System.out.println("Thank you");
                break;
            } else {
                System.out.println("Some thing is wrong try agan ?");
            }
        }

        input.close();
    }

    static void retable(Set<Integer> table) {
        for (int i = 0; i < 10; i++) {
            String status = table.contains(i) ? "[x]" : "[ ]";
            System.out.print("table " + (i+1) + ": " + status + " ");
        }
        System.out.println();
    }

    static void cancel(Set<Integer> table, List<List<String>> list, int c) {
        table.remove(c);
        list.remove(c);
    }

}