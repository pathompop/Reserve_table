
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class Booking{

    boolean checkduplicate(Set<String> set, String name) {
        if (set.contains(name)) {
            return true;
        } else {
            return false;
        }
    }

    String getNo (String order) {
        String a = "";
        int b = 0;
        for (int i = 0; i < order.length(); i++) {
            if (order.charAt(i) == ' ') {
                b++;
                if (b == 3) {
                    break;
                }
                a = "";
            } else {
                a += order.charAt(i);
            }
        }
        return a;
    }

    String getDouble (String order) {
        String a = "";
        int b = 0;
        for (int i = 0; i < order.length(); i++) {
            if (order.charAt(i) == ' ' && b < 2) {
                b++;
                a = "";
            } else {
                a += order.charAt(i);
            }
        }
        return a;
    }
}

public class add {

    static String resetcolor = "\u001B[0m";
    static String red = "\u001B[38;2;255;0;0m";
    static String green = "\u001B[38;2;0;255;0m";
    static String pink = "\u001B[38;2;255;105;180m";
    static String aqua = "\u001B[38;2;0;255;255m";
    static String blueviolet = "\u001B[38;2;95;0;255m";
    public static void main(String[] args) {
        List<String> orderlist = new ArrayList<>();
        Set<String> dupset = new HashSet<>();
        Set<String> doubleSet = new HashSet<>();
        Scanner input = new Scanner(System.in);
        Booking booking = new Booking();

        int id = 2547;

        while (true) {
            System.out.print("command : ");
            String cmd = input.nextLine();

            switch (cmd) {

                case "add" :
                    System.out.print("Name :");
                    String name = input.nextLine();
                    System.out.print("No. :");
                    int no = input.nextInt();
                    input.nextLine(); // Consume the newline character
                    System.out.print("Date :");
                    String date = input.nextLine();
                    System.out.print("Time :");
                    String time = input.nextLine();
                    if (booking.checkduplicate(dupset,"#" + id + " "+ name+" " + no + " " + date + " " + time)) {
                        System.out.println(red + "duplicate booking" + resetcolor);
                        id--;
                    } else {
                        id++;
                        orderlist.add("#" + id + " " + name + " " + no + " " + date + " " + time);
                        System.out.println(green + "#ID : " + id + resetcolor);
                        System.out.println(green + "No. : " + no + resetcolor);
                        System.out.println(green + "Name : " + name + resetcolor);
                        System.out.println(green + "Date : " + date + resetcolor);
                        System.out.println(green + "Time : " + time + resetcolor);
                        if (doubleSet.contains(no + " " + date + " " + time)) {
                            System.out.println(aqua + "Double Booking" + resetcolor);
                        }
                        dupset.add("#" + id + " " + name + " " + no + " " + date + " " + time);
                        doubleSet.add(no + " " + date + " " + time);
                    }
                    break;

                    case "queue" :
                        System.out.print("No. : ");
                        String queueNo = input.nextLine();
                        if (orderlist.isEmpty()) {
                            System.out.println(red + "No orders in the queue." + resetcolor);
                        } else {
                            for (String order : orderlist) {
                                if (booking.getNo(order).equals(queueNo)) {
                                    System.out.println(blueviolet + order + resetcolor);
                                    break;
                                }
                            }
                        }
                        break;

                    case "cancel" :
                        System.out.print("ID : ");
                        String cancelId = input.nextLine();
                        boolean found = false;
                        for (String order : orderlist) {
                            if (order.startsWith("#" + cancelId)) {
                                orderlist.remove(order);
                                dupset.remove(order);
                                doubleSet.remove(booking.getDouble(order));
                                System.out.println(green + "ID: #" + cancelId + "  cancelled." + resetcolor);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println(red + "Order #" + cancelId + " not found." + resetcolor);
                        }
                        break;

                    case "exit" :
                        System.out.println("Exiting the program.");
                        input.close();
                        return;

                    default:
                        System.out.println(pink + "Invalid command. Please try again." + resetcolor);
                        break;
            }

        }
    }

    // static boolean checkduplicate(Set<String> set, String name) {
    //     if (set.contains(name)) {
    //         return true;
    //     } else {
    //         set.add(name);
    //         return false;
    //     }
    // }
}