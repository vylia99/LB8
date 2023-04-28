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
    //список діагнозів пацієнтів із вказанням кількості пацієнтів
    public Map<String, Long> filterDiagnosis(List<Patient> patients){
        Map<String, Long> diagnosis=filterDiagnosisCount(patients);

        List<Map.Entry<String, Long>> sorted = new ArrayList<>(diagnosis.entrySet());
        sorted.sort(Map.Entry.<String, Long>comparingByValue().reversed());
        Map<String, Long> temp = sorted
                .stream().collect(Collectors
                        .toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2)->e1,LinkedHashMap::new));

        return temp;

    }
    //список діагнозів пацієнтів, зареєстрованих у системі без повторів
    public List<String> filterDiagnosisRegist(List<Patient> patients){

        return patients.stream().flatMap(patient -> Stream.of(patient.getDiagnosis()))
                .distinct().collect(Collectors.toList());
    }

    //список діагнозів пацієнтів із вказанням кількості пацієнтів
    public Map<String, Long> filterDiagnosisCount(List<Patient> patients ){

        return patients.stream()
                .collect(Collectors
                        .groupingBy(Patient::getDiagnosis,Collectors
                                .counting()));
    }
    //пошук у списку по прізвищю
    public Patient searchBySurname(List<Patient> patients, String surname) {
        return patients.stream().filter(patient -> patient.getSurname()
                .equals(surname)).findFirst().orElse(null);


    }
    //видалення
    public  List<Patient> removeBySurname(List<Patient> patients, Patient patient) {
        patients.remove(patient);

        return patients;
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
