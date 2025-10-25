package com.blackhorse.modeling.idef0;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * Модель IDEF0
 * @modelguid {DBC53B0F-3333-4F48-8B57-2304C7788C88} 
 */
public class IDEF0Model {
    /**
     * Автор модели
     * @modelguid {2467F46B-CBAD-4799-B14B-CC13BF349C81}
     */
    private String author = "";
    /**
     * Дата создания модели
     * @modelguid {701EDF3F-C8B3-4AC2-B424-EA78D5CFDB0C}
     */
    private Date date = new Date(System.currentTimeMillis());
    /**
     * Название проекта
     * @modelguid {0EACA320-B26C-4ACF-9DBA-EE379235475A}
     */
    private String projectName = "";
    /**
     * Статус модели
     * @modelguid {38AF9C7A-5DCD-4F61-B4B3-24EB1F428619}
     */
    private String status = "WORKING";
    
	/**
	 * Виртуальный корневой бокс
	 * @modelguid {846CFF33-364D-4B20-AC54-74B40A69EE9B}
	 */
	protected IDEF0Box rootBox = new IDEF0Box ("-0");

	/** @modelguid {F0C590F3-260E-425B-BDF5-B353B98613A6} */
	public IDEF0Box getRootBox() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return rootBox;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {91EC0110-D910-407C-BE3F-90DA7A5E1FC1} */
	public void setRootBox(IDEF0Box aRootBox) {
		/*Begin Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
		
		rootBox = aRootBox;
		/*End Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
	}

	/**
	 * 
	 * @return
	 * @modelguid {7BDED52A-1B0E-45E5-91D3-386BEDB087AD}
	 */
	public Set getArrowSet() {
	    return getArrowSet(getRootBox());
	}
	
	/**
	 * Возвращает множество стрелок из модели
	 * @param root
	 * @return
	 * @modelguid {F9701DA8-5996-416B-BD78-0B116A3E25A8}
	 */
	public Set getArrowSet(IDEF0Box root) {
	    // TODO Возможно, надо будет использовать Map
	    Set set = new HashSet();
	    List kids = root.getChildren();
	    for (int i = 0; i < kids.size(); i++) {
	        IDEF0Element element = (IDEF0Element) kids.get(i);
	        if (element instanceof IDEF0Arrow) {
	            set.add(element);
            } else if (element instanceof IDEF0Box) {
                set.addAll(getArrowSet((IDEF0Box) element));
            }
	    }
	    
	    return set;
	}
	/**
	 * Возвращает множество боксов из модели
	 * @return
	 * @modelguid {F914A425-EF93-4478-85A9-DBC3B7805CE3}
	 */
	public Set getBoxSet() {
	    return getBoxSet((IDEF0Box) getRootBox().getChildren().get(0));
	}
	
	/**
	 * Возвращает множество боксов поддерева иерархии диаграмм
	 * @param root корень поддерева
	 * @return
	 * @modelguid {0F7DA1A9-CE43-4443-9E13-50A408D277DC}
	 */
	protected Set getBoxSet(IDEF0Box root) {
	    // TODO Возможно, надо будет использовать Map
	    Set set = new HashSet();
	    set.add(root);
	    List kids = root.getChildren();
	    for (int i = 0; i < kids.size(); i++) {
	        IDEF0Element element = (IDEF0Element) kids.get(i);
	        if (element instanceof IDEF0Box) {
                set.addAll(getBoxSet((IDEF0Box) element));
            }
	    }
	    
	    return set;
	}
	
    /**
     * @return Returns the author.
     * @modelguid {3D70AF70-2F2B-4D6A-A256-A965139E644A}
     */
    public String getAuthor() {
        return author;
    }
    /**
     * @param author The author to set.
     * @modelguid {B8BAFB04-7118-4F93-8347-D3B2710B7CDB}
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * @return Returns the date.
     * @modelguid {3B61232A-369A-48C0-A5EF-B64794DA12B6}
     */
    public Date getDate() {
        return date;
    }
    /**
     * @param date The date to set.
     * @modelguid {C952EEF4-77E9-4BE0-893F-D98FE4460E1E}
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * @return Returns the projectName.
     * @modelguid {7BF5A02A-FEF9-4AF5-B57E-2FC47A7DFFBB}
     */
    public String getProjectName() {
        return projectName;
    }
    /**
     * @param projectName The projectName to set.
     * @modelguid {3E91AD05-D546-455C-997A-5D6C93341CDD}
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    /**
     * @return Returns the status.
     * @modelguid {8ED0B1B6-12CC-4F5B-9D1D-CEE09F4247D4}
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     * @modelguid {E7CBDB0C-4467-44C1-AAA8-FC99A23CD5F0}
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

