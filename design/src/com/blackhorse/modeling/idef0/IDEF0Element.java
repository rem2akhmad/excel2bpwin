package com.blackhorse.modeling.idef0;
import com.blackhorse.modeling.Element;
/** 
 * Ёлемент диаграммы IDEF0
 * @modelguid {8F7DC5D4-BDAC-44F2-943D-301EB593439B} 
 */
public abstract class IDEF0Element implements Element {
	/** @modelguid {8419F309-F30F-467B-B277-83550BC69C08} */
	protected String name;

	/** @modelguid {9AA4437C-774B-4CDA-9D82-F72EF4502853} */
	protected String id;
	
	/** @modelguid {E1179DB6-E454-4C54-923E-8DA7233BBE2E} */
	protected String definition;
	
	/** @modelguid {981B1251-EF25-4200-9F53-48C135530DFD} */
	protected String note;

	/** @modelguid {F331C233-3AAF-4EF5-8037-DF0D42602ED4} */
	public String getName() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return name;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {F6AA27B6-096A-452F-81CD-BE7EABB1CEC3} */
	public void setName(String aName) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		name = aName;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}

	/** @modelguid {02CDD9A9-AEED-42A0-8234-C8214F79F4B1} */
	public String getId() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return id;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {63AFA236-1A3F-4D0F-BC36-EC50810836EF} */
	public void setId(String aId) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		id = aId;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}
	
	/** @modelguid {006AA511-FA41-4081-A397-FDA3050F2AAF} */
	public void setId(int id) {
		setId(Integer.toString(id));
	}

	/** @modelguid {1FDB5745-0ECB-409C-AAD1-BA93A8895E9B} */
	public IDEF0Element() {
		super();
	}

	/** @modelguid {B6AE685E-195D-47EB-A191-BCE0E7278CFD} */
	public IDEF0Element(String id) {
		super();
		setId(id);
	}

	/** @modelguid {B025EB33-D63A-46ED-8964-18D2331630F1} */
    public String getDefinition() {
        return definition;
    }
    
	/** @modelguid {E1F6FE68-AE38-458F-A258-4000EF2E80A8} */
    public void setDefinition(String definition) {
        this.definition = definition;
    }
    
	/** @modelguid {719EAA4A-DDB6-42DC-9466-51EA4A7E22F2} */
    public String getNote() {
        return note;
    }
    
	/** @modelguid {505653C2-1BCB-4235-9410-BFD25A9D1C14} */
    public void setNote(String note) {
        this.note = note;
    }
}

