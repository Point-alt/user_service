package userservice;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final UserDao userdao = new UserDao();
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

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);

        userdao.save(user);
        System.out.println("Юзер " + user + " добавлен");
    }

    private static void listUsers() {
        List<User> users = userdao.getAll();
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

        User user = userdao.getById(id);
        if (user == null) {
            System.out.println("Пользователь не найден");
            return;
        }
        System.out.println("Новое имя: ");
        String name = scanner.nextLine();
        if (!name.isBlank()) {
            user.setName(name);
        }
        System.out.println("Новый email: ");
        String email = scanner.nextLine();
        if (!email.isBlank()) {
            user.setEmail(email);
        }
        System.out.println("Новый возраст: ");
        String ageStr = scanner.nextLine();
        if (!ageStr.isBlank()) {
            try {
                user.setAge(Integer.parseInt(ageStr));
            } catch (NumberFormatException e) {}
        }


        userdao.update(user);
        System.out.println("Пользователь " + user + " обновлен");
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

        User user = userdao.getById(id);

        userdao.delete(user);
        System.out.println("Пользователь " + user + " удален");
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
