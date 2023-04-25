package my;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;



public class Logic {
    public Logic() {
    }

    public List <Patient> readFromFile(String file){
        List <Patient> patients = new ArrayList<>(100);
        String str="";
        Path path = Paths.get(file);
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNext()) {
                str = scanner.nextLine();
                String[] strs = str.trim().split("\\|");
                patients.add(new Patient(Integer.parseInt(strs[0]), strs[1], strs[2], strs[3],
                        strs[4], strs[5], Integer.parseInt(strs[6]), strs[7]));
            }

        }catch (IOException ignored) {
        }
        return patients;
    }
    public void writeToFile(Path file, List<Patient> patients){
        try (BufferedWriter bfr = Files.newBufferedWriter(file)){
            for (Patient p: patients){
                bfr.write(p.createString());
            }
        }catch (IOException ignored){
        }
    }
    public void addToFile(Path file, List<Patient> patients){
        try (BufferedWriter bfr= Files.newBufferedWriter(file, StandardOpenOption.APPEND)){
            for (Patient p: patients){
                bfr.write(p.createString());
            }
        }catch (IOException ignored){
        }
    }
    public List<Patient> addToList(int q){
        List<Patient> patients = IntStream.range(0, q).mapToObj(i -> scannerPatient()).collect(Collectors.toList());

        return patients;
    }
    public List<Patient> filterDiagnosis(List<Patient> patients, String diagnosis) { //указаний діагноз в порядку зростання номерів медичної картки
        List<Patient> temp = patients.stream().filter(p -> Objects.equals(p.getDiagnosis(), diagnosis))
                .sorted(Comparator.comparingInt(Patient::getNumberMedCart)).collect(Collectors.toList());

        return temp;

    }
    public List<Patient> filterNumberMedCart(List<Patient> patients, int numberLow, int numberUp) { //номер медичної карти у яких знаходиться в заданому інтервалі
        List<Patient> temp = patients.stream().filter(p -> numberLow <= p.getNumberMedCart() && numberUp >= p.getNumberMedCart())
                .collect(Collectors.toList());

        return temp;
    }
    public List<Patient> filterPhone(List<Patient> patients, int phone) { //фільтер номер телефона яких починається з вказаної цифри
        List<Patient> temp = patients.stream().filter(p -> p.getPhone().charAt(0) - '0' == phone)
                .collect(Collectors.toList());

        return temp;
    }
    public List<String> filterDiagnosis(List<Patient> patients){ //список діагнозів пацієнтів із вказанням кількості пацієнтів
        Map<String, Integer> diagnosis = new HashMap<>();

        patients.stream().map(Patient::getDiagnosis).forEach(diagnoz -> diagnosis.put(diagnoz, diagnosis.getOrDefault(diagnoz, 0) + 1));
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(diagnosis.entrySet());
        sorted.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        List<String> temp = sorted.stream().map(entry -> entry.getKey() + " - " + entry.getValue())
                .collect(Collectors.toCollection(() -> new ArrayList<>(0)));

        return temp;
    }
    public List<String> filterDiagnosisRegist(List<Patient> patients){ //список діагнозів пацієнтів, зареєстрованих у системі без повторів

        return patients.stream().flatMap(patient -> Stream.of(patient.getDiagnosis()))
                .distinct().collect(Collectors.toList());
    }

    public List<String> filterDiagnosisCount(List<Patient> patients){ //список діагнозів пацієнтів із вказанням кількості пацієнтів
        List<String> diagnosisCount = new ArrayList<>();
        boolean d=false;
        int j=0;
        for (Patient patient : patients) {
            String diagnoz = patient.getDiagnosis();

            IntStream.range(0, j).anyMatch(p -> diagnosisCount.get(p).equals(diagnoz));
            if (!d) {
                diagnosisCount.set(j++, diagnoz);
            }
        }

        return diagnosisCount;
    }

    public Patient scannerPatient(){
        Scanner s = new Scanner(System.in);
        System.out.println("Введіть id");
        int id = s.nextInt();
        System.out.println("Введіть прізвище");
        String surname = s.next();
        System.out.println("Введіть ім'я");
        String name = s.next();
        System.out.println("Введіть ім'я по-батькові");
        String fatherName = s.next();
        System.out.println("Введіть адресу");
        String address = s.next();
        System.out.println("Введіть номер телефону");
        String phone = s.next();
        System.out.println("Введіть номер медичної картки");
        int numberMedCart = s.nextInt();
        System.out.println("Введіть діагноз");
        String diagnosis = s.next();
        return new Patient(id, surname, name, fatherName, address, phone, numberMedCart, diagnosis);
    }

}
