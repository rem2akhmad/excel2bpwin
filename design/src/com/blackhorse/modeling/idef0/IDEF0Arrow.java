package com.blackhorse.modeling.idef0;

import java.util.ArrayList;
import java.util.List;

import com.blackhorse.modeling.Element;
import com.blackhorse.modeling.Point;

/** 
 * Стрелка на модели IDEF0
 * @modelguid {0043F31D-6FC7-49D3-A36B-595ADCF14EDA} 
 */
public class IDEF0Arrow extends IDEF0Element {
	/**
	 * Путь стрелки от начальной точки до конечной 
	 * @modelguid {9BF2058F-0E4D-4398-BC90-C5E66DB5FB8A} 
	 */
	protected List path = new ArrayList();
	
	/**
	 * Откуда исходит стрелка 
	 * @modelguid {F56F8414-B59C-447A-A6AB-49ABB10A4AD1} 
	 */
	protected Element source;

	/** 
	 * Куда заходит стрелка
	 * @modelguid {A41BF394-9055-4BB5-AEBA-A3545075E65A} 
	 */
	protected Element target;
	/**
	 * Координаты расположение ярлыка стрелки
	 * @modelguid {DD79FB63-D740-4EDE-B00E-0107BB092C70}
	 */
	protected Point labelCoordinates;

	/**
	 * 
	 * @modelguid {DDFDDB30-E668-4958-9F05-EE4691D23774}
	 */
	protected int sourceType;

	/**
	 * 
	 * @modelguid {0E0C0E5D-C12B-4E84-8286-099B58CC8430}
	 */
	protected int targetType;

	/**
	 * Вход
	 * @modelguid {2C275319-BC8E-4AFA-93BB-546BAD96B711}
	 */
	public static final int INPUT = 1;

    /**
     * @return Returns the labelCoordinates.
     * @modelguid {6C5107E1-D772-41A7-BC46-BAE30AC045B6}
     */
    public Point getLabelCoordinates() {
        return labelCoordinates;
    }
    /**
     * @param labelCoordinates The labelCoordinates to set.
     * @modelguid {84D65EC6-A138-4A37-A467-37D25AEA1741}
     */
    public void setLabelCoordinates(Point labelCoordinates) {
        this.labelCoordinates = labelCoordinates;
    }
	/**
	 * Выход
	 * @modelguid {62B1BE12-348A-4835-908F-FD6AC146EEED}
	 */
	public static final int OUTPUT = 2;
	
	/**
	 * Управление 
	 * @modelguid {1F7C1992-8116-4D04-A872-7045A3F5B8FD} 
	 */
	public static final int CONTROL = 3;
	
	/** 
	 * Механизм
	 * @modelguid {8D7133FE-0BD7-4EA1-AB15-57DFCDAC2598} 
	 */
	public static final int MECHANISM = 4;
	

	/** @modelguid {3EFCBD27-D703-44D4-8FA0-3EFA3EF76E41} */
	public IDEF0Arrow() {
		super();
	}

	/** @modelguid {A64BFE8F-FC74-47C9-9929-F5FD25149188} */
	public IDEF0Arrow(String id) {
		super(id);
	}

	/** @modelguid {EFE7CF13-9F0B-40EE-A04E-ADDBFF78B07C} */
	public Element getSource() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return source;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {D51D6756-6367-4F88-BCD3-5A9F24BEB65E} */
	public void setSource(Element aSource) {
		/*Begin Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
		
		source = aSource;
		/*End Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
	}

	/** @modelguid {A5AE71D8-D256-44B9-AAF3-29F6A4C4459A} */
	public Element getTarget() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return target;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {99A77C87-240A-4CDE-BE1A-BFB84C4CE801} */
	public void setTarget(Element aTarget) {
		/*Begin Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
		
		target = aTarget;
		/*End Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
	}

	/** @modelguid {69CE62D7-9C5C-4989-B5DB-CC75B90429D9} */
	public void setTarget(Element aTarget, int type) {
		setTarget(aTarget);
		setTargetType(type);
	}

	/** @modelguid {CACBE8C4-AD4E-4778-A20F-6CEE013BE91A} */
	public void setSource(Element aSource, int type) {
		setSource(aSource);
		setSourceType(type);
	}

	/** @modelguid {9E3B26A2-CC5F-45C3-85E2-CA38A14444F7} */
	public int getSourceType() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return sourceType;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {71FC4644-5312-4109-B3CA-CEA6D7FD7785} */
	public void setSourceType(int aSourceType) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		sourceType = aSourceType;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}

	/** @modelguid {FEBF0C85-B11F-43DE-AF95-1EBD63C536A3} */
	public int getTargetType() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return targetType;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {15B77A3E-81CE-48E0-A2C1-DF60D8382C26} */
	public void setTargetType(int aTargetType) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		targetType = aTargetType;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}
	/** @modelguid {7F4CCE6F-C225-4EA4-A207-01324C931A2E} */
	public java.util.List getPath() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return path;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

}

