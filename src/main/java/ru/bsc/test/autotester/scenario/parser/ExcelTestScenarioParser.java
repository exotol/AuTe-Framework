package ru.bsc.test.autotester.scenario.parser;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.bsc.test.autotester.model.Step;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rrudakov on 5/16/16.
 * Project name bcs-rest-at
 */
public class ExcelTestScenarioParser {

    private static final String SERVICE_RELATIVE_PATH_HEAD = "SERVICE_RELATIVE_PATH";
    private static final String REQUEST_TO_APPLICATION_HEAD = "REQUEST";
    private static final String RESPONSE_FROM_WEB_SERVICE_HEAD = "MOCK_RESPONSES_FOR_REQUEST";
    private static final String RESPONSE_FROM_APPLICATION_HEAD = "EXPECTED_RESPONSE_FROM_PORTAL";
    private static final String TEST_CASE_DIR_HEAD = "EXPECTED_REQUESTS_TO_WEB_SERVICE_DIRECTORY";
    private static final String SAVE_VALUE_HEAD = "SAVING_VALUES";
    private static final String DB_PARAMS = "DB_PARAMS";
    private int serviceRelativePathCellNumber = -1;
    private int requestToApplicationCellNumber = -1;
    private int responseFromWebServiceCellNumber = -1;
    private int responseFromApplicationCellNumber = -1;
    private int testCaseDirCellNumber = -1;
    private int saveValueCellNumber = -1;
    private int dbParamsCellNumber = -1;
    private Sheet sheet;
    private OPCPackage opcPackage;

    public ExcelTestScenarioParser(InputStream excelFile) throws IOException {
        try {
            opcPackage = OPCPackage.open(excelFile);
            Workbook workbook = new XSSFWorkbook(opcPackage);
            workbook.setMissingCellPolicy(Row.RETURN_NULL_AND_BLANK);
            this.sheet = workbook.getSheetAt(0);
            opcPackage.revert();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public List<List<Step>> parse() throws IOException {
        List<List<Step>> scenarios = new ArrayList<>();

        parseHeader(sheet.getRow(0));

        Row currentRow;
        int startTestRowNumber;
        int endTestRowNumber = 0;

        for (int currentRowNumber = 1; currentRowNumber < sheet.getLastRowNum(); currentRowNumber++) {
            currentRow = sheet.getRow(currentRowNumber);

            if (!isEmptyRowOrComment(currentRow)) {

                startTestRowNumber = currentRowNumber;

                while (!(isEmptyRowOrComment(sheet.getRow(currentRowNumber)))) {
                    endTestRowNumber = currentRowNumber;
                    currentRowNumber++;
                }

                scenarios.add(parseTest(startTestRowNumber, endTestRowNumber));
            }
        }
        this.opcPackage.close();
        return scenarios;
    }

    private void parseHeader(Row headerRow) {
        Iterator<Cell> iterator = headerRow.cellIterator();
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            if (cell.getCellType() != Cell.CELL_TYPE_STRING)
                throw new AssertionError("Invalid value type in head of table");
            if (cell.getStringCellValue().trim().equals(SERVICE_RELATIVE_PATH_HEAD))
                serviceRelativePathCellNumber = cell.getColumnIndex();
            else if (cell.getStringCellValue().trim().equals(REQUEST_TO_APPLICATION_HEAD) || cell.getStringCellValue().trim().equals("REQUESTS"))
                requestToApplicationCellNumber = cell.getColumnIndex();
            else if (cell.getStringCellValue().trim().equals(RESPONSE_FROM_WEB_SERVICE_HEAD))
                responseFromWebServiceCellNumber = cell.getColumnIndex();
            else if (cell.getStringCellValue().trim().equals(RESPONSE_FROM_APPLICATION_HEAD) || cell.getStringCellValue().trim().equals("EXPECTED_RESPONSE_FROM_JMBA"))
                responseFromApplicationCellNumber = cell.getColumnIndex();
            else if (cell.getStringCellValue().trim().equals(TEST_CASE_DIR_HEAD) || cell.getStringCellValue().trim().equals("CASE_DIR_REQUESTS_JMBA_TO_MOCK"))
                testCaseDirCellNumber = cell.getColumnIndex();
            else if (cell.getStringCellValue().trim().equals(SAVE_VALUE_HEAD))
                saveValueCellNumber = cell.getColumnIndex();
            else if (cell.getStringCellValue().trim().equals(DB_PARAMS))
                dbParamsCellNumber = cell.getColumnIndex();
            else throw new AssertionError("Invalid format head of table in Excel file");
        }
    }



    private List<Step> parseTest(int startRowNumber, int endRowNumber) throws IOException {
        List<Step> scenario = new ArrayList<>();
        for (int currentRowNumber = startRowNumber; currentRowNumber <= endRowNumber; currentRowNumber++) {
            String serviceRelativePath = null;
            String requestToApplication = null;

            Row row = sheet.getRow(currentRowNumber);
            if (currentRowNumber == startRowNumber) {
                serviceRelativePath = serviceRelativePathCellNumber != -1 ? getCellValue(row.getCell(serviceRelativePathCellNumber)) : "";
                requestToApplication = getCellValue(row.getCell(requestToApplicationCellNumber));
            } else {
                String buffer = serviceRelativePathCellNumber != -1 ? getCellValue(row.getCell(serviceRelativePathCellNumber)) : "";
                if (!buffer.isEmpty()) {
                    serviceRelativePath = buffer;
                }
                buffer = getCellValue(row.getCell(requestToApplicationCellNumber));
                if ("empty".equals(buffer)) {
                    requestToApplication = "";
                } else if (!"".equals(buffer)) {
                    requestToApplication = buffer;
                }
            }

            String responseFromWebService = getCellValue(row.getCell(responseFromWebServiceCellNumber));
            String responseFromApplication = getCellValue(row.getCell(responseFromApplicationCellNumber));
            String testCaseDir = getCellValue(row.getCell(testCaseDirCellNumber));
            String saveValue = getCellValue(row.getCell(saveValueCellNumber));
            String dbParams = dbParamsCellNumber != -1 ? getCellValue(row.getCell(dbParamsCellNumber)) : "";

            // TODO process testCaseDir

            /*
            List<String> stringList = new LinkedList<>();
            // Костыли для импорта запросов к сервису из конкретного проекта
            if (!testCaseDir.isEmpty()) {
                File serviceRequestDirectory = new File("c:\\projects\\jmba-at\\src\\test\\resources\\expectedJmbaToWsRequests\\" + testCaseDir);
                if (serviceRequestDirectory.exists()) {
                    // TODO проверить директории по названиям сервисов в responseFromWebService
                    for (File serviceRequestFile : serviceRequestDirectory.listFiles(pathname -> pathname != null && pathname.getName() != null && pathname.getName().endsWith(".xml"))) {
                        if (!serviceRequestFile.isDirectory()) {
                            stringList.add(new String(Files.readAllBytes(Paths.get(serviceRequestFile.getPath())), StandardCharsets.UTF_8));
                        }
                    }
                }
            }
            */


            scenario.add(new Step(
                    serviceRelativePath == null ? "" : serviceRelativePath.endsWith(" GET") ? serviceRelativePath.substring(0, serviceRelativePath.length() - 4) : serviceRelativePath,
                    serviceRelativePath == null ? "GET" : serviceRelativePath.endsWith(" GET") ? "GET" : "POST",
                    requestToApplication,
                    "",
                    responseFromApplication,
                    saveValue,
                    responseFromWebService,
                    dbParams,
                    testCaseDir));
            //scenario.add(new Step(serviceRelativePath, requestToApplication, responseFromWebService, responseFromApplication, testCaseDir, saveValue));
        }

        return scenario;
    }

    private boolean isEmptyRowOrComment(Row row) {
        if (row == null) return true;

        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (row.getCell(i) == null) continue;
            if (row.getCell(i).getCellType() == Cell.CELL_TYPE_BLANK) continue;
            if (!row.getCell(i).getStringCellValue().matches("#.*|")) return false;
        }
        return true;
    }

    private String getCellValue(Cell cell) {
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) return "";
        else {
            if (cell.getCellType() != Cell.CELL_TYPE_STRING)
                throw new AssertionError(String.format("Invalid value type '%s' in excel file. Row: %d, Cell: %d",
                        cell.getCellType(), cell.getAddress().getRow(), cell.getAddress().getColumn()));
            else return fixSpaces(cell.getStringCellValue().trim());
        }
    }

    private String fixSpaces(String string) {
        char brokenSpace = 160;
        char goodSpace = 32;
        string = string.replaceAll(" ", " ");
        return string.replace(brokenSpace, goodSpace);
    }
}
