/**
 * 
 */
package com.love.service;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;


/**
 * @author bao
 *
 */
public class XlsUtil {

	public static final int MAX_COLUMN = 9; 				// ��ͨSheet�������
	public static final int MAX_COLUMN_TOTAL = 6; 			// ͳ��Sheet�������
	
	/**
	 * ��ʼ���̶�ͷ
	 * PS: ����ͷʱ���ϲ���Ԫ��ı߿�û�п�����ȫ
	 * @param sheet
	 * @param total �Ƿ�Ϊͳ��Sheet
	 * @param sheetName Ĭ��ֵ
	 * @throws WriteException 
	 */
	public static void initHeader(WritableSheet sheet, boolean total, String sheetName) throws WriteException{
		addBorder(sheet, 0, 0);
		if(!total){
			addBorder(sheet, 0, 1);
			addBorder(sheet, 0, 2, sheetName);
		}else{
			addBorder(sheet, 0, 1);
		}
	}
	
	/**
	 * ���С���ⵥԪ��
	 * @param sheet
	 * @param column
	 * @param row
	 * @param contents
	 * @throws WriteException
	 */
	public static void addPartCell(WritableSheet sheet, int column, int row, String contents) throws WriteException{
		WritableFont bold = new WritableFont(WritableFont.createFont("����"), 10, WritableFont.BOLD); 
		
		WritableCellFormat cf = new WritableCellFormat(bold);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setBackground(Colour.PALE_BLUE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		Label lbl = new Label(column, row, contents, cf);
		sheet.addCell(lbl);
	}
	
	/**
	 * �߿��ʽ
	 * @throws WriteException
	 */
	public static CellFormat getCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("����"), 10);
		
		WritableCellFormat cf = new WritableCellFormat(font);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * ������ʽ: �߿� + ��ֱ���� + �Զ�����
 	 * @throws WriteException
	 */
	public static CellFormat getParamCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("����"), 10);
		
		WritableCellFormat cf = new WritableCellFormat(font);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setVerticalAlignment(VerticalAlignment.BOTTOM);
		cf.setWrap(true);
		return cf;
	}
	
	/**
	 * �߿� + ���и�ʽ
	 * @throws WriteException
	 */
	public static CellFormat getCenterCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("����"), 10);
		
		WritableCellFormat cf = new WritableCellFormat(font);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.CENTRE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * �߿� + ���� + �Ӵָ�ʽ
	 * @throws WriteException
	 */
	public static CellFormat getCenterBoldCellFormat() throws WriteException{
		WritableFont bold = new WritableFont(WritableFont.createFont("����"), 10, WritableFont.BOLD);  
		
		WritableCellFormat cf = new WritableCellFormat(bold);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.CENTRE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * �߿� + ���� + �Ӵָ�ʽ
	 * @throws WriteException
	 */
	public static CellFormat getRightBoldCellFormat() throws WriteException{
		WritableFont bold = new WritableFont(WritableFont.createFont("����"), 10, WritableFont.BOLD);  
		
		WritableCellFormat cf = new WritableCellFormat(bold);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.RIGHT);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * ���ר�� + ���� + �߿�
	 * @param sheet
	 * @param column
	 * @param row
	 * @param contents
	 * @throws WriteException
	 */
	public static CellFormat getPriceCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("����"), 10);

		WritableCellFormat cf = new WritableCellFormat(font, NumberFormats.ACCOUNTING_FLOAT);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.CENTRE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		return cf;
	}
	
	/**
	 * ���ר�� + ���� + �߿� + �Ӵ�
	 * @param sheet
	 * @param column
	 * @param row
	 * @param contents
	 * @throws WriteException
	 */
	public static CellFormat getPriceBoldCellFormat() throws WriteException{
		WritableFont bold = new WritableFont(WritableFont.createFont("����"), 10, WritableFont.BOLD); 

		WritableCellFormat cf = new WritableCellFormat(bold, NumberFormats.ACCOUNTING_FLOAT);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.RIGHT);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * ���ר�� + ���� + �߿�
	 * @param sheet
	 * @param column
	 * @param row
	 * @param contents
	 * @throws WriteException
	 */
	public static CellFormat getPriceRightCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("����"), 10);

		WritableCellFormat cf = new WritableCellFormat(font, NumberFormats.ACCOUNTING_FLOAT);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.RIGHT);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * �õ��ϲ���Ԫ��ֵ
	 * 1) �Ƿ�Ϊ�ϲ���Ԫ��
	 * 2) �õ�������Ԫ���ֵ
	 * 3) ����ͬһ��
	 * @param sheet
	 * @param c
	 * @param r
	 * @return
	 */
	public static Cell getMergedCell(Sheet sheet, int c, int r){
		Range[] ranges = sheet.getMergedCells();
		Cell topCell = null;
		for (Range range : ranges) {
			if(c >= range.getTopLeft().getColumn() && c <= range.getBottomRight().getColumn()
					&& r >= range.getTopLeft().getRow() && r <= range.getBottomRight().getRow()){
				topCell = range.getTopLeft();
				break;
			}
		}
		
		if(topCell != null && topCell.getRow() != r){
			return topCell;
		}else{
			return sheet.getCell(c, r);
		}
	}
	
	/**
	 * ָ����Ԫ�����ӱ߿�
	 * @param sheet
	 * @param column
	 * @param row
	 * @throws WriteException
	 */
	private static void addBorder(WritableSheet sheet, int column, int row) throws WriteException{
		addBorder(sheet, column, row, null);
	}
	
	/**
	 * ָ����Ԫ�����ӱ߿�
	 * @param sheet
	 * @param column
	 * @param row
	 * @param sheetName Ĭ��ֵ
	 * @throws WriteException
	 */
	private static void addBorder(WritableSheet sheet, int column, int row, String sheetName) throws WriteException{
		WritableCellFormat cf;
		WritableCell cell = sheet.getWritableCell(column, row);
		if(cell.getCellFormat() != null){
			cf = new WritableCellFormat(cell.getCellFormat());
		}else{
			WritableFont bold = new WritableFont(WritableFont.createFont("����"), 10, WritableFont.BOLD); 
			
			cf = new WritableCellFormat(bold);
			cf.setAlignment(Alignment.CENTRE);
			cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		}
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		
		if(sheetName == null){
			sheetName = cell.getContents();
		}
		Label lbl = new Label(column, row, sheetName, cf);
		sheet.addCell(lbl);
	}
}
