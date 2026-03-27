//глава8
//вариант 1
//3 задание: Проверить, является ли строка сильным паролем. Пароль считается сильным, если его длина больше либо равна 10 символам, он содержит как минимум
//одну цифру, одну букву в верхнем и одну букву в нижнем регистре. Пароль может содержать только латинские буквы и/или цифры, а также символ «_».

import java.util.Scanner;

public class Var_C {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите пароль для проверки: ");
        String password = scanner.nextLine();

        boolean isStrong = checkPasswordStrength(password);
        if (isStrong) {
            System.out.println("Пароль сильный!");
        } else {
            System.out.println("Пароль слабый!");
            System.out.println("\nНе выполнены следующие требования:");
            showPasswordRequirements(password);
        }
        scanner.close();
    }

    public static boolean checkPasswordStrength(String password) {
        boolean lengthOk = checkLength(password);// длина >= 10
        boolean hasDigit = checkHasDigit(password);// есть цифра
        boolean hasUpper = checkHasUpperCase(password);// есть заглавная буква
        boolean hasLower = checkHasLowerCase(password);// есть строчная буква
        boolean charsOk = checkAllowedChars(password);// только разрешенные символы

        //пароль сильный, если все условия выполнены
        return lengthOk && hasDigit && hasUpper && hasLower && charsOk;
    }

    public static boolean checkLength(String password) {
        return password.length() >= 10;
    }

    public static boolean checkHasDigit(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkHasUpperCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkHasLowerCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkAllowedChars(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            //проверяем, является ли символ разрешенным
            boolean isLetter = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
            boolean isDigit = (c >= '0' && c <= '9');
            boolean isUnderscore = (c == '_');

            if (!isLetter && !isDigit && !isUnderscore) {
                return false; //нашли неразрешенный символ
            }
        }
        return true; //все символы разрешены
    }
    //какие требования не выполнены
    public static void showPasswordRequirements(String password) {
        if (!checkLength(password)) {
            System.out.println("длина пароля меньше 10 символов");
        }
        if (!checkHasDigit(password)) {
            System.out.println("нет ни одной цифры");
        }
        if (!checkHasUpperCase(password)) {
            System.out.println("нет ни одной заглавной буквы (A-Z)");
        }
        if (!checkHasLowerCase(password)) {
            System.out.println("нет ни одной строчной буквы (a-z)");
        }
        if (!checkAllowedChars(password)) {
            System.out.println("содержит недопустимые символы (разрешены: A-Z, a-z, 0-9, _)");
            showInvalidChars(password);
        }
    }

    public static void showInvalidChars(String password) {
        System.out.print("Недопустимые символы: ");
        boolean found = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            boolean isLetter = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
            boolean isDigit = (c >= '0' && c <= '9');
            boolean isUnderscore = (c == '_');

            if (!isLetter && !isDigit && !isUnderscore) {
                System.out.print("'" + c + "' ");
                found = true;
            }
        }
        if (!found) {
            System.out.print("нет");
        }
        System.out.println();
    }
}