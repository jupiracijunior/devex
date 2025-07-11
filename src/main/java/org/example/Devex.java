package org.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;

@Command(name = "devex", description = "Comando para gerenciamento de datas")
public class Devex implements Callable<Integer> {
    private LocalDateTime currentDateTime;

    @Option(names = {"-c", "--compareTo"}, arity = "1", description = "Compara o valor de data e hora com o horario atual")
    private String compare;

    @Option(names = {"-f", "--format"}, description = "Define a fomatacao da data e hora")
    private String dataTimeFormat = "dd-MM-yyyy HH-mm-ss";

    @Override
    public Integer call() throws Exception {
        if (getCompare() != null) {

            LocalDateTime ldt = differFullDate(getCompare());

            if ((ldt.compareTo(LocalDateTime.now()) > 0)) {
                System.out.println(getCompare() + " e maior que a data atual");
            } else {
                System.out.println(getCompare() + " e menor que a data atual");
            }
            return 0;
        }

        this.currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(getDataTimeFormat());
        System.out.println(dateFormat.format(getCurrentDateTime()));
        return 0;
    }

    // Business inteligence part
    public LocalDateTime differFullDate(String fullDate) {
        String[] date = returnDate(fullDate);
        String[] time = returnTime(fullDate);

        LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]),
                Integer.parseInt(date[0]), Integer.parseInt(time[2]), Integer.parseInt(time[1]),
                Integer.parseInt(time[0]));

        return ldt;
    }

    public String[] returnDate(String fullDate) {
        String[] date = fullDate.split(" ");
        return date[0].split("-");
    }

    public String[] returnTime(String fullDate) {
        String[] time = fullDate.split(" ");
        return time[1].split("-");
    }

    // Getters
    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public String getDataTimeFormat() {
        return dataTimeFormat;
    }

    public String getCompare() {
        return compare;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Devex()).execute(args);
        System.exit(exitCode);
    }
}