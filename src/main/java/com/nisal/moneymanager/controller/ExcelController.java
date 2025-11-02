package com.nisal.moneymanager.controller;

import com.nisal.moneymanager.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/download/income")
    public ResponseEntity<byte[]> downloadCurrentMonthIncomeExcel() {
        byte[] excelData = excelService.generateCurrentMonthIncomeExcel();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=income_details.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excelData);
    }

    @GetMapping("/download/expense")
    public ResponseEntity<byte[]> downloadCurrentMonthExpenseExcel() {
        byte[] excelData = excelService.generateCurrentMonthExpenseExcel();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=expense_details.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excelData);
    }


}
