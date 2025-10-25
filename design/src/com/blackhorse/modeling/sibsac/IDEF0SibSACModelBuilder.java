package com.blackhorse.modeling.sibsac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.blackhorse.converting.sibsac.SibSACModelRow;
import com.blackhorse.modeling.idef0.IDEF0Arrow;
import com.blackhorse.modeling.idef0.IDEF0Border;
import com.blackhorse.modeling.idef0.IDEF0Box;
import com.blackhorse.modeling.idef0.IDEF0Model;

/** 
 * 
 * @modelguid {493905BF-4C10-4BA4-8877-12976BEA560C} 
 */
public class IDEF0SibSACModelBuilder implements SibSACModelBuilder {
	/** @modelguid {170CF67A-D871-429E-9380-A672D80F1317} */
	protected List result;
	
	/** @modelguid {1095F8BE-7B1F-49F0-8F51-47C1802AA08C} */
	protected List source;

	/** @modelguid {1B1D79A2-64D9-4119-A749-AE79420B18EC} */
	public void build() {
		// Создаем контейнер под результат
		result = new ArrayList();
		
		// Группируем список строк по функциям (столбец 7)
		Map rowsMap = new HashMap();
		for (int i = 0; i < source.size(); i++) {
			SibSACModelRow row = (SibSACModelRow) source.get(i);
			List rowList = (List) rowsMap.get(row.get(7).toString().trim());
			if (rowList == null) {
				rowList = new ArrayList();
				rowsMap.put(row.get(7).toString().trim(), rowList);
			}
			rowList.add(row);
		}
		
		// Строим модель для каждой группы и добавляем ее в результат
		for (Iterator it = rowsMap.values().iterator(); it.hasNext();) {
			result.add(buildModel((List) it.next()));
		}
	}

	/** @modelguid {AE6E385F-F831-4FBD-AEC1-C1A69586246F} */
	public void build(Object rows) {
	    setSource(rows);
	    build();
	}
	
	/** @modelguid {04E72D71-026B-4AE8-829B-4686F943F32E} */
	protected IDEF0Model buildModel(List rows) {
		Map boxes = new HashMap();
		SibSACModelRow row = null;
		String boxName = null;
		String arrowName = null;
		IDEF0Box box = null;
		IDEF0Box rootBox = null;
		IDEF0Box mainBox = null;
		IDEF0Box centerBox = null;
		IDEF0Arrow arrow = null;
		
		IDEF0Model model = new IDEF0Model();
		
		// Виртуальный
		row = (SibSACModelRow)rows.get(0);		
		boxName = row.get(0).toString().trim();
		rootBox = model.getRootBox();
	   		rootBox.setName(boxName);
		
		// Основной
		boxName = row.get(0).toString().trim();
		mainBox = new IDEF0Box();
		   	mainBox.setName(boxName);
		rootBox.getChildren().add(mainBox);
		
		// Центральный
		boxName = row.get(7).toString().trim();
		centerBox = new IDEF0Box();
			centerBox.setName(boxName);
		List container = mainBox.getChildren();
		container.add(centerBox);
		
		for (int i = 0; i < rows.size(); i++) {
			row = (SibSACModelRow)rows.get(i);
			boxName = row.get(4).toString().trim() + " " + row.get(5).toString().trim();
			box = (IDEF0Box)boxes.get(boxName);
			if (box == null) {
				box = new IDEF0Box();
				box.setName(boxName);
				boxes.put(box.getName(), box);
				container.add(box);
			}
			
			// Входы и выходы
			arrowName = row.get(2).toString().trim() + "\n" + row.get(3).toString().trim();
			String direction = row.get(1).toString().trim();
			if (direction.equalsIgnoreCase("ИСХ")) {
			    arrow = new IDEF0Arrow();
				    arrow.setName(arrowName);
				    arrow.setSource(centerBox, IDEF0Arrow.OUTPUT);
				    arrow.setTarget(box, IDEF0Arrow.INPUT);
			    container.add(arrow);
			}
			if (direction.equalsIgnoreCase("ВХ")) {
			    arrow = new IDEF0Arrow();
			    	arrow.setName(arrowName);
				    arrow.setSource(box, IDEF0Arrow.OUTPUT);
				    arrow.setTarget(centerBox, IDEF0Arrow.INPUT);
			    container.add(arrow);
			}
			
			// Управление
			arrowName = row.get(6).toString().trim();
			if (!"".equalsIgnoreCase(arrowName) && !"нет".equalsIgnoreCase(arrowName)) {
			    arrow = new IDEF0Arrow();
				    arrow.setName(arrowName);
				    arrow.setSource(new IDEF0Border(), IDEF0Arrow.CONTROL);
				    arrow.setTarget(centerBox, IDEF0Arrow.CONTROL);
			    container.add(arrow);
			}

		}
		
		return model;
	}

	/** @modelguid {F7FDE4B6-2BCA-4D31-84D5-5E96C23604E2} */
	public Object getResult() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return result;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {B4420E92-5D95-4BC4-9A08-E491BAD8405D} */
	public void setSource(Object rows) {
		this.source = (List) rows;
	}

}

