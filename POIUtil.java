package com.sohu.pay.base.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class POIUtil{

	//Excel文件路径
	private String filePath = "";

	//当前选定的Sheet名称
	private String selectedSheetName = "";

	//当前选定的Sheet索引
	private int selectedSheetIndex = 0;

	//设定开始读取的Sheet索引
	private int startSheetIndex = 0;

	//设定结束读取的Sheet索引 负数表示倒数第n页
	private int endSheetIndex = 0;

	//设定开始读取的行数
	private int startRowIndex = 0;

	//设定结束读取的行数 负数表示倒数第n行
	private int endRowIndex = 0;

	public POIUtil(){}

	public POIUtil(String filePath){
		this.filePath = filePath;
	}

	//导出excel文件
	/*
	 * 根据不同导出格式，调用对应的导出方法
	 */
	public boolean writeExcel(List<List<String>> list) throws IOException{
		return writeExcel(list, filePath);
	}

	public boolean writeExcel(List<List<String>> list, String filePath) throws IOException{
		if(filePath == null || filePath.equals("")){
			throw new IOException("文件路径不能为空！");
		}

		String extension = filePath.substring(filePath.lastIndexOf(".")+1);

		boolean result = false;

		if(extension.equals("xls")){
			result = writeExcel_xls(list, filePath);
		}else if(extension.equals("xlsx")){
			result = writeExcel_xlsx(list, filePath);
		}else if(extension.equals("csv")){
			result = writeExcel_xls(list, filePath);
		}else{
			result = writeExcel_xls(list, filePath);	//没有扩展名 尝试以xls方式写出
		}

		return result;
	}

	public boolean writeExcel_xls(List<List<String>> list, String filePath) throws IOException{
		if(filePath==null || filePath.equals("")){
			throw new IOException("文件路径不能为空！");
		}
		if(list==null || list.size()==0){
			throw new IOException("写入数据不能为空！");
		}

		File file = new File(filePath);

		if(file.exists()){
			return writeExcel(new HSSFWorkbook(), list, filePath);
		}

		return false;
	}

	public boolean writeExcel_xlsx(List<List<String>> list, String filePath) throws IOException{
		if(filePath==null || filePath.equals("")){
			throw new IOException("文件路径不能为空！");
		}
		if(list==null || list.size()==0){
			throw new IOException("写入数据不能为空！");
		}

		File file = new File(filePath);

		if(file.exists()){
			return writeExcel(new XSSFWorkbook(), list, filePath);
		}

		return false;
	}

	private boolean writeExcel(Workbook workbook, List<List<String>> list, String filePath){
		if(workbook != null){
			int rowNum = list.size();
			int cellNum = 0;
			if(list.get(0) != null){
				cellNum = list.get(0).size();
			}
			Sheet sheet = workbook.createSheet();
			Row row = null;
			Cell cell = null;
			List<String> eachLine = null;

			for(int i=0;i<rowNum;i++){
				eachLine = list.get(i);
				if(eachLine != null){
					row = sheet.createRow(i);
					for(int j=0;j<cellNum;j++){
						cell = row.createCell(j);
						if(eachLine.get(j) != null){
							cell.setCellValue(eachLine.get(j));
						}
					}
				}
			}

			try {
				FileOutputStream fileOutputStream = new FileOutputStream(filePath);
				workbook.write(fileOutputStream);
				fileOutputStream.flush();
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return true;
		}

		return false;
	}

	//读取excel文件
	/*
	 * 根据文件扩展名，调用对应的读取方法
	 */
	public List<List<String>> readExcel() throws IOException{
		return readExcel(this.filePath);
	}

	/*
	 * 根据文件扩展名，调用对应的读取方法
	 */
	public List<List<String>> readExcel(String filePath) throws IOException{
		if(filePath == null || filePath.equals("")){
			throw new IOException("文件路径不能为空！");
		}
		if(!new File(filePath).exists()){
			throw new IOException("文件不存在！");
		}

		String extension = filePath.substring(filePath.lastIndexOf(".")+1);

		List<List<String>> result = new ArrayList<>();

		if(extension.equals("xls")){
			result = readExcel_xls(filePath);
		}else if(extension.equals("xlsx")){
			result = readExcel_xlsx(filePath);
		}else if(extension.equals("csv")){
			result = readExcel_xls(filePath);
		}else{
			result = readExcel_xls(filePath);	//没有扩展名 尝试以xls方式读取
		}

		return result;
	}

	/*
	 * 读取Excel 97-03版，xls格式
	 */
	public List<List<String>> readExcel_xls(String filePath) throws IOException{
		File file = new File(filePath);

		if(!file.exists()){
			throw new IOException("文件不存在！");
		}

		HSSFWorkbook workbook = null;
		List<List<String>> result = new ArrayList<>();

		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
			result = readExcel(workbook);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 读取Excel 07版，xlsx格式
	 */
	public List<List<String>> readExcel_xlsx (String filePath) throws IOException{
		File file = new File(filePath);

		if(!file.exists()){
			throw new IOException("文件不存在！");
		}

		XSSFWorkbook workbook = null;
		List<List<String>> result = new ArrayList<>();

		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
			result = readExcel(workbook);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 通用读取Excel
	 */
	private List<List<String>> readExcel(Workbook workbook){
		List<List<String>> result = new ArrayList<>();
		Sheet sheet = null;
		int sheetCount = workbook.getNumberOfSheets();	//获取所有sheet数目

		for(int i=startSheetIndex;i<endSheetIndex+sheetCount;i++){	//默认顺序读取所有sheet

			sheet = workbook.getSheetAt(i);
			int lastRowNum = sheet.getLastRowNum();
			Row row = null;
			List<String> list = new ArrayList<>();

			for(int j=startRowIndex;j<endRowIndex+lastRowNum;j++){	//遍历每一行
				row = sheet.getRow(j);
				for(int k=0;k<row.getLastCellNum();k++){	//遍历所有单元格并存入list
					String value = getCellValue(row.getCell(k));
					list.add(value);
				}
				result.add(new ArrayList<String>(list));
			}
		}

		return result;
	}

	/*
	 * 读取单元格的值
	 */
	private String getCellValue(Cell cell) {
		Object result = "";
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					result = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					result = cell.getNumericCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					result = cell.getBooleanCellValue();
					break;
				case Cell.CELL_TYPE_FORMULA:
					result = cell.getCellFormula();
					break;
				case Cell.CELL_TYPE_ERROR:
					result = cell.getErrorCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					break;
				default:
					break;
			}
		}
		return result.toString();
	}

	public String getSelectedSheetName() {
		return selectedSheetName;
	}

	public void setSelectedSheetName(String selectedSheetName) {
		this.selectedSheetName = selectedSheetName;
	}

	public int getSelectedSheetIndex() {
		return selectedSheetIndex;
	}

	public void setSelectedSheetIndex(int selectedSheetIndex) {
		this.selectedSheetIndex = selectedSheetIndex;
	}

	public int getStartSheetIndex() {
		return startSheetIndex;
	}

	public void setStartSheetIndex(int startSheetIndex) {
		this.startSheetIndex = startSheetIndex;
	}

	public int getEndSheetIndex() {
		return endSheetIndex;
	}

	public void setEndSheetIndex(int endSheetIndex) {
		this.endSheetIndex = endSheetIndex;
	}

	public int getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public int getEndRowIndex() {
		return endRowIndex;
	}

	public void setEndRowIndex(int endRowIndex) {
		this.endRowIndex = endRowIndex;
	}
}
