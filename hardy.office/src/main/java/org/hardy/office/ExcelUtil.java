package org.hardy.office;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil
{
  public static <T> void createSimpleExcel(String sheetName, Short hssfstyle, List<T> contentExcel, OutputStream os, Excelcontent<T> excelContent, String... titles)
    throws IOException
  {
    if (hssfstyle == null) {
      hssfstyle = Short.valueOf((short)2);
    }
    HSSFWorkbook wb = new HSSFWorkbook();
    
    HSSFSheet sheet = wb.createSheet(sheetName);
    
    HSSFRow row = sheet.createRow(0);
    
    HSSFCellStyle style = wb.createCellStyle();
    style.setAlignment(hssfstyle.shortValue());
    for (int i = 0; i < titles.length; i++)
    {
      HSSFCell cell = row.createCell(i);
      cell.setCellValue(titles[i]);
      cell.setCellStyle(style);
    }
    for (int i = 1; i <= contentExcel.size(); i++)
    {
      row = sheet.createRow(i);
      excelContent.doList(row, contentExcel.get(i - 1), i);
    }
    wb.write(os);
    os.flush();
    os.close();
  }
  
  public static abstract interface Excelcontent<E>
  {
    public abstract void doList(HSSFRow paramHSSFRow, E paramE, int paramInt);
  }
}
