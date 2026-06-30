/*
 * CSC 208 Assignment - Object-Oriented Programming
 * Name: Ngullah Mudana Joshua
 * Matric Number: CSC/24/0000
 *
 * Description:
 * This program models a simple student grading system using the principles
 * of Object-Oriented Programming (OOP). It records each student's name and
 * the courses they took, including the course units and scores. It then
 * computes the corresponding letter grade and grade point for each course,
 * and calculates the student's cumulative GPA using the standard formula:
 *
 *     GPA = SUM(Grade Point × Course Unit) / SUM(Course Units)
 *
 * It also exports all the results to a CSV file
 * (grading_report.csv), which can be opened directly in Microsoft Excel/Google Sheets.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GradingSystem {

    // ---------- Course class: represents one course taken by a student ----------
    static class Course {
        String courseName;
        int unit;
        double score;

        Course(String courseName, int unit, double score) {
            this.courseName = courseName;
            this.unit = unit;
            this.score = score;
        }

        // Convert numeric score to letter grade based on department scale
        String getLetterGrade() {
            if (score >= 70) return "A";
            else if (score >= 60) return "B";
            else if (score >= 50) return "C";
            else if (score >= 45) return "D";
            else if (score >= 40) return "E";
            else return "F";
            }

        // Convert letter grade to grade point (5-point scale)
        double getGradePoint() {
            switch (getLetterGrade()) {
                case "A": return 5.0;
                case "B": return 4.0;
                case "C": return 3.0;
                case "D": return 2.0;
                case "E": return 1.0;
                default:  return 0.0; // F
            }
        }
    }

    // ---------- Student class: represents one student and their courses ----------
    static class Student {
        String name;
        String matricNumber;
        List<Course> courses = new ArrayList<>();

        Student(String name, String matricNumber) {
            this.name = name;
            this.matricNumber = matricNumber;}

        void addCourse(String courseName, int unit, double score) {
            courses.add(new Course(courseName, unit, score));}

        // Calculate cumulative GPA: sum(gradePoint * unit) / sum(unit)
        double calculateGPA() {
            double totalPoints = 0.0;
            int totalUnits = 0;

            for (Course c : courses) {
                totalPoints += c.getGradePoint() * c.unit;
                totalUnits += c.unit;}

            if (totalUnits == 0) return 0.0;
            return totalPoints / totalUnits;
        }
    }

    public static void main(String[] args) {

        List<Student> students = new ArrayList<>();

        // ---------- Student(Myself) ----------
        Student s1 = new Student("Ngullah Mudana Joshua", "CSC/24/0000");
        s1.addCourse("CSC 202 - Comparative Programming Language", 3, 80);
        s1.addCourse("CSC 204 - Assembly Language Programming", 3, 75);
        s1.addCourse("CSC 208 - Object-Oriented Programming", 2, 78);
        s1.addCourse("STA 112 - Statistics for Computing", 3, 74);
        students.add(s1);

        //---------- Pseudo names (Just randonm names ) ----------
        Student s2 = new Student("Niyi Adeniyi Esosa", "CYS/24/9001");
        s2.addCourse("CSC 202 - Comparative Programming Language", 4, 68);
        s2.addCourse("CSC 204 - Assembly Language Programming", 3, 62);
        s2.addCourse("CSC 208 - Object-Oriented Programming", 2, 48);
        s2.addCourse("STA 112 - Statistics for Computing", 3, 71);
        students.add(s2);

        Student s3 = new Student("Baks Tunde Bakare", "CSC/24/9002");
        s3.addCourse("CSC 202 - Comparative Programming Language", 3, 78);
        s3.addCourse("CSC 204 - Assembly Language Programming", 3, 65);
        s3.addCourse("CSC 208 - Object-Oriented Programming", 2, 54);
        s3.addCourse("STA 112 - Statistics for Computing", 3, 46);
        students.add(s3);

        Student s4 = new Student("Ahmodu Ahmed Bulaba", "BAT/24/0001");
        s4.addCourse("CSC 202 - Comparative Programming Language", 3, 22);
        s4.addCourse("CSC 204 - Assembly Language Programming", 3, 38);
        s4.addCourse("CSC 208 - Object-Oriented Programming", 2, 46);
        s4.addCourse("STA 112 - Statistics for Computing", 3, 51);
        students.add(s4);

        Student s5 = new Student("Remi Oluremi Balabulu", "RAT/24/0002");
        s5.addCourse("CSC 202 - Comparative Programming Language", 3, 58);
        s5.addCourse("CSC 204 - Assembly Language Programming", 3, 75);
        s5.addCourse("CSC 208 - Object-Oriented Programming", 2, 26);
        s5.addCourse("STA 112 - Statistics for Computing", 3, 34);
        students.add(s5);

        //---------- Print report to terminal ----------
        printConsoleReport(students);

        //---------- Write report to CSV ----------
        writeCSVReport(students, "grading_report.csv");
    }

    //Prints a readable report to the terminal
    static void printConsoleReport(List<Student> students) {
        System.out.println("=================================================");
        System.out.println("      CSC 208 - OBJECT-ORIENTED PROGRAMMING           ");
        System.out.println("=================================================\n");

        for (Student s : students) {
            System.out.println("Student Name : " + s.name);
            System.out.println("Matric No.   : " + s.matricNumber);
            System.out.println("-------------------------------------------------");
            System.out.printf("%-30s %-6s %-6s %-6s %-6s%n",
                    "Course", "Unit", "Score", "Grade", "Point");

            for (Course c : s.courses) {
                System.out.printf("%-30s %-6d %-6.1f %-6s %-6.1f%n",
                        c.courseName, c.unit, c.score,
                        c.getLetterGrade(), c.getGradePoint());
            }

            System.out.printf("Cumulative GPA: %.2f%n", s.calculateGPA());
            System.out.println("=================================================\n");
        }
    }

    static void writeCSVReport(List<Student> students, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {

            //CSV header row
            writer.append("Name,Matric Number,Course,Unit,Score,Grade,Grade Point,Cumulative GPA\n");

            for (Student s : students) {
                double gpa = s.calculateGPA();

                for (Course c : s.courses) {
                    writer.append(escapeCSV(s.name)).append(",");
                    writer.append(escapeCSV(s.matricNumber)).append(",");
                    writer.append(escapeCSV(c.courseName)).append(",");
                    writer.append(String.valueOf(c.unit)).append(",");
                    writer.append(String.valueOf(c.score)).append(",");
                    writer.append(c.getLetterGrade()).append(",");
                    writer.append(String.valueOf(c.getGradePoint())).append(",");
                    writer.append(String.format("%.2f", gpa)).append("\n");
                }
            }

            writer.flush();
            System.out.println("CSV report successfully written to: " + fileName);

        } catch (IOException e) {
            System.out.println("Error writing CSV file: " + e.getMessage());
        }}

    static String escapeCSV(String field) {
        if (field.contains(",")) {
            return "\"" + field + "\"";
        }
        return field;
    }
}
