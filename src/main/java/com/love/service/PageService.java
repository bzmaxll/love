/**
 * 
 */
package com.love.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hisupplier.commons.exception.PageNotFoundException;
import com.hisupplier.commons.util.Coder;
import com.hisupplier.commons.util.FileUtil;
import com.hisupplier.commons.util.StringUtil;
import com.love.dao.PageDAO;
import com.love.entity.Category;
import com.love.entity.Product;
import com.love.entity.Report;
import com.love.entity.UpdateMap;

/**
 * @author bao
 *
 */
@Service
public class PageService {

	@Autowired
	private PageDAO pageDAO;
	
	public Map<String, Object> getHome() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Category> catList = new ArrayList<Category>();
		Category rootCategory = new Category(0, "根分类");
		catList.add(rootCategory);
		
		List<Category> _catList = pageDAO.findCategoryList();
		int catId = getFirstCatId(pageDAO.findCategoryList());
		if(_catList.size() > 0){
			Map<Integer, Category> map = new HashMap<Integer, Category>();
			for (Category category : _catList) {
				map.put(category.getId(), category);
			}
			
			for (Iterator<Category> iterator = _catList.iterator(); iterator.hasNext();) {
				Category category = iterator.next();
				if(category.getParentId() > 0){
					Category parentCategory = map.get(category.getParentId());
					if(parentCategory != null){
						parentCategory.addChildren(category);
					}
					iterator.remove();
				}
			}
			if(_catList.size() > 0){
				rootCategory.setChildren(_catList);
			}
		}

		JsonConfig jsonConfig = new JsonConfig(); 
		// 如果目录的children为null，不序列化
	    jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
	        public boolean apply(Object source, String name, Object value) {
	            if (value == null && "children".equals(name)) {
	                return true;
	            }
	            return false;
	        }
	    }); 
	    
		JSONArray jsonArray = JSONArray.fromObject(catList, jsonConfig);
		result.put("treeData", jsonArray.toString());
		result.put("catId", catId);
		return result;
	}

	public List<Product> findProductList(int catId) {
		if(catId <= 0){
			catId = getFirstCatId(pageDAO.findCategoryList());
			
			if(catId <= 0){
				return new ArrayList<Product>(); 
			}
		}

		List<Product> proList = pageDAO.findProductList(catId);
		return proList;
	}
	
	public int addCategory(int parentId, String name) {
		Category cat = new Category();
		cat.setName(Coder.decodeURL(name));
		cat.setParentId(parentId);
		
		int id = pageDAO.addCategory(cat);
			
		return id;
	}

	public String editCategory(int id, String name) {
		int num = pageDAO.updateCategory(id, Coder.decodeURL(name));

		return num > 0 ? "success" : "fail";
	}

	public String deleteCategory(int id) {
		int num = pageDAO.deleteCategory(id);

		pageDAO.deleteChildCategory(id);
		
		return num > 0 ? "success" : "fail";
	}

	public String moveCategory(int id, int oldCatId, String pids) {
		UpdateMap map = new UpdateMap("Product");
		map.addField("catId", id);
		map.addWhere("catId", oldCatId);
		map.addWhere("id", pids, "in");
		
		int num = pageDAO.update(map);
		return num > 0 ? "success" : "fail";
	}
	
	public String addProduct(Product product) {
		StringUtil.trimToEmpty(product, "name,brand,unit,param,remark");
		
		if(product.getId() > 0){
			UpdateMap map = new UpdateMap("Product");
			map.addField("name", product.getName());
			map.addField("model", product.getModel());
			map.addField("brand", product.getBrand());
			map.addField("unit", product.getUnit());
			map.addField("param", product.getParam());
			map.addField("remark", product.getRemark());
			map.addField("price1", product.getPrice1() + "");
			map.addField("price2", product.getPrice2() + "");
			map.addField("price3", product.getPrice3() + "");
			map.addField("price4", product.getPrice4() + "");
			map.addField("price5", product.getPrice5() + "");
			map.addWhere("id", product.getId());
			
			int num = pageDAO.update(map);
			return num > 0 ? "success" : "fail";
		}else{
			int id = pageDAO.addProduct(product);
			product.setId(id);
			
			return id > 0 ? "success" : "fail";
		}
	}

	public String deleteProduct(String ids) {
		UpdateMap map = new UpdateMap("Product");
		map.addField("state", 0);
		map.addWhere("id", ids, "in");
		
		int num = pageDAO.update(map);
		return num > 0 ? "success" : "fail";
	}
	
	private int getFirstCatId(List<Category> catList){
		int catId = 0;
		for (Category category : catList) {
			if(category.getParentId() > 0){
				catId = category.getId();
				break;
			}
		}
		
		return catId;
	}

	public List<Report> findReportList() {
		List<Report> reportList = pageDAO.findReportList();
		return reportList;
	}

	public List<Report> findReport(int id) {
		if(id <= 0){
			id = initReport();
		}

		return getCollapseReportList(id);
	}
	
	private List<Report> getCollapseReportList(int id){
		List<Report> reportList = pageDAO.findReportDetailList(id);
		
		int[] ids = new int[reportList.size()];
		int i = 0;
		Map<Integer, Report> map = new HashMap<Integer, Report>();
		for (Report report : reportList) {
			if(report.getpId() > 0 && report.getDepth() == 3){
				ids[i] = report.getpId();
				i++;
			}

			map.put(report.getId(), report);
		}
		
		if(ids.length > 0){
			List<Product> proList = pageDAO.findProductListByIds(ids);
			Map<Integer, Product> proMap = new HashMap<Integer, Product>();
			for (Product product : proList) {
				proMap.put(product.getId(), product);
			}
			
			for (Report report : reportList) {
				Product p = proMap.get(report.getpId());
				if(p != null){
					report.setBrand(p.getBrand());
					report.setModel(p.getModel());
					report.setUnit(p.getUnit());
					report.setPrice4(p.getPrice4());
					report.setParam(p.getParam());
				}
			}
		}
		
		for (Report report : reportList) {
			if(report.getParentId() > 0){
				Report parentReport = map.get(report.getParentId());
				if(parentReport != null){
					parentReport.addChildren(report);
				}
			}
		}

		for (Iterator<Report> iterator = reportList.iterator(); iterator.hasNext();) {
			Report report = iterator.next();
			if(report.getParentId() > 0){
				iterator.remove();
			}
		}
		
		return reportList;
	}
	
	private int initReport(){
		int id = addReportRoot("默认清单");
		
		int sheetId = addReportSheet(id, "Sheet1");
		
		addReportPart(id, sheetId, "第一部分");

		return id;
	}

	public String deleteReport(int id) {
		List<UpdateMap> maps = new ArrayList<UpdateMap>();
		
		UpdateMap map = new UpdateMap("Report");
		map.addField("state", 0);
		map.addWhere("id", id);
		maps.add(map);
		
		map = new UpdateMap("Report");
		map.addField("state", 0);
		map.addWhere("rootId", id);
		maps.add(map);
		
		int num = pageDAO.update(maps);
		return num > 0 ? "success" : "fail";
	}

	public String editReport(Report report) {
		StringUtil.trimToEmpty(report, "name");
		
		if(report.getDepth() == 3){
			UpdateMap map = new UpdateMap("Report");
			map.addField("num", report.getNum());
			map.addWhere("id", report.getId());
			
			int num = pageDAO.update(map);
			return num > 0 ? "success" : "fail";
		}else{
			UpdateMap map = new UpdateMap("Report");
			map.addField("name", report.getName());
			map.addWhere("id", report.getId());
			
			int num = pageDAO.update(map);
			return num > 0 ? "success" : "fail";
		}
	}
	
	public String addReport(Report report) {
		if(report.getDepth() == 0){
			report.setName("Sheet");
			report.setRootId(report.getId());
			report.setParentId(report.getId());
			report.setDepth(1);
			int id = pageDAO.addReport(report);
			
			return id > 0 ? "success" : "fail";
		}else if(report.getDepth() == 1){
			Report parentReport = pageDAO.findReport(report.getId());
			
			report.setName("默认部分");
			report.setRootId(parentReport.getRootId());
			report.setParentId(report.getId());
			report.setDepth(2);
			int id = pageDAO.addReport(report);
			
			return id > 0 ? "success" : "fail";
		}else if(report.getDepth() == 2){
			Report parentReport = pageDAO.findReport(report.getId());
			
			int[] ids = StringUtil.toIntArray(StringUtil.toArray(report.getIds(), ","));
			List<Product> proList = pageDAO.findProductListByIds(ids);
			
			Report childReport;
			int num = 0;
			for (Product product : proList) {
				childReport = new Report();
				childReport.setName(product.getName());
				childReport.setRootId(parentReport.getRootId());
				childReport.setParentId(report.getId());
				childReport.setDepth(3);
				childReport.setNum(1);
				childReport.setpId(product.getId());
				childReport.setBrand(product.getBrand());
				childReport.setModel(product.getModel());
				childReport.setUnit(product.getUnit());
				childReport.setPrice4(product.getPrice4());
				num += pageDAO.addReport(childReport);
			}
			
			return num > 0 ? "success" : "fail";
		}
		
		return "fail";
	}

	public String removeReport(int id) {
		Report report = pageDAO.findReport(id);
		
		if(report.getDepth() == 0){
			return "fail";
		}
		
		List<UpdateMap> maps = new ArrayList<UpdateMap>();
		
		UpdateMap map = new UpdateMap("Report");
		map.addField("state", 0);
		map.addWhere("id", id);
		maps.add(map);
		
		map = new UpdateMap("Report");
		map.addField("state", 0);
		map.addWhere("parentId", id);
		maps.add(map);
		
		if(report.getDepth() == 1){
			List<Integer> idList = pageDAO.findReportIdList(id);
			if(idList.size() > 0){
				map = new UpdateMap("Report");
				map.addField("state", 0);
				map.addWhere("depth", 3);
				map.addWhere("parentId", StringUtil.toIntString(idList, ","), "in");
				maps.add(map);
			}
		}
		
		int num = pageDAO.update(maps);
		return num > 0 ? "success" : "fail";
	}

	public String createExcelFile(String fileDir, int id) {
		List<Report> reportList = getCollapseReportList(id);
		if(reportList.size() <= 0){
			throw new PageNotFoundException();
		}
		
		WritableWorkbook book = null;
		Report root = reportList.get(0);
		String fileName = root.getName() + ".xls";
		File templateFile = new File(ServletActionContext.getServletContext().getRealPath("") + "/template.xls");
		try {
			File file = new File(fileDir + "/" + fileName);
			if (!file.exists()) {
				file = FileUtil.createNewFile(fileDir, fileName);
			}
			
			Workbook templateWorkbook = Workbook.getWorkbook(templateFile);

			book = Workbook.createWorkbook(file, templateWorkbook);

			int sheet_index = 2;
			if(root.getChildren() != null){
				Map<String, String> sheetTotal = new HashMap<String, String>(); // 所有总计单元格
				for (Report sheetReport : root.getChildren()) {
					book.copySheet(1, sheetReport.getName(), sheet_index);
					WritableSheet sheet = book.getSheet(sheet_index);
					XlsUtil.initHeader(sheet, false, sheetReport.getName());
					
					if(sheetReport.getChildren() != null){
						StringBuilder totalCells = new StringBuilder();	// 所有小计单元格
						int beginRow = 4;

						for (Report partReport : sheetReport.getChildren()) {
							// 名称为空，不显示part部分
							if(StringUtil.isNotBlank(partReport.getName())){
								sheet.mergeCells(0, beginRow, XlsUtil.MAX_COLUMN - 1, beginRow);
								XlsUtil.addPartCell(sheet, 0, beginRow, partReport.getName());
								sheet.setRowView(beginRow, 460);
								beginRow++;
							}

							if(partReport.getChildren() != null){
								int minRow = beginRow + 1;
								int maxRow = minRow;
								int index = 1;
								for (Report productReport : partReport.getChildren()) {
									jxl.write.Number nbl = new jxl.write.Number(0, beginRow, (double) index, XlsUtil.getCenterCellFormat());
									sheet.addCell(nbl);
									
									Label lbl = new Label(1, beginRow, productReport.getName(), XlsUtil.getCellFormat());
									sheet.addCell(lbl);
									
									lbl = new Label(2, beginRow, productReport.getModel(), XlsUtil.getCenterCellFormat());
									sheet.addCell(lbl);
									
									lbl = new Label(3, beginRow, productReport.getBrand(), XlsUtil.getCenterCellFormat());
									sheet.addCell(lbl);
									
									nbl = new jxl.write.Number(4, beginRow, (double) productReport.getNum(), XlsUtil.getCenterCellFormat());
									sheet.addCell(nbl);
									
									lbl = new Label(5, beginRow, productReport.getUnit(), XlsUtil.getCenterCellFormat());
									sheet.addCell(lbl);
									
									nbl = new jxl.write.Number(6, beginRow, (double) productReport.getPrice4(), XlsUtil.getPriceCellFormat());
									sheet.addCell(nbl);
									
									Formula f1 = new Formula(7, beginRow, "G" + (beginRow + 1) + "*E" + (beginRow + 1), XlsUtil.getPriceCellFormat());
									sheet.addCell(f1);
									
									lbl = new Label(8, beginRow, productReport.getParam(), XlsUtil.getParamCellFormat());
									sheet.addCell(lbl);
									
									sheet.setRowView(beginRow, 460);
									beginRow++;
									index++;
									maxRow++;
								}
								
								Label lbl = new Label(0, beginRow, "", XlsUtil.getCellFormat());
								sheet.addCell(lbl);
								
								sheet.mergeCells(1, beginRow, XlsUtil.MAX_COLUMN - 3, beginRow);
								lbl = new Label(1, beginRow, "小计", XlsUtil.getCenterCellFormat());
								sheet.addCell(lbl);
								
								Formula f1 = new Formula(7, beginRow, "SUM(H" + minRow + ":H" + (maxRow - 1) + ")", XlsUtil.getPriceCellFormat());
								sheet.addCell(f1);
								
								lbl = new Label(8, beginRow, "", XlsUtil.getParamCellFormat());
								sheet.addCell(lbl);
								
								sheet.setRowView(beginRow, 460);
								totalCells.append("+H" + (beginRow + 1));
								beginRow++;
							}
						}
						
						sheet.mergeCells(0, beginRow, XlsUtil.MAX_COLUMN - 3, beginRow);
						
						Label lbl = new Label(0, beginRow, "合计", XlsUtil.getCenterCellFormat());
						sheet.addCell(lbl);
						
						if(totalCells.length() > 0){
							totalCells.deleteCharAt(0);
						}
						
						Formula f1 = new Formula(7, beginRow, totalCells.toString(), XlsUtil.getPriceCellFormat());
						sheet.addCell(f1);
						
						lbl = new Label(8, beginRow, "", XlsUtil.getCellFormat());
						sheet.addCell(lbl);
						sheet.setRowView(beginRow, 460);
						sheetTotal.put(sheetReport.getName(), "H" + (beginRow + 1));
						
					}
					sheet_index++;
				}
				
				// 总价Sheet
				if(root.getChildren().size() > 1){
					book.copySheet(0, "总价", 2);
					WritableSheet sheet = book.getSheet(2);
					XlsUtil.initHeader(sheet, true, null);
					
					
					int beginRow = 3;
					int minRow = beginRow + 1;
					int maxRow = minRow;
					for (Report sheetReport : root.getChildren()) {
						jxl.write.Number nbl = new jxl.write.Number(0, beginRow, (double) (beginRow - 2), XlsUtil.getCenterCellFormat());
						sheet.addCell(nbl);
	
						Label lbl = new Label(1, beginRow, sheetReport.getName(), XlsUtil.getCenterBoldCellFormat());
						sheet.addCell(lbl);
						
						nbl = new jxl.write.Number(2, beginRow, 1d, XlsUtil.getCenterCellFormat());
						sheet.addCell(nbl);
						
						lbl = new Label(3, beginRow, "", XlsUtil.getCenterCellFormat());
						sheet.addCell(lbl);
	
						Formula f1 = new Formula(4, beginRow, "'" + sheetReport.getName() + "'!" + sheetTotal.get(sheetReport.getName()), XlsUtil.getPriceRightCellFormat());
						sheet.addCell(f1);
						
						f1 = new Formula(5, beginRow, "C" + (beginRow + 1) + "*E" + (beginRow + 1), XlsUtil.getPriceRightCellFormat());
						sheet.addCell(f1);
						
						beginRow++;
						maxRow++;
					}
					
					sheet.mergeCells(0, beginRow, 4, beginRow);
					
					Label lbl = new Label(0, beginRow, "总计", XlsUtil.getRightBoldCellFormat());
					sheet.addCell(lbl);
					
					Formula f1 = new Formula(5, beginRow, "SUM(F" + minRow + ":F" + (maxRow - 1)+ ")", XlsUtil.getPriceBoldCellFormat());
					sheet.addCell(f1);
				}
			}
			
			// 移除2个模板
			book.removeSheet(0);
			book.removeSheet(0);	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(book != null){
				try {
					book.write();
					book.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return fileName;
	}

	public String importReport(File attachment, String attachmentFileName) {
		int rootId = addReportRoot(attachmentFileName.substring(0, attachmentFileName.lastIndexOf(".")));
		int catId = getFirstCatId(pageDAO.findCategoryList());
		try {
			Workbook workbook = Workbook.getWorkbook(attachment);
			
			for(Sheet sheet : workbook.getSheets()){
				if(sheet.getName().equals("总价")){
					continue;
				}
				// 没有序号这一单元格，为无效sheet
				Cell cell = sheet.findCell("序号");
				if(cell == null){
					continue;
				}
				
				int sheetId = addReportSheet(rootId, sheet.getName());
				int partId = 0;
				List<Report> reportList = new ArrayList<Report>();
				
				
				int beginRow = cell.getRow();
				
				while (beginRow < sheet.getRows() - 1) {
					beginRow++;
					cell = sheet.getCell(0, beginRow);

					if(StringUtil.isBlank(cell.getContents())){
						continue;
					}
					
					// part部分
					Colour colour = cell.getCellFormat().getBackgroundColour();
					if(colour != Colour.DEFAULT_BACKGROUND){
						partId = addReportPart(rootId, sheetId, cell.getContents());
						continue;
					}

					String name =  sheet.getCell(1, beginRow).getContents();
					String model = XlsUtil.getMergedCell(sheet, 2, beginRow).getContents();
					String brand = XlsUtil.getMergedCell(sheet, 3, beginRow).getContents();
					int num = StringUtil.toInt(XlsUtil.getMergedCell(sheet, 4, beginRow).getContents(), 1);
					
					String unit = XlsUtil.getMergedCell(sheet, 5, beginRow).getContents();
					float price = 0;
					
					cell = XlsUtil.getMergedCell(sheet, 6, beginRow);
					if(cell.getType() == CellType.NUMBER){
						NumberCell numberCell = (NumberCell) cell; 
						price = (float) numberCell.getValue();
					}
					
					String param = "";
					if(sheet.getColumns() >= 9){
						param = XlsUtil.getMergedCell(sheet, 8, beginRow).getContents();
					}

					// 不是设备区域
					if(StringUtil.isBlank(name) || StringUtil.isBlank(model) || StringUtil.isBlank(brand)){
						continue;
					}
	
					int pId = pageDAO.findProduct(name, model, brand);
					if(pId <= 0){
						Product product = new Product();
						product.setCatId(catId);
						product.setName(name);
						product.setModel(model);
						product.setBrand(brand);
						product.setUnit(unit);;
						product.setPrice4(price);
						product.setParam(param);
						product.setRemark("");
						pId = pageDAO.addProduct(product);
					}

					// 没有part部分, 插入一条空记录
					if(partId <= 0){
						partId = addReportPart(rootId, sheetId, "");
					}
					
					Report report = new Report();
					report.setName(name);
					report.setRootId(rootId);
					report.setParentId(partId);
					report.setDepth(3);
					report.setpId(pId);
					report.setNum(num);
					reportList.add(report);
				}
				
				// 批量插入
				if(reportList.size() > 0){
					pageDAO.addReport(reportList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "导入失败";
		} 

		return "success";
	}
	
	/**
	 * 添加根报告
	 * @param name
	 * @return
	 */
	private int addReportRoot(String name){
		Report report = new Report();
		report.setName(name);
		
		return pageDAO.addReport(report);
	}
	
	/**
	 * 添加Sheet报告
	 * @param rootId
	 * @param name
	 * @return
	 */
	private int addReportSheet(int rootId, String name){
		Report report = new Report();
		report.setName(name);
		report.setRootId(rootId);
		report.setParentId(rootId);
		report.setDepth(1);
		return pageDAO.addReport(report);
	}
	
	/**
	 * 添加Part报告
	 * @param rootId
	 * @param name
	 * @return
	 */
	private int addReportPart(int rootId, int sheetId, String name){
		Report report = new Report();
		report.setName(name);
		report.setRootId(rootId);
		report.setParentId(sheetId);
		report.setDepth(2);
		return pageDAO.addReport(report);
	}
}
