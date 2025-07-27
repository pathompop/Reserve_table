import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class reserve {

    static String resetcolor = "\u001B[0m";
    static String red = "\u001B[38;2;255;0;0m";
    static String green = "\u001B[38;2;0;255;0m";
    static String pink = "\u001B[38;2;255;105;180m";
    static String aqua = "\u001B[38;2;0;255;255m";
    static String blueviolet = "\u001B[38;2;95;0;255m";
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        List<List<String>> orderlist = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        int aii[] = new int[10];    //เก็บหมายเลขโต๊ะ
        List<Integer> aq = new ArrayList<>();   //เก็บลำดับโต๊ะ
        int a = 0;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(blueviolet + "can you need help? type " + green + "'help'" + blueviolet + " to see commands." + resetcolor);
        while(true) {
            System.out.print("command : ");
            String command = input.nextLine();

            switch (command) {
                case "reserve":     // จองโต๊ะ
                    retable(set, orderlist, aii);

                    try {   // ป้องกันการใส่ข้อมูลที่ไม่ใช่ตัวเลข
                        System.out.print("order : ");
                        int order = input.nextInt() - 1;
                        if (!set.contains(order) && order >= 0 && order < 10) {
                            input.nextLine();
                            set.add(order);

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
                            orderlist.add(list);
                        } else if (ordercheck(orderlist, aii[order]) && order >= 0 && order < 10) {
                            for (int i = orderlist.get(aii[order]).size(); i < 4; i++) {
                                LocalDateTime now = LocalDateTime.now();
                                System.out.print("customer " + (i + 1) + ": ");
                                String customer = input.nextLine();
                                if (customer.contains("exit")) { break; }
                                orderlist.get(aii[order]).add(now.format(formatter) + " " + customer);
                                if (i <= 4) {
                                    System.out.println(green + "This table is full." + resetcolor);
                                    break;
                                }
                            }
                        } else {
                            System.out.println(aqua + "The table has been reserved." + resetcolor);
                        }
                    } catch (Exception e) { // ดักจับเมื่อผู้ใช้ใส่ข้อมูลที่ไม่ใช่ตัวเลข
                        System.out.println(red + "Invalid input, please enter a valid table number." + resetcolor);
                        input.nextLine();
                    }
                    break;

                case "status":      // แสดงสถานะโต๊ะ
                    retable(set, orderlist, aii);
                    break;

                case "cancel":      // ยกเลิกการจองโต๊ะ
                retable(set, orderlist, aii);
                    System.out.print("Cancel order : ");
                    try {   // ป้องกันการยกเลิกโต๊ะที่ไม่ถูกต้อง
                        int nums = input.nextInt() - 1;
                        input.nextLine();
                        if (set.contains(nums)) {
                            cancel(set, orderlist, nums, aii[nums]);
                            // input.nextLine();
                            aii[nums] = 0;
                            aq.removeIf(n -> n == nums);
                            a--;
                            System.out.println("The table has been cancelled.");
                        } else {
                            System.out.println(red + "The table is not reserved." + resetcolor);
                        }
                    } catch (Exception e) {
                        System.out.println(red + "Invalid input, please enter a valid table number." + resetcolor);
                        input.nextLine();
                    }
                    break;

                case "queue":       // แสดงคิวการจองโต๊ะ
                    int i = 0;
                    while (i < orderlist.size()) {
                        System.out.println("table :" + (aq.get(i) + 1) + " ");
                        for (String ctm : orderlist.get(i)) {
                            System.out.println("    " + ctm);
                        }
                        i++;
                    }
                    break;

                case "love":
                    System.out.println(pink +   "▄     █  ▄▄▄  ▄   ▄ ▗▞▀▚▖    ▄   ▄  ▄▄▄  █  ▐▌     ▄▄▄  ▄▄▄      ▄▄▄▄  █  ▐▌ ▄▄▄ ▐▌   \n" + //
                                                "▄     █ █   █ █   █ ▐▛▀▀▘    █   █ █   █ ▀▄▄▞▘    ▀▄▄  █   █     █ █ █ ▀▄▄▞▘▀▄▄  ▐▌   \n" + //
                                                "█     █ ▀▄▄▄▀  ▀▄▀  ▝▚▄▄▖     ▀▀▀█ ▀▄▄▄▀          ▄▄▄▀ ▀▄▄▄▀     █   █      ▄▄▄▀ ▐▛▀▚▖\n" + //
                                                "█     █                      ▄   █                                               ▐▌ ▐▌\n" + //
                                                "                              ▀▀▀                                                     "
                                                + resetcolor);
                    break;

                case "exit":    // ออกจากโปรแกรม
                    System.out.println("Thank you");
                    input.close();
                    return;

                case "help":    // แสดงคำสั่งที่ใช้ได้
                    System.out.println(aqua + "Available commands:" + resetcolor);
                    System.out.println("  reserve - Reserve a table");
                    System.out.println("  status  - Show table status");
                    System.out.println("  cancel  - Cancel a reservation");
                    System.out.println("  queue   - Show reservation queue");
                    System.out.println("  exit    - Exit program");
                    break;

                default:    // คำสั่งไม่ถูกต้อง
                    System.out.println("Something is wrong, try again?");
            }
        }
    }

    static void retable(Set<Integer> set, List<List<String>> list, int[] aii) {
        for (int i = 0; i < 10; i++) {
            String status = "";
            String color = resetcolor;
            if (!set.contains(i)) {
                status = "[ ]";
            } else if (ordercheck(list, aii[i])) {
                status = "[-]";
                color = blueviolet;
            } else {
                status = "[x]";
                color = red;
            }
            System.out.print(color + "table " + (i+1) + ": " + status + " " + resetcolor);
        }
        System.out.println();
    }

    static void cancel(Set<Integer> set, List<List<String>> list, int nums, int aii) {
        set.remove(nums);
        list.remove(aii);
    }

    static boolean ordercheck(List<List<String>> list, int aii) {
        try {
            if (list.get(aii).size() < 4) {
                return true;
            }
            return false;
        } catch (Exception e) {return false;}
    }

}