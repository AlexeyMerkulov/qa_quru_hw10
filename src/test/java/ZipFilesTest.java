import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipFile;

public class ZipFilesTest {

    @Test
    void verifyXlxsContent() throws Exception {
        try (ZipFile zipFile = new ZipFile("src/test/resources/files.zip");
             InputStream is = zipFile.getInputStream(zipFile.getEntry("config.xlsx"))
        ) {
            XLS xls = new XLS(is);

            Assertions.assertEquals("Название процедуры",
                    xls.excel.getSheetAt(0).
                            getRow(0)
                            .getCell(0)
                            .getStringCellValue());
            Assertions.assertEquals("разработка рабочей документации",
                    xls.excel.getSheetAt(0).
                            getRow(1)
                            .getCell(0)
                            .getStringCellValue());
            Assertions.assertEquals("разработка проектной документации",
                    xls.excel.getSheetAt(0).
                            getRow(2)
                            .getCell(0)
                            .getStringCellValue());
        }
    }

    @Test
    void verifyCsvContent() throws Exception {
        try (ZipFile zipFile = new ZipFile("src/test/resources/files.zip");
             InputStream is = zipFile.getInputStream(zipFile.getEntry("cars.csv"));
             Reader reader = new InputStreamReader(is)
        ) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> content = csvReader.readAll();

            Assertions.assertEquals(3, content.size());

            String[] firstRow = content.get(0);
            String[] secondRow = content.get(1);
            String[] thirdRow = content.get(2);

            Assertions.assertArrayEquals(new String[] {"brand", "model", "year of production", "colour", "engine"}, firstRow);
            Assertions.assertArrayEquals(new String[] {"BMW", "5GT", "2013", "black", "diesel"}, secondRow);
            Assertions.assertArrayEquals(new String[] {"Honda", "Civic", "2016", "red", "petrol"}, thirdRow);
        }
    }

    @Test
    void verifyPdfContent() throws Exception {
        try (ZipFile zipFile = new ZipFile("src/test/resources/files.zip");
             InputStream is = zipFile.getInputStream(zipFile.getEntry("ReferenceCard.pdf"))
        ) {
            PDF pdf = new PDF(is);

            Assertions.assertTrue(pdf.text.contains("WINDOWS & LINUX KEYMAP"));
        }
    }
}
