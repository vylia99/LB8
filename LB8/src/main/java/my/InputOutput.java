package my;

import java.util.*;

public class InputOutput {
    ArrayList<Patient> patients;
    Logic logic;


    public InputOutput() {
        logic = new Logic();
    }
    public void printPatient(List<Patient> patients) {
        for (Patient p: patients) {
            System.out.println(p);
        }
    }

    public void printFilterDiagnosis(List<Patient> patients) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть діагноз");
        String st = scanner.nextLine();
        System.out.println(st);
        printPatient(logic.filterDiagnosis(patients, st));
    }

    public void printFilterNumberMedCart(List<Patient> patients) {
        Scanner c = new Scanner(System.in);
        System.out.println("Введіть початок інтервалу");
        int numLow = c.nextInt();
        System.out.println("Введіть кінець інтервалу");
        int numUp = c.nextInt();
        printPatient(logic.filterNumberMedCart(patients,numLow, numUp));
    }

    public void printFilterPhone(List<Patient> patients) {
        Scanner s = new Scanner(System.in);
        System.out.println("Введіть початкову цифру номеру телефону");
        int f = s.nextInt();
        List<Patient> list = logic.filterPhone(patients, f);
        System.out.println(list.size());
        printPatient(list);
    }

    public void printDiagnosis(List<Patient> patients) {
        Map <String, Long> diagnosis=logic.filterDiagnosis(patients);
        printMap(diagnosis);
          }

    public void printDiagnosisRegist(List<Patient> patients) {
        System.out.println(logic.filterDiagnosisRegist(patients));
    }

    public void printDiagnosisCount(List<Patient>patients){
        Map <String, Long> diagnosisCount=logic.filterDiagnosisCount(patients);
        printMap(diagnosisCount);
    }

    private static void printMap(Map<String, Long> diagnosisCount) {
        for (Map.Entry q: diagnosisCount.entrySet()){
            System.out.println("key: "+q.getKey()+" count: "+q.getValue());
        }
    }
    public void printSearchBySurname(List<Patient> patients){
        Scanner s = new Scanner(System.in);
        System.out.println("Введіть прізвище для пошуку");
        String sm = s.nextLine();
        Patient patient = logic.searchBySurname(patients, sm);
        System.out.println("Результат пошуку:");
        System.out.println(patient);
    }

}