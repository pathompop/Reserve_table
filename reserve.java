import java.util.*;

public class reserve {

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        List<String> list = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        while(true) {
            System.out.print("command : ");
            String command = input.nextLine();

            if (command.equals("reserve")) {
                retable(set);
                    System.out.print("order : ");
                    int order = input.nextInt();
                    if (!set.contains(order)) {
                    set.add(order - 1);
                    input.nextLine();


                    for (int i = 0; i < 4; i++) {
                        System.out.print("custommer " + (i+1) + ": ");
                        String custommer = input.nextLine();
                        if (custommer.equals("exit")) {break;}
                        list.add(order*4 + i, custommer);   // error
                    }
                    System.out.println(list);
                }
            } else if (command.equals("status")) {
                retable(set);
                char c = command.charAt(command.length()-1);
                if (Character.isDigit(c)) {
                    int num = Character.getNumericValue(c);
                    for (int i = num*4; i < num*4 + 4; i++) {
                        System.out.println(list.get(i));
                    }
                }
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
}