package com.blackhorse.converting.sibsac;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/** @modelguid {9B6A558E-AD6E-47A3-B403-81719C527D2E} */
public class SibSACModelRow {
	/** @modelguid {0865ADC8-A636-47BD-BE62-490D366E6E35} */
	private Map map = new HashMap();

	/** @modelguid {58BC7D3C-CF86-4D88-994D-5866D586EA7F} */
	public SibSACModelRow() {
		super();
	}
	
	/** @modelguid {6762E799-18AA-4CAF-B907-5CB9BC72A47E} */
	public Object get(int index) {
	    Object ret = map.get(new Integer(index));
	    if (ret == null) {
	        return "";    
	    }
	    return ret;
	}

	/** @modelguid {46B84255-C870-41F3-BF1E-EA7983884835} */
	public SibSACModelRow(HSSFRow row) {
		super();

	    Iterator cellIt = row.cellIterator();
	    while (cellIt.hasNext()) {
	        HSSFCell cell = (HSSFCell)cellIt.next();
	        switch (cell.getCellType()) {
		        case HSSFCell.CELL_TYPE_BLANK:
		            map.put(new Integer(cell.getCellNum()), "");
		            break;
		        case HSSFCell.CELL_TYPE_STRING: {
		            String value = cell.getStringCellValue();
		            map.put(new Integer(cell.getCellNum()), value);
		        }break;
		        case HSSFCell.CELL_TYPE_NUMERIC:
		            map.put(new Integer(cell.getCellNum()), Double.toString(cell.getNumericCellValue()));
		            break;
		        default :
		            map.put(new Integer(cell.getCellNum()), "");
		        break;
            }
	    }
	}

}

