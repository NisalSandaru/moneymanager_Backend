package com.nisal.moneymanager.controller;

import com.nisal.moneymanager.service.EmailService;
import com.nisal.moneymanager.service.ExcelService;
import com.nisal.moneymanager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final ExcelService excelService;
    private final ProfileService profileService;

    @PostMapping("/income-excel")
    public ResponseEntity<String> sendIncomeExcelEmail() {

        System.out.println("Income Excel Email");
        // Get current user's email
        var profile = profileService.getCurrentProfile();
        String toEmail = profile.getEmail();

        // Generate Excel file
        byte[] excelBytes = excelService.generateCurrentMonthIncomeExcel();

        // Send email
        emailService.sendIncomeExcelWithAttachment(
                toEmail,
                "Your Monthly Income Report",
                "<p>Hello <b>" + profile.getFullName() + "</b>,</p>"
                        + "<p>Attached is your income report for this month.</p>"
                        + "<p>Regards,<br>Money Manager</p>",
                excelBytes
        );

        return ResponseEntity.ok("Email sent successfully to " + toEmail);
    }

    @PostMapping("/expense-excel")
    public ResponseEntity<String> sendExpenseExcelEmail() {

        System.out.println("Expense Excel Email");
        // Get current user's email
        var profile = profileService.getCurrentProfile();
        String toEmail = profile.getEmail();

        // Generate Excel file
        byte[] excelBytes = excelService.generateCurrentMonthExpenseExcel();

        // Send email
        emailService.sendExpenseExcelWithAttachment(
                toEmail,
                "Your Monthly Expense Report",
                "<p>Hello <b>" + profile.getFullName() + "</b>,</p>"
                        + "<p>Attached is your expense report for this month.</p>"
                        + "<p>Regards,<br>Money Manager</p>",
                excelBytes
        );

        return ResponseEntity.ok("Email sent successfully to " + toEmail);
    }
}
