package com.blackhorse.modeling.sibsac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.blackhorse.converting.sibsac.SibSACModelRow;
import com.blackhorse.modeling.Element;
import com.blackhorse.modeling.idef0.IDEF0Arrow;
import com.blackhorse.modeling.idef0.IDEF0Border;
import com.blackhorse.modeling.idef0.IDEF0Box;
import com.blackhorse.modeling.idef0.IDEF0Element;
import com.blackhorse.modeling.idef0.IDEF0Model;

/** 
 * Стоит модель внутренного представления IDEF0 на основе подготовленного источника данных
 * @modelguid {1D55F773-B330-4A3C-9095-5AD1BB60A52F} 
 */
public class IDEF0SibSACFullModelBuilder implements SibSACModelBuilder {
    /**
     * Номера столбцов в файле excel
     * @modelguid {16DE0AB9-0B8B-4C17-9B20-C7DBF9E0520E}
     */
    private int modelName	= 0;
	/** @modelguid {F3591480-4C62-40FC-9EF1-43AAA491F046} */
    private int direction	= 1;
	/** @modelguid {B39DA121-F554-4499-BF39-9283DEEC0092} */
    private int arrowName1	= 2;
	/** @modelguid {1E9837AC-A6A8-48FE-A05A-6E46410E7903} */
    private int arrowName2	= 3;
	/** @modelguid {9FF3687C-8364-48CF-A07B-483FE5216934} */
    private int orgName1	= 4;
	/** @modelguid {B19938F5-9FF4-4E66-B015-9A7A4FD6EB58} */
    private int orgName2	= 5;
	/** @modelguid {9F74273F-EFCB-4DD0-A8A9-DB3F763C2DED} */
    private int controlName	= 6;
	/** @modelguid {42372C00-C9DD-4DBE-9467-E96FEA6722B5} */
    private int funcName	= 7;
	/** @modelguid {424DC849-C456-4C97-A674-CB4B01D4FCA4} */
    private int shortArrowName	= 8;
    
	/** @modelguid {382916BE-1F01-4E15-9C7B-C9B8D2413AA0} */
    private boolean projectNameByModel = false;
    
    /**
     * @return Returns the isProjectNameByModel.
     * @modelguid {EB8CE868-D70C-43AE-8C48-16081E12055A}
     */
    public boolean isProjectNameByModel() {
        return projectNameByModel;
    }
    /**
     * @param isProjectNameByModel The isProjectNameByModel to set.
     * @modelguid {E7E9E021-CD37-49E2-B0E0-5E1DF8939520}
     */
    public void setProjectNameByModel(boolean projectNameByModel) {
        this.projectNameByModel = projectNameByModel;
    }
    /**
     * Автор
     * @modelguid {C201C2FA-6005-4E14-BF64-B0D3844EF2CE}
     */
    private String author = "";
    /**
     * Название проекта
     * @modelguid {78C5278F-F213-4D17-AB1C-E50F070DD0C8}
     */
    private String projectName = "";
    /**
     * Статус
     * @modelguid {DE265459-F03D-4EFA-9184-91F4F91A0291}
     */
    private String status = "WORKING";
    
    /**
     * Максимальное количество боксов на диаграмме 
     * @modelguid {404FD902-7F80-495D-937A-36C135DE8EF0}
     */
    protected int MAX_BOXES_ON_DIAGRAM = 49;
	/** @modelguid {6586C3D1-F2A2-4B9E-861C-BC0CCA75FBD9} */
	protected static String LEVEL_ONE_BOX_PREFIX = "Ф ";
	
	/** @modelguid {557C6EC7-3C73-4055-AB53-A9CB8CF28865} */
	protected List result;

	/** @modelguid {999D98B3-6D08-42E4-9941-877F034BEB35} */
	protected List source;

	/** @modelguid {A907698D-C021-40E8-B0E9-AE9A7E10CD7A} */
	protected IDEF0Model model;
	
	/** @modelguid {26B0D666-4CF3-4DE5-AE09-12C8EBEA2FA4} */
	protected IDEF0Box parent;
	
//	/** @modelguid {FA2A0E25-94D7-46EE-ABFD-E27486C60E02} */
//	protected Map boxes = new HashMap();

	/** @modelguid {E2EE3095-625B-4485-A2CE-ABDFF240CF91} */
	public Object getResult() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return result;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {85C56D1B-4106-4E61-BB17-22C8D1C32865} */
	public void build(Object rows) {
	    setSource(rows);
	    build();
	}

	/** @modelguid {342D94DC-35DC-4907-B40B-A33896FFF8ED} */
	public void build() {
		// Создаем контейнер под результат
		result = new ArrayList();
		
		// Группируем список строк по департаментам (столбец 0)
		Map rowsMap = new HashMap();
		for (int i = 0; i < source.size(); i++) {
			SibSACModelRow row = (SibSACModelRow) source.get(i);
			String name = row.get(modelName).toString().trim();
			List rowList = (List) rowsMap.get(name);
			if (rowList == null) {
				rowList = new ArrayList();
				rowsMap.put(name, rowList);
			}
			rowList.add(row);
		}
		
		// Строим модель для каждой группы и добавляем ее в результат
		for (Iterator it = rowsMap.values().iterator(); it.hasNext();) {
		    // TODO сюда вставляем сплиттер модели
		    IDEF0Model mdl = buildModel((List) it.next());
		    List mdls = splitModel(mdl);
			result.addAll(mdls);
		}
	}
	
	/**
	 * "Разрезает" модель на несколько, если количество функций на диаграмме первого уровня превышает
	 * количество определенное в MAX_BOXES_ON_DIAGRAMM 
	 * @param mdl исходная модель
	 * @return список моделей
	 * @modelguid {B38BAFD4-D052-4B60-AE3E-BF3365655D96}
	 */
	protected List splitModel(IDEF0Model mdl) {
	    List ret = new ArrayList();
	    IDEF0Box rootBox = mdl.getRootBox();
	    IDEF0Box ctxBox = (IDEF0Box)mdl.getRootBox().getChildren().get(0);
	    List level1 = ctxBox.getChildren();
	    int countBoxes = 0;
	    IDEF0Model newModel = null;
	    IDEF0Box root = null;
	    IDEF0Box ctx = null;
	    for (int i = 0; i < level1.size(); i++) {
	        if (newModel == null) {
	            countBoxes = 0;
	            newModel = new IDEF0Model();
	            	newModel.setAuthor(mdl.getAuthor());
	            	newModel.setDate(mdl.getDate());
	            	newModel.setProjectName(mdl.getProjectName());
	            	newModel.setStatus(mdl.getStatus());
	            root = new IDEF0Box();
	            	root.setDefinition(rootBox.getDefinition());
	            	root.setId(rootBox.getId());
	            	root.setName(rootBox.getName());
	            	root.setNote(rootBox.getNote());
	            newModel.setRootBox(root);
	            ctx = new IDEF0Box();
	            	ctx.setDefinition(ctxBox.getDefinition());
	            	ctx.setId(ctxBox.getId());
	            	ctx.setName(ctxBox.getName());
	            	ctx.setNote(ctxBox.getNote());
	            ctx.setParent(root);
	            ret.add(newModel);
	        }
	        Element el = (Element)level1.get(i);
	        if (el instanceof IDEF0Box) {
	            ((IDEF0Box)el).setParent(ctx);	            
	            if (++countBoxes == MAX_BOXES_ON_DIAGRAM) {	                
	                newModel = null;
	            }
	        }
	    }
	    return ret;
	}

	/** @modelguid {D2DED72D-11A9-4B2E-A8DC-57F41C0B15B7} */
	public void setSource(Object rows) {
		this.source = (List) rows;
	}

	/** @modelguid {6734A95D-489F-436C-9B64-0DA5F0357F55} */
	protected IDEF0Model buildPreparedModel(List rows) {
		SibSACModelRow row = (SibSACModelRow) rows.get(0);
		String name = (String) row.get(this.modelName);
		
		// Создаем модель
		model = new IDEF0Model();

		// Создаем виртуальный бокс
		IDEF0Box root = model.getRootBox();
			root.setDefinition(name);
			root.setNote(name);
			root.setName(name);
		
//		// Очищаем список боксов
//		boxes.clear();
			
		return model;
	}

	/** @modelguid {7B932D7C-7A87-4697-90FA-3DD7320EB2F5} */
	protected IDEF0Box buildBoxLazy(String name) {
	    List list = parent.getChildren();
	    Map map = new HashMap();
	    for (int i = 0; i < list.size(); i++) {
	        IDEF0Element element = (IDEF0Element) list.get(i);
	        if (element instanceof IDEF0Box) {
		        map.put(element.getName(), element);
	        }
	    }
	    
		IDEF0Box box = (IDEF0Box) map.get(name);
		
		if (box == null) {
			box = new IDEF0Box();
				box.setName(name);
				box.setDefinition(name);
				box.setNote(name);
			box.setParent(parent);
		}
		
		return box;
	}
	
	/** @modelguid {E5AB3364-168F-406D-AC4F-B3BA5B09498F} */
	protected IDEF0Border buildBorderLazy(String name) {
	    List list = parent.getChildren();
	    Map map = new HashMap();
	    for (int i = 0; i < list.size(); i++) {
	        IDEF0Element element = (IDEF0Element) list.get(i);
	        if (element instanceof IDEF0Border) {
		        map.put(element.getName(), element);
	        }
	    }
	    
		IDEF0Border border = (IDEF0Border) map.get(name);
		
		if (border == null) {
		    border = new IDEF0Border();
		    	border.setName(name);
		    list.add(border);
		}
		
		return border;
    }
	
	/** @modelguid {36AB7723-7E7F-4B75-9F89-0C4471BEFF2D} */
	protected IDEF0Box buildContextBox(SibSACModelRow row) {
		String name = row.get(this.modelName).toString().trim();

		IDEF0Box box = buildBoxLazy(name);
		
		return box;
	}

	/** @modelguid {7710257F-DD80-41C1-9B5C-5E23951B9C27} */
	protected IDEF0Box buildLevelOneBox(SibSACModelRow row) {
		String name = row.get(this.funcName).toString().trim();

		IDEF0Box box = buildBoxLazy(name);
		
		return box;
	}

	/** @modelguid {95745A3B-5982-4DFF-BEAC-FF32E722C295} */
	protected IDEF0Box buildLevelTwoCenterBox(SibSACModelRow row) {
		String name = row.get(this.funcName).toString().trim();

		IDEF0Box box = buildBoxLazy(name);
		
		return box;
	}

	/** @modelguid {D5197254-FEEC-486C-B5B4-7F7BA67A7F98} */
	protected IDEF0Box buildLevelTwoBounderBox(SibSACModelRow row) {
		String name = row.get(this.orgName1).toString().trim() + " " + row.get(this.orgName2).toString().trim();

		IDEF0Box box = buildBoxLazy(name);
		
		return box;
	}

	/** @modelguid {44D5401C-F92F-403F-9393-B2F3378469E5} */
	protected IDEF0Arrow buildArrow(SibSACModelRow row, IDEF0Box center, IDEF0Box bounder) {
		String defin;
		String name;
		IDEF0Arrow arrow = null;
		
		// Входы и выходы
		defin = row.get(this.arrowName1).toString().trim() + " " + row.get(this.arrowName2).toString().trim();
		name = row.get(this.shortArrowName).toString().trim();
		String direction = row.get(this.direction).toString().trim();
		if (direction.equalsIgnoreCase("ИСХ")) {
		    arrow = new IDEF0Arrow();
			    arrow.setName(name);
			    arrow.setDefinition(defin);
			    arrow.setNote(defin);
			    arrow.setSource(center, IDEF0Arrow.OUTPUT);
			    arrow.setTarget(bounder, IDEF0Arrow.INPUT);
			parent.getChildren().add(arrow);
		}
		if (direction.equalsIgnoreCase("ВХ")) {
		    arrow = new IDEF0Arrow();
		    	arrow.setName(name);
			    arrow.setDefinition(defin);
			    arrow.setNote(defin);
			    arrow.setSource(bounder, IDEF0Arrow.OUTPUT);
			    arrow.setTarget(center, IDEF0Arrow.INPUT);
			parent.getChildren().add(arrow);
		}
		
		return arrow;
	}
	
	/** @modelguid {588AC7FB-966A-4641-B329-F4691F803E8A} */
	protected IDEF0Arrow buildControlArrow(SibSACModelRow row, IDEF0Box center) {
		String name = row.get(this.controlName).toString().trim();
		
	    List list = parent.getChildren();
	    Map map = new HashMap();
	    for (int i = 0; i < list.size(); i++) {
	        IDEF0Element element = (IDEF0Element) list.get(i);
	        if (element instanceof IDEF0Arrow) {
		        map.put(element.getName(), element);
	        }
	    }
	    
	    IDEF0Arrow arrow = (IDEF0Arrow) map.get(name);
	    
	    if (!"".equalsIgnoreCase(name) && !"нет".equalsIgnoreCase(name) && arrow == null) {
			IDEF0Border border = buildBorderLazy(name);
			
			arrow = new IDEF0Arrow();
			    arrow.setName(name);
			    arrow.setDefinition(name);
			    arrow.setNote(name);
			    arrow.setSource(border, IDEF0Arrow.CONTROL);
			    arrow.setTarget(center, IDEF0Arrow.CONTROL);
			parent.getChildren().add(arrow);
		}
		
		return arrow;
	}

    /** @modelguid {D6F12315-0F1B-4DC8-8BBF-A97489A6B317} */
	protected IDEF0Model buildModel(List rows) {
		model = buildPreparedModel(rows);
		
		for (int i = 0; i < rows.size(); i++) {
			SibSACModelRow row = (SibSACModelRow) rows.get(i);
			
			parent = model.getRootBox();
			
			IDEF0Box context = buildContextBox(row);
			parent = context;
			
			IDEF0Box main = buildLevelOneBox(row);
			parent = main;
			
			IDEF0Box center = buildLevelTwoCenterBox(row);
			
			IDEF0Box bounder = buildLevelTwoBounderBox(row);

			IDEF0Arrow control = buildControlArrow(row, center);

			IDEF0Arrow arrow = buildArrow(row, center, bounder);
		}
		model.setAuthor(author);
		// название проекта берется из projectName или зи модели
		if (isProjectNameByModel()) {
		    model.setProjectName(model.getRootBox().getName());
		} else {
		    model.setProjectName(projectName);
		}
		model.setStatus(status);
		return model;
	}

    /**
     * @return Returns the lEVEL_ONE_BOX_PREFIX.
     * @modelguid {56228EDE-B05D-4BF1-B8F0-9A1A4413E933}
     */
    public static String getLEVEL_ONE_BOX_PREFIX() {
        return LEVEL_ONE_BOX_PREFIX;
    }
    /**
     * @param level_one_box_prefix The lEVEL_ONE_BOX_PREFIX to set.
     * @modelguid {79592E95-6015-486D-B3D4-36CFD1259BE9}
     */
    public static void setLEVEL_ONE_BOX_PREFIX(String level_one_box_prefix) {
        LEVEL_ONE_BOX_PREFIX = level_one_box_prefix;
    }
    /**
     * @return Returns the arrowName1.
     * @modelguid {987D51D9-4E75-433D-A04A-6627F36CDB94}
     */
    public int getArrowName1() {
        return arrowName1;
    }
    /**
     * @param arrowName1 The arrowName1 to set.
     * @modelguid {9468BCF0-31A4-4413-B1BA-080153CF0258}
     */
    public void setArrowName1(int arrowName1) {
        this.arrowName1 = arrowName1;
    }
    /**
     * @return Returns the arrowName2.
     * @modelguid {52A43FFD-4684-4CEF-8873-1C45F55D3DD4}
     */
    public int getArrowName2() {
        return arrowName2;
    }
    /**
     * @param arrowName2 The arrowName2 to set.
     * @modelguid {87BA476B-C38E-497C-81E0-E446F69115F6}
     */
    public void setArrowName2(int arrowName2) {
        this.arrowName2 = arrowName2;
    }
    /**
     * @return Returns the controlName.
     * @modelguid {D1D63031-8748-4388-B22E-A852A8C3C7D0}
     */
    public int getControlName() {
        return controlName;
    }
    /**
     * @param controlName The controlName to set.
     * @modelguid {73568474-70D7-45DE-92AF-A256D80264F2}
     */
    public void setControlName(int controlName) {
        this.controlName = controlName;
    }
    /**
     * @return Returns the direction.
     * @modelguid {E712469D-8CDA-4315-93D3-8A612391BDAB}
     */
    public int getDirection() {
        return direction;
    }
    /**
     * @param direction The direction to set.
     * @modelguid {25CF4314-A955-4EC1-98D9-FF99E1361BD0}
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }
    /**
     * @return Returns the funcName.
     * @modelguid {84EECEA1-73E2-4673-A203-DA7907F1E8E2}
     */
    public int getFuncName() {
        return funcName;
    }
    /**
     * @param funcName The funcName to set.
     * @modelguid {D9C2F5E3-6BCB-4959-A354-7CB3F2B1F2C5}
     */
    public void setFuncName(int funcName) {
        this.funcName = funcName;
    }
    /**
     * @return Returns the modelName.
     * @modelguid {F8B8EA7B-96D0-4589-9DA2-F5CEB0BD1C36}
     */
    public int getModelName() {
        return modelName;
    }
    /**
     * @param modelName The modelName to set.
     * @modelguid {9489E1C7-D438-4A9E-8C3C-0EF37731072F}
     */
    public void setModelName(int modelName) {
        this.modelName = modelName;
    }
    /**
     * @return Returns the orgName1.
     * @modelguid {A4442635-1FB8-466B-B05C-4D9ED04E6DC6}
     */
    public int getOrgName1() {
        return orgName1;
    }
    /**
     * @param orgName1 The orgName1 to set.
     * @modelguid {06F0198A-7AA1-43F3-BC0E-9743671EEE84}
     */
    public void setOrgName1(int orgName1) {
        this.orgName1 = orgName1;
    }
    /**
     * @return Returns the orgName2.
     * @modelguid {50AF1B9F-589D-4F0F-93E1-113FBA4A12DA}
     */
    public int getOrgName2() {
        return orgName2;
    }
    /**
     * @param orgName2 The orgName2 to set.
     * @modelguid {5EF0EB5D-529D-4402-80E1-00935A6AF37F}
     */
    public void setOrgName2(int orgName2) {
        this.orgName2 = orgName2;
    }
    /**
     * @return Returns the shortArrowName.
     * @modelguid {A49F7522-761E-4E15-99C4-0008175ADB28}
     */
    public int getShortArrowName() {
        return shortArrowName;
    }
    /**
     * @param shortArrowName The shortArrowName to set.
     * @modelguid {3B1480D8-CD80-481D-8DF8-445676D395A2}
     */
    public void setShortArrowName(int shortArrowName) {
        this.shortArrowName = shortArrowName;
    }
    /**
     * @param author The author to set.
     * @modelguid {944369E8-5CB2-417C-8FD1-36ABFE5468BA}
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * @param projectName The projectName to set.
     * @modelguid {E62E7507-D30D-4C47-AB87-5DFA85FB9806}
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    /**
     * @param status The status to set.
     * @modelguid {A9E2A963-4D91-4CE1-8486-B4906864FDA9}
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

