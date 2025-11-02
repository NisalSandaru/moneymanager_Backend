package com.nisal.moneymanager.service;

import com.nisal.moneymanager.entity.ExpenseEntity;
import com.nisal.moneymanager.entity.IncomeEntity;
import com.nisal.moneymanager.entity.ProfileEntity;
import com.nisal.moneymanager.repository.ExpenseRepository;
import com.nisal.moneymanager.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final ProfileService profileService;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public byte[] generateCurrentMonthIncomeExcel() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        // Fetch only this month's incomes
        List<IncomeEntity> incomes = incomeRepository.findByProfileIdAndDateBetween(
                profile.getId(), startDate, endDate
        );

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("This Month Income");

            // Header
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "Name", "Category", "Amount", "Date"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Data rows
            int rowIdx = 1;
            for (IncomeEntity income : incomes) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(income.getId());
                row.createCell(1).setCellValue(income.getName());
                row.createCell(2).setCellValue(income.getCategory().getName());
                row.createCell(3).setCellValue(income.getAmount().doubleValue());
                row.createCell(4).setCellValue(income.getDate().toString());
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }
    }

    public byte[] generateCurrentMonthExpenseExcel() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        // Fetch only this month's incomes
        List<ExpenseEntity> expenses = expenseRepository.findByProfileIdAndDateBetween(
                profile.getId(), startDate, endDate
        );

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("This Month Expense");

            // Header
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "Name", "Category", "Amount", "Date"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Data rows
            int rowIdx = 1;
            for (ExpenseEntity expense : expenses) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(expense.getId());
                row.createCell(1).setCellValue(expense.getName());
                row.createCell(2).setCellValue(expense.getCategory().getName());
                row.createCell(3).setCellValue(expense.getAmount().doubleValue());
                row.createCell(4).setCellValue(expense.getDate().toString());
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }
    }


}
