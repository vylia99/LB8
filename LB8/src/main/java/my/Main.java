package my;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    List<Patient> patients;
     String fileName;
    public static void main(String[] args) {
        Main prog = new Main();
        prog.run();

    }

    private void run() {
        Logic logic = new Logic();
        InputOutput printAll = new InputOutput();
        List<Patient> temp;

        while (true){
            while (true){
                System.out.println("1: Додати запис до файлу");
                System.out.println("2: Вивести зміст файлу на екран");
                System.out.println("3: Вивести список пацієнтів, які мають указаний діагноз в порядку зростання номерів медичної картки");
                System.out.println("4: Вивести список пацієнтів, номер медичної карти у яких знаходиться в заданому інтервалі");
                System.out.println("5: Вивести кількість та список пацієнтів, номер телефона яких починається з вказаної цифри");
                System.out.println("6: Вивести список діагнозів пацієнтів (без повторів) із вказанням кількості пацієнтів, " +
                        "що мають цей діагноз у порядку спадання цієї кількості.");
                System.out.println("7: Вивести список діагнозів пацієнтів, зареєстрованих у системі без повторів");
                System.out.println("8: для кожного діагнозу визначити кількість пацієнтів, яким він поставлений");
                System.out.println("9: Знайти пацієнтів");
                System.out.println("0: Вийти з програми");

                Scanner sc = new Scanner(System.in);
                int k = sc.nextInt();
                int q;
                File file = null;
                switch (k){
                    case 0:
                        System.out.println("Роботу завершенно");
                        System.exit(0);
                        break;
                    case 1:
                        System.out.println("Введіть кількість паціентів");
                        q = sc.nextInt();
                        temp = logic.addToList(q);
                        System.out.println("Введіть назву файлу");
                        fileName = sc.next();
                        Path path = Paths.get(fileName);
                        if (!Files.exists(path, new LinkOption[0])){
                            logic.writeToFile(path, temp);
                        }   else {
                            logic.addToFile(path, temp);}
                        break;
                    case 2:
                        System.out.println("Введіть назву файлу");
                        fileName = sc.next();
                        System.out.println("Зміст файлу");
                        patients = logic.readFromFile(fileName);
                        printAll.printPatient(patients);
                        break;
                    case 3:
                        System.out.println("Введіть назву файлу");
                        fileName = sc.next();
                        patients = logic.readFromFile(fileName);
                        printAll.printFilterDiagnosis(patients);
                        break;
                    case 4:
                        System.out.println("Введіть назву файлу");
                        fileName = sc.next();
                        patients = logic.readFromFile(fileName);
                        printAll.printFilterNumberMedCart(patients);
                        break;
                    case  5:
                        System.out.println("Введіть назву файлу");
                        fileName = sc.next();
                        patients = logic.readFromFile(fileName);
                        printAll.printFilterPhone(patients);
                        break;
                    case 6:
                        System.out.println("Введіть назву файлу");
                        fileName = sc.next();
                        patients = logic.readFromFile(fileName);
                        printAll.printDiagnosis(patients);
                        break;
                    case 7:
                        System.out.println("Введіть назву файлу");
                        fileName = sc.next();
                        patients = logic.readFromFile(fileName);
                        printAll.printDiagnosisRegist(patients);
                        break;
                    case 8:
                        System.out.println("Введіть назву файлу");
                        fileName = sc.next();
                        patients = logic.readFromFile(fileName);
                        printAll.printDiagnosisCount(patients);
                        break;
                    case 9:
                        System.out.println("Введіть назву файлу");
                        fileName = sc.next();
                        patients = logic.readFromFile(fileName);
                        printAll.printSearchBySurname(patients);

                        System.out.println("Бажаєте видалити пацієнта?");
                            while (true) {
                                System.out.println("1: Так");
                                System.out.println("2: Ні");
                                Scanner s1 = new Scanner(System.in);
                                int ks = sc.nextInt();
                                switch (ks) {
                                    case 1:
                                        System.out.println("Введіть прізвище для пошуку");
                                        String pr = s1.next();
                                        Patient pat=logic.searchBySurname(patients,pr);
                                        logic.removeBySurname(patients,pat);
                                        System.out.println("Введіть назву файлу");
                                        fileName = s1.next();
                                        path = Paths.get(fileName);
                                        logic.writeToFile(path,patients);
                                        System.out.println("Зміни збереженно");
                                        break;
                                    case 2:
                                        System.out.println("Зміни скасовано");
                                        break;
                                }break;
                            }

                }   }

        }
    }


}