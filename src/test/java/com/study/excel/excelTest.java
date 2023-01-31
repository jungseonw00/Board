package com.study.excel;

import com.study.CarExcelDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

public class excelTest {

	@Test
	void excelTest() {
		try {
			// 엑셀 파일 만듬
			Workbook workbook = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
			// 시트 생성
			Sheet sheet = workbook.createSheet();
			// 임시 데이터
			CarExcelDto carExcelDto = new CarExcelDto("현대", "소나타", 100, 4.9);
			List<CarExcelDto> carExcelDtos = Arrays.asList(carExcelDto);

			FileOutputStream fos = new FileOutputStream("test.xlsx");

			int rowIndex = 0;
			Row headerRow = sheet.createRow(rowIndex++);
			Cell headerCell1 = headerRow.createCell(0);
			headerCell1.setCellValue("회사");

			Cell headerCell2 = headerRow.createCell(1);
			headerCell2.setCellValue("차종");

			Cell headerCell3 = headerRow.createCell(2);
			headerCell3.setCellValue("가격");

			Cell headerCell4 = headerRow.createCell(3);
			headerCell4.setCellValue("평점");

			for (CarExcelDto dto : carExcelDtos) {
				Row bodyRow = sheet.createRow(rowIndex++);

				Cell bodyCell1 = bodyRow.createCell(0);
				bodyCell1.setCellValue(dto.getCompany());

				Cell bodyCell2 = bodyRow.createCell(1);
				bodyCell2.setCellValue(dto.getName());

				Cell bodyCell3 = bodyRow.createCell(2);
				bodyCell3.setCellValue(dto.getPrice());

				Cell bodyCell4 = bodyRow.createCell(3);
				bodyCell4.setCellValue(dto.getRating());
			}

			workbook.write(fos);
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
