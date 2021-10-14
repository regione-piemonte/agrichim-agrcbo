package it.csi.cuneo;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class ExcelUtils
{
	public static final int ALIGN_CENTER = 0;
	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_RIGHT = 2;

  /**
   * Crea n celle in una riga
   * (da utilizzare nel caso di visualizzazione di stringhe)
   * @param columns
   * @param sheet
   * @param currentRow
   * @param style
   * @return int
   */
  public static int newRowCellString(Object[] columns, HSSFSheet sheet, int currentRow, HSSFCellStyle style)
  {                                 
    HSSFRow row = sheet.createRow(currentRow++);
    if (currentRow==1) //Header, imposto un'altezza diversa
    	row.setHeight((short)1600);
    HSSFCell cell = null;

    for (int i = 0; i < columns.length; i++)
    {
      cell = row.createCell(i);
      cell.setCellStyle(style);
      Object value = columns[i];
      if (value != null)
      {
      	if (value instanceof BigDecimal)
      	{
      		cell.setCellValue(Double.parseDouble(value.toString()));
      		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
      	}
      	else
      	{
      		cell.setCellValue(value.toString());
      		cell.setCellType(Cell.CELL_TYPE_STRING);
      	}
      }
    }

    return currentRow;
  }
  

  //overload per vector 
  public static int newRowCellString(Vector<?> columns, HSSFSheet sheet, int currentRow, HSSFCellStyle style)
  {
    HSSFRow row = sheet.createRow(currentRow++);
    HSSFCell cell = null;

    for (int i = 0; i < columns.size(); i++)
    {
      cell = row.createCell(i);
      cell.setCellStyle(style);
      Object obj = columns.get(i);
      if (obj != null)
      {	
      	if (obj instanceof Double)
      	{
      		cell.setCellValue(((Double)obj).doubleValue());
      	}
      	else if (obj instanceof BigDecimal)
      	{
      		cell.setCellValue(((BigDecimal)obj).doubleValue());
      	}

      	else cell.setCellValue(obj.toString());
      }
    }

    return currentRow;
  }

  /**
   * Crea n celle in una riga
   * (da utilizzare nel caso di visualizzazione di stringhe e BigDecimal, in questo caso è possibile la sommatoria)
   * @param columns
   * @param sheet
   * @param currentRow
   * @param style
   * @return int
   */
  public static int newRowCellStringBigDecimal(Object[] columns, HSSFSheet sheet, int currentRow, HSSFCellStyle style)
  {
    HSSFRow row = sheet.createRow(currentRow++);
    HSSFCell cell = null;

    for (int i = 0; i < columns.length; i++)
    {
      cell = row.createCell(i);
      cell.setCellStyle(style);

      if (columns[i] instanceof BigDecimal)
      {
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellValue(new BigDecimal(columns[i].toString()).doubleValue());
      }
      else
      {
        cell.setCellValue(columns[i].toString());
      }
    }

    return currentRow;
  }

  
  
  
  
  
  
  
  
  /**
   * Crea una riga vuota
   * @param sheet
   * @param currentRow
   * @return int
   */
  public static int newEmptyRow(HSSFSheet sheet, int currentRow)
  {
    sheet.createRow(currentRow++);

    return currentRow;
  }

  /**
   * Font normale
   * @param workBook
   * @return HSSFCellStyle
   */
  public static HSSFCellStyle styleFontDefault(HSSFWorkbook workBook)
  {
    HSSFFont fontDefault = workBook.createFont();
    HSSFCellStyle styleDefault = workBook.createCellStyle();
    styleDefault.setFont(fontDefault);
    //prova Elisa
    styleDefault.setWrapText(true); //per andare a capo
    
    return styleDefault;
  }

  /**
   * Per ciascuna colonna indicata in input (cols) del range di righe identificate da rowFrom e rowTO,
   * viene applicato lo stile alla cella
   * @param workBook
   * @param cellStyle
   * @param rowFrom
   * @param rowTo
   * @param cols
   */
  public static void setCellsStyleNumeric(HSSFWorkbook workBook, HSSFCellStyle cellStyle, int rowFrom, int rowTo, int[] cols)
  {
    HSSFSheet sheet = workBook.getSheetAt(0);
    HSSFRow row = null;
    HSSFCell cell = null;
    
    for (int r = rowFrom; r <= rowTo; r++)
    {
      row = sheet.getRow(r);
      if (row != null)
      {
        //Per ciascuna colonna indicata in input (cols) del range di righe identificate da rowFrom e rowTO,
        //viene applicato lo stile alla cella
        int lenght = cols.length;
        for (int c = 0; c < lenght; c++)
        {
          cell = row.getCell(cols[c]);

          String value = cell.getStringCellValue();
          if (value != null && value != "")
          {
            //La cella deve contenere un valore numerico
            cell.setCellValue(new Double(value).doubleValue());
          }
          cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
          cell.setCellStyle(cellStyle);
        }
      }
    }
  }

  /**
   * Per ciascuna colonna indicata in input (cols) del range di righe identificate da rowFrom e rowTO,
   * viene applicato l'allineamento alla cella
   * @param workBook
   * @param cellStyle
   * @param rowFrom
   * @param rowTo
   * @param cols
   */
  public static void setCellsStyleAlignment(HSSFWorkbook workBook, int alignment, int rowFrom, int rowTo, int[] cols)
  {
    HSSFSheet sheet = workBook.getSheetAt(0);
    HSSFRow row = null;
    HSSFCell cell = null;
    CellStyle newCellStyle = workBook.createCellStyle();

    for (int r = rowFrom; r <= rowTo; r++)
    {
      row = sheet.getRow(r);

      if (row != null)
      {
        //Per ciascuna colonna indicata in input (cols) del range di righe identificate da rowFrom e rowTO,
        //viene applicato lo stile alla cella
        int lenght = cols.length;
        for (int c = 0; c < lenght; c++)
        {
          cell = row.getCell(cols[c]);
          newCellStyle.cloneStyleFrom(cell.getCellStyle());
          cell.setCellStyle(newCellStyle);
        
          //Allineamento al centro
          switch (alignment)
					{
						case ALIGN_CENTER:
	          	newCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	          	newCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
							break;

						case ALIGN_RIGHT:
	          	newCellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	          	newCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
							break;

						default:
	          	newCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	          	newCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
							break;
					}
          cell.setCellStyle(newCellStyle);
        }
      }
    }
  }

  /**
   * Font grassetto
   * @param workBook
   * @return HSSFCellStyle
   */
  public static HSSFCellStyle styleFontBold(HSSFWorkbook workBook)
  {
    HSSFFont fontBold = workBook.createFont();
    HSSFCellStyle styleBold = workBook.createCellStyle();
    fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    styleBold.setRotation((short) + 90);
    styleBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    styleBold.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
    styleBold.setFont(fontBold);
    styleBold.setWrapText(true);

    return styleBold;
  }

  /**
   * Formatta l'importo espresso in euro
   * @param workBook
   * @param isBold
   * @return
   */
  public static HSSFCellStyle styleFontEuro(HSSFWorkbook workBook, boolean isBold)
  {
    HSSFCellStyle styleFont = isBold ? styleFontBold(workBook) : styleFontDefault(workBook);
    styleFont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
    styleFont.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

    return styleFont;
  }
  
  /**
   * Formatta un numero senza virgola
   * @param workBook
   * @param isBold
   * @return
   */
  public static HSSFCellStyle styleFontIntero(HSSFWorkbook workBook, boolean isBold)
  {
    HSSFCellStyle styleFont = isBold ? styleFontBold(workBook) : styleFontDefault(workBook);
    styleFont.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
    styleFont.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));

    return styleFont;
  }

  public static void autosizeColumns(HSSFWorkbook workBook)
  {
    Graphics graphics = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB).getGraphics();

    for(int k=0; k<workBook.getNumberOfSheets(); k++)
    {
      HSSFSheet sheet = workBook.getSheetAt(k);
      int columnsWidth[] = new int[0];
      Iterator<Row> iterRows = sheet.rowIterator();
      Vector<CellRangeAddress> mergedRegions = new Vector<CellRangeAddress>();

      for (int i = 0; i < sheet.getNumMergedRegions(); i++)
      {
      	CellRangeAddress region = sheet.getMergedRegion(i);
        //System.out.println("MERGED REGION : COLUMN FROM "+region.getColumnFrom()+" - COLUMN TO : "+region.getColumnTo()+" - ROW FROM "+region.getRowFrom()+" - ROW TO "+region.getRowTo());
        if (region.getLastColumn() - region.getFirstColumn() > 0)
        {
          //System.out.println("REGION ADDED!!!");
          mergedRegions.add(region);
        }
      }

      while(iterRows.hasNext())
      {
        HSSFRow row = (HSSFRow)iterRows.next();

        //System.out.println("ROW "+row.getRowNum()+" WITH "+row.getLastCellNum()+" CELLS");
        for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++)
        {
          HSSFCell cell = row.getCell(i);
          if(cell!=null) {
            HSSFFont font = workBook.getFontAt(cell.getCellStyle().getFontIndex());
            //System.out.println("CELL NUMBER "+i+" WITH FONT "+font.getFontName()+" (BOLD "+(font.getBoldweight()==font.BOLDWEIGHT_BOLD)+" - ITALIC "+font.getItalic()+")");
            int mergedColumnsNum = 1;
            int lastColumnsMergeSize = 0;
            int fontStyle = Font.PLAIN;
            if(font.getBoldweight()==HSSFFont.BOLDWEIGHT_BOLD)
              fontStyle = fontStyle|Font.BOLD;
            if(font.getItalic())
              fontStyle = fontStyle|Font.ITALIC;
            Font awtFont = new Font(font.getFontName(), fontStyle, font.getFontHeight());

            String cellValue;
            if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC)
              cellValue = ""+cell.getNumericCellValue();
            else
              cellValue = cell.getStringCellValue();

            FontMetrics fontMetrics = graphics.getFontMetrics(awtFont);

            //int stringWidth = fontMetrics.stringWidth(cellValue);
            int stringWidth = 0;

            if(cellValue.indexOf("\n")>=0&&cell.getCellStyle().getWrapText()) {
              StringTokenizer st = new StringTokenizer(cellValue, "\n");
              int tokenLength;
              while(st.hasMoreTokens()) {
                tokenLength = fontMetrics.stringWidth(st.nextToken());
                stringWidth = Math.max(stringWidth, tokenLength);
              }
            }else
              stringWidth = fontMetrics.stringWidth(cellValue);

            //System.out.println("CELL VALUE : "+cellValue+" - LENGTH "+cellValue.length()+"- WIDTH "+stringWidth);
            for (int j = 0; j < mergedRegions.size(); j++)
            {
            	CellRangeAddress region = (CellRangeAddress) mergedRegions.get(j);
              if (region.isInRange(row.getRowNum(), i))
              {
                int startCol = Math.min(region.getFirstColumn(), region.getLastColumn());
                if (i == startCol)
                {
                  mergedColumnsNum = Math.abs(region.getLastColumn()-region.getFirstColumn())+1;
                  stringWidth = stringWidth / (mergedColumnsNum);
                  lastColumnsMergeSize = Math.max(Math.max(region.getFirstColumn(), region.getLastColumn())-row.getLastCellNum(), 0);
                  //System.out.println("IN REGION: COLUMNS NUM "+mergedColumnsNum+" - STRING WIDTH "+stringWidth);
                }
                else
                {
                  mergedColumnsNum = 0;
                }
                break;
              }
              else if (region.getFirstColumn() < row.getRowNum() && region.getLastColumn() < row.getRowNum())
              {
              	mergedRegions.remove(j--);
              }
            }

            if(columnsWidth.length<row.getLastCellNum()+lastColumnsMergeSize+1) {
              //System.out.println("AUMENTO LARGHEZZA ARRAY DA "+columnsWidth.length+" A "+(row.getLastCellNum()+lastColumnsMergeSize+1));
              int[] newColumnsWidth = new int[row.getLastCellNum()+lastColumnsMergeSize+1];
              System.arraycopy(columnsWidth, 0, newColumnsWidth, 0, columnsWidth.length);
              columnsWidth = newColumnsWidth;
            }

            for(int j=0; j<mergedColumnsNum; j++) {
              //System.out.println("COLUMN "+(i+j)+" - WIDTH "+columnsWidth[i+j]+" - STRING WIDTH "+stringWidth);
              if(columnsWidth[i+j]<stringWidth)
                columnsWidth[i+j] = stringWidth;
            }
          }
        }
      }

      for(int i=0; i<columnsWidth.length; i++) {
      	short width = (short)(columnsWidth[i]*(2.8-(columnsWidth[i]/10000)));
      	//patch: con stringhe molto lunghe la funzione 2.8 x - x^2/10000 decresce fino a diventare negativa (e la colonna sparisce...)
      	if(width<0){
      		if(i>0){
      			width = (short)(columnsWidth[i-1]*(2.8-(columnsWidth[i]/10000)));
      		}
      		else{
      			width = (short)100;
      		}
      	}
        sheet.setColumnWidth(i, width);
       //System.out.println(" Calcolo AUTOSIZE" , "colonna" + i+ ": " + columnsWidth[i]*(2.8-(0.1*(columnsWidth[i]/1000))) + " approx: " + ((short)columnsWidth[i]*(2.8-(0.1*(columnsWidth[i]/1000)))));
      }
    }
  }
}