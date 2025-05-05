package com.example.student_portal.scheduler;

import com.example.student_portal.entity.MonthlyReport;
import com.example.student_portal.entity.Result;
import com.example.student_portal.entity.Student;
import com.example.student_portal.repository.AttendanceRepository;
import com.example.student_portal.repository.MonthlyReportRepository;
import com.example.student_portal.repository.ResultRepository;
import com.example.student_portal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final StudentRepository studentRepo;
    private final AttendanceRepository attendanceRepo;
    private final MonthlyReportRepository monthlyRepo;
    private final ResultRepository resultRepo;

    // Run on last day of each month at 11:59 PM
//    @Scheduled(cron = "* * * * * *", zone = "Asia/Kolkata")
    public void generateMonthlyReports() {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.of(now.getYear(), now.getMonth());
        int month = currentMonth.getMonthValue();
        int year = currentMonth.getYear();
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        List<Student> students = studentRepo.findAll();
        List<MonthlyReport> reports = new ArrayList<>();

        for (Student student : students) {
            long attendanceCount = attendanceRepo.countByStudentIdAndDateBetweenAndPresentTrue(
                    student.getId(), start.atStartOfDay(), end.atTime(LocalTime.MAX)
            );

            double totalPercentage = resultRepo.findByStudentId(student.getId()).stream()
                    .mapToDouble(Result::getPercentage)
                    .average().orElse(0.0);

            int performancePoints = getPointFromPercentage(totalPercentage);
            int attendancePoints = (int) attendanceCount; // 1⭐ per lecture
            int totalPoints = performancePoints + attendancePoints;

            // Optional: Send report via email
            System.out.printf("Monthly Report for %s: Attendance=%d, PerformancePoints=%d, Total=%d%n",
                    student.getFullName(), attendanceCount, performancePoints, totalPoints);

            MonthlyReport report = MonthlyReport.builder()
                    .studentId(student.getId())
                    .fullName(student.getFullName())
                    .month(month)
                    .year(year)
                    .totalLecturesAttended((int) attendanceCount)
                    .attendancePoints(attendancePoints)
                    .avgPercentage(totalPercentage)
                    .performancePoints(performancePoints)
                    .totalPoints(totalPoints)
                    .generatedAt(LocalDateTime.now())
                    .monthlyRank(1).build();

            reports.add(report);

            // Save all reports first
            monthlyRepo.saveAll(reports);

            // Fetch back & sort by total points to assign rank
            List<MonthlyReport> sortedReports = monthlyRepo.findByMonthAndYear(month, year).stream()
                    .sorted(Comparator.comparingInt(MonthlyReport::getTotalPoints).reversed())
                    .collect(Collectors.toList());

            for (int i = 0; i < sortedReports.size(); i++) {
                MonthlyReport r = sortedReports.get(i);
                r.setMonthlyRank(i + 1);
            }

            monthlyRepo.saveAll(sortedReports);
        }
    }

    private int getPointFromPercentage(double percentage) {
        if (percentage >= 90) return 5;
        else if (percentage >= 75) return 4;
        else if (percentage >= 35) return 3;
        else return 0;
    }

    // Every minute
//    @Scheduled(cron = "1 * * * * *")  // At 0 seconds of every minute
//    public void runEveryMinute() {
//        System.out.println(">>> Running task every MINUTE");
//    }


}