package userservice;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final UserService userService = new UserService(new UserDao());
    private static final Scanner scanner = new Scanner(System.in);

    public static void menu() {
        System.out.println("---Введите номер действия---");
        System.out.println("1. Создать юзера");
        System.out.println("2. Показать всех юзеров");
        System.out.println("3. Обновить");
        System.out.println("4. Удалить");
        System.out.println("5. Выйти");
    }

    private static void createUser() {
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();

        System.out.println("Введите email: ");
        String email = scanner.nextLine();

        System.out.println("Введите возраст: ");
        Integer age = null;
        try {
            age = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {}

        userService.createUser(name, email, age);
        System.out.println("Юзер добавлен");
    }

    private static void listUsers() {
        List<User> users = userService.listUsers();
        if (users.isEmpty()) {
            System.out.println("Список пуст");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void updateUser() {
        System.out.println("Введите ID");
        long id = 0;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {}

        User user = userService.getUserById(id);
        if (user == null) {
            System.out.println("Пользователь не найден");
            return;
        }
        System.out.println("Новое имя: ");
        String name = scanner.nextLine();

        System.out.println("Новый email: ");
        String email = scanner.nextLine();

        System.out.println("Новый возраст: ");
        String ageStr = scanner.nextLine();
        Integer age = null;
        if (!ageStr.isBlank()) {
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {}
        }

        userService.updateUser(user, name, email, age);
        System.out.println("Пользователь обновлён");

    }

    public static void deleteUser() {
        System.out.println("Введите ID");
        long id = 0;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Пользователь не найден");
            return;
        }

        User user = userService.getUserById(id);
        if (user == null) {
            System.out.println("Пользователь не найден");
        }

        userService.deleteUser(user);
        System.out.println("Пользователь удален");
    }



    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            menu();
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> createUser();
                case "2" -> listUsers();
                case "3" -> updateUser();
                case "4" -> deleteUser();
                case "5" -> running=false;
                default -> System.out.println("Некорректный ввод");
            }
        }
    }
}
