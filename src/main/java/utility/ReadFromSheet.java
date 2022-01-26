package utility;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadFromSheet {
    public static String[][] ReadFromSheetOne(String excelfileName, String sheetNameOrTestClassName)
            throws IOException {

        // Create an object of File class to open xlsx file
        File file = new File(FrameworkConstants.DATA_FILE_PATH + excelfileName);

        // Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = null;

        // Find the file extension by splitting file name in substring and getting only
        // extension name

        String fileExtensionName = excelfileName.substring(excelfileName.indexOf("."));

        // Check condition if the file is xlsx file

        if (fileExtensionName.equals(".xlsx")) {
            // If it is xlsx file then create object of XSSFWorkbook class
            workbook = new XSSFWorkbook(inputStream);

        }
        // Check condition if the file is xls file
        else if (fileExtensionName.equals(".xls")) {

            // If it is xls file then create object of HSSFWorkbook class
            workbook = new HSSFWorkbook(inputStream);
        }

        // Read sheet inside the workbook by its name
        Sheet testSheet = workbook.getSheet(sheetNameOrTestClassName);
        int rowCount = testSheet.getLastRowNum();

        // get the top column name row from sheet
        Row colNameRow = testSheet.getRow(0);

        int colCount = colNameRow.getLastCellNum();

        String[][] arrayExcelData = new String[rowCount][colNameRow.getLastCellNum()];

        // looping all the rows data
        for (int i = 1; i <= rowCount; i++) {

            Row dataRow = testSheet.getRow(i);
            // Create a loop to get all cell values in a row
            for (int j = 0; j < colCount; j++) {

                if (dataRow.getCell(j).getCellType().toString() == "STRING")
                    arrayExcelData[i - 1][j] = dataRow.getCell(j).getStringCellValue();
                else if (dataRow.getCell(j).getCellType().toString() == "NUMERIC")
                    arrayExcelData[i - 1][j] = String.valueOf(dataRow.getCell(j).getNumericCellValue());
                else
                    arrayExcelData[i - 1][j] = String.valueOf(dataRow.getCell(j).getBooleanCellValue());
            }
        }
        return arrayExcelData;
    }    }
