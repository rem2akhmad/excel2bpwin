package com.blackhorse.modeling.idef0;
/**
 * Диаграмма модели IDEF0
 * @modelguid {C46F0BA3-BE07-4152-BB7E-CA4F4ED68C76}
 */
public class IDEF0Diagram {
    /**
     * C-Number
     * @modelguid {2F0BC4BB-67D2-46B2-B9F9-20E10BD00A38}
     */
    private String cNumber = "";
    /**
     * Статус диаграммы
     * @modelguid {51DA88CD-F5AF-4C06-9BD4-E7CB5C47CEA2}
     */
    private String status = "WORKING";
	/**
	 * 
	 * @modelguid {E0B64916-57CC-4821-873A-519CFB1FBB8B}
	 */
	protected IDEF0Box box = null;
	
	/**
	 * Constructor for IDEF0Diagram.
	 * @modelguid {94F8EF6E-B673-4E8E-81BC-EF14BB2D1DD3}
	 */
	protected IDEF0Diagram() {
		super();
	}
	
	/**
	 *  @modelguid {208C67A3-5893-4D36-AF00-0C143A3A2C7E} 
	 */
	public IDEF0Diagram(IDEF0Box box) {
		super();
		this.box = box;
	}
	
	
	/** 
	 * Возвращает идентификатор диаграммы
	 * @modelguid {55A35B74-8487-413A-B3A8-F0A3576D84C9} 
	 */
	public String getNode() {
		// TODO рекурсивно собирать id боксов
		return "A" + box.getId();
	}	
	
	/**
	 * Возвращает назавание диаграммы 
	 * @modelguid {667AC963-D634-4D96-8AF0-4AD1FDFD4A82} 
	 */
	public String getTitle() {
		return box.getName();
	}
    /**
     * @return Returns the cNumber.
     * @modelguid {929A3A56-DB91-4B3B-85E8-CA944E824287}
     */
    public String getCNumber() {
        return cNumber;
    }
    /**
     * @param number The cNumber to set.
     * @modelguid {F750EE8E-BB98-40A2-BA51-1ECD602EFA0F}
     */
    public void setCNumber(String number) {
        cNumber = number;
    }
    /**
     * @return Returns the status.
     * @modelguid {E0A12BF6-6486-4393-A690-0C2E42C53981}
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     * @modelguid {66CC691A-7118-4533-8B09-2F110A80E780}
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

