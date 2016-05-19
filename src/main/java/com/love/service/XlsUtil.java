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

	public static final int MAX_COLUMN = 9; 				// 普通Sheet的最大列
	public static final int MAX_COLUMN_TOTAL = 6; 			// 统计Sheet的最大列
	
	/**
	 * 初始化固定头
	 * PS: 拷贝头时，合并单元格的边框没有拷贝完全
	 * @param sheet
	 * @param total 是否为统计Sheet
	 * @param sheetName 默认值
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
	 * 添加小标题单元格
	 * @param sheet
	 * @param column
	 * @param row
	 * @param contents
	 * @throws WriteException
	 */
	public static void addPartCell(WritableSheet sheet, int column, int row, String contents) throws WriteException{
		WritableFont bold = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD); 
		
		WritableCellFormat cf = new WritableCellFormat(bold);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setBackground(Colour.PALE_BLUE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		Label lbl = new Label(column, row, contents, cf);
		sheet.addCell(lbl);
	}
	
	/**
	 * 边框格式
	 * @throws WriteException
	 */
	public static CellFormat getCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10);
		
		WritableCellFormat cf = new WritableCellFormat(font);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * 参数格式: 边框 + 垂直居中 + 自动换行
 	 * @throws WriteException
	 */
	public static CellFormat getParamCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10);
		
		WritableCellFormat cf = new WritableCellFormat(font);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setVerticalAlignment(VerticalAlignment.BOTTOM);
		cf.setWrap(true);
		return cf;
	}
	
	/**
	 * 边框 + 居中格式
	 * @throws WriteException
	 */
	public static CellFormat getCenterCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10);
		
		WritableCellFormat cf = new WritableCellFormat(font);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.CENTRE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * 边框 + 居中 + 加粗格式
	 * @throws WriteException
	 */
	public static CellFormat getCenterBoldCellFormat() throws WriteException{
		WritableFont bold = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD);  
		
		WritableCellFormat cf = new WritableCellFormat(bold);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.CENTRE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * 边框 + 居右 + 加粗格式
	 * @throws WriteException
	 */
	public static CellFormat getRightBoldCellFormat() throws WriteException{
		WritableFont bold = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD);  
		
		WritableCellFormat cf = new WritableCellFormat(bold);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.RIGHT);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * 会计专用 + 居中 + 边框
	 * @param sheet
	 * @param column
	 * @param row
	 * @param contents
	 * @throws WriteException
	 */
	public static CellFormat getPriceCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10);

		WritableCellFormat cf = new WritableCellFormat(font, NumberFormats.ACCOUNTING_FLOAT);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.CENTRE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		
		return cf;
	}
	
	/**
	 * 会计专用 + 居右 + 边框 + 加粗
	 * @param sheet
	 * @param column
	 * @param row
	 * @param contents
	 * @throws WriteException
	 */
	public static CellFormat getPriceBoldCellFormat() throws WriteException{
		WritableFont bold = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD); 

		WritableCellFormat cf = new WritableCellFormat(bold, NumberFormats.ACCOUNTING_FLOAT);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.RIGHT);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * 会计专用 + 居右 + 边框
	 * @param sheet
	 * @param column
	 * @param row
	 * @param contents
	 * @throws WriteException
	 */
	public static CellFormat getPriceRightCellFormat() throws WriteException{
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10);

		WritableCellFormat cf = new WritableCellFormat(font, NumberFormats.ACCOUNTING_FLOAT);
		cf.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		cf.setAlignment(Alignment.RIGHT);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		return cf;
	}
	
	/**
	 * 得到合并单元的值
	 * 1) 是否为合并单元格
	 * 2) 得到顶部单元格的值
	 * 3) 不在同一行
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
	 * 指定单元格增加边框
	 * @param sheet
	 * @param column
	 * @param row
	 * @throws WriteException
	 */
	private static void addBorder(WritableSheet sheet, int column, int row) throws WriteException{
		addBorder(sheet, column, row, null);
	}
	
	/**
	 * 指定单元格增加边框
	 * @param sheet
	 * @param column
	 * @param row
	 * @param sheetName 默认值
	 * @throws WriteException
	 */
	private static void addBorder(WritableSheet sheet, int column, int row, String sheetName) throws WriteException{
		WritableCellFormat cf;
		WritableCell cell = sheet.getWritableCell(column, row);
		if(cell.getCellFormat() != null){
			cf = new WritableCellFormat(cell.getCellFormat());
		}else{
			WritableFont bold = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD); 
			
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
