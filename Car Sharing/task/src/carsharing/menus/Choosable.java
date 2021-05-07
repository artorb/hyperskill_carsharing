package carsharing.menus;

import java.util.List;
import java.util.Scanner;

public interface Choosable extends Runnable {

    private static void handleOption(List<MenuEntity> menu, int option) {
        if (option < 0 || option > menu.size() - 1)
            System.out.println("Not a valid menu option: " + option);
        else
            menu.get(option).run();
    }

    static void display(List<MenuEntity> menu) {
        menu.stream().skip(1).forEach(item -> System.out.println(item.getOption() + ". " + item.getTitle()));
        menu.stream().findFirst().ifPresent(exitOption -> System.out.println(exitOption.getOption() + ". " + exitOption.getTitle()));
//        for (MenuEntity item : menu) {
//            System.out.println(item.getOption() + ". " + item.getTitle());
//        }
        int option = 0;
        try (Scanner scan = new Scanner(System.in)) {
            option = scan.nextInt();
            handleOption(menu, option);
        } catch (Exception e) {
            System.out.println("Not a valid menu option: " + option);
            return;
        }
        System.out.flush();
    }
}
