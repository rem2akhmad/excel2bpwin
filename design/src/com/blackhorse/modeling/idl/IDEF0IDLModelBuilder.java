package com.blackhorse.modeling.idl;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.blackhorse.modeling.Element;
import com.blackhorse.modeling.Point;
import com.blackhorse.modeling.idef0.IDEF0Arrow;
import com.blackhorse.modeling.idef0.IDEF0Border;
import com.blackhorse.modeling.idef0.IDEF0Box;
import com.blackhorse.modeling.idef0.IDEF0Diagram;
import com.blackhorse.modeling.idef0.IDEF0Model;

/** 
 * Строит IDL представление из внутренего представления модели IDEF0
 * @modelguid {5123E92F-4344-4FC9-9B7F-37C52502FB58} 
 */
public class IDEF0IDLModelBuilder implements IDLModelBuilder {
	/** @modelguid {0D3250A2-4CA7-4504-A084-E7B4C067C450} */
	protected IDEF0Model source;

	/** @modelguid {52C24A25-8DA6-40E3-8D49-27CB60D57DE7} */
	protected String result;

	/** @modelguid {DB187732-E1AF-42CD-B938-8D1D06019EE2} */
	public void build() {
		/*
		 * Обходим элементы модели (для начала можно последовательно)
		 * и переводим информацию о каждом элементе в текст.
		 */
		String res = "";
		
		res += " KIT ;\n";
		res += buildHeader();
		
		res += buildDiagrams(source.getRootBox());
		
		res += buildGlossary(source.getRootBox());
		
		res +=	"   ENDMODEL ;\n" +
				" ENDKIT ;\n";
	 	
		result = res;
	}
	
	/**
	 * Создает глоссарий к модели 
	 * @modelguid {167013D1-CAE8-4C08-A7FD-91F722B349B3}
	 */
	protected String buildGlossary(IDEF0Box box) {
	    String res = "";
	    
	    res += "    DIAGRAM GLOSSARY A0G {LWI A " + "\n";
	    
	    // Исключаем контекстрый бокс
	    Set boxes = source.getBoxSet();
	    for (Iterator it = boxes.iterator(); it.hasNext();) {
            IDEF0Box b = (IDEF0Box) it.next();
            res += buildBoxTerm(b);
        }
	    
	    res += "     ENDGLOSS } ;" + "\n";
	    res += "     GLOSSARY ;" + "\n";
	    
	    Set arrows = source.getArrowSet();
	    for (Iterator it = arrows.iterator(); it.hasNext();) {
            IDEF0Arrow a = (IDEF0Arrow) it.next();
            res += buildArrowTerm(a);
        }
	    
	    res += "     ENDGLOSS ;" + "\n";
	    res += "    ENDDIAGRAM ;" + "\n";
	    
	    return res;
	}
	
	/** @modelguid {A180663F-9E3A-4BBC-B2BB-C41727EE1389} */
    private String buildBoxTerm(IDEF0Box box) {
        String res = "" + 
		"      TERM '" + box.getName() + "'" + "\n" + 
		"      '" + box.getDefinition() + "'" + "\n" + 
		"      N '" + box.getNote() + "'" + "\n" + 
		"      S '"+ box.getDiagram().getStatus()+"'" + "\n" + 
		"      C 255 255 255" + "\n" + 
		"      F 1.000000" + "\n" + 
		"\n";
      
        return res;
    }
    
	/** @modelguid {2E05785F-C865-44CF-9263-5DB2E7594F0D} */
    private String buildArrowTerm(IDEF0Arrow arrow) {
        String res = "" + 
		"      TERM '" + arrow.getName() + "'" + "\n" +
		"      '" + arrow.getDefinition() + "'" + "\n" +
		"      {LWI N '" + arrow.getNote() + "'" + "\n" +
		"      S 'WORKING'" + "\n" +
		"      C 255 255" + "\n" +
		"  } ;" + "\n" +
		"\n";
      
        return res;
    }

    /** 
	 * Рекурсивно объодит дерево диаграмм и генерирует IDL-текст для каждой диаграммы
	 * @modelguid {6A203F86-0C52-49DE-9E33-C2BD77BBBF48} 
	 */
	protected String buildDiagrams(IDEF0Box box) {
		String res = "";
		List children = box.getChildren();
		
		// Если дочерних элементов нет, то нечего и диаграммы строить ?)
		if (children.size() == 0) {
			return res;
		}

		res += buildStartDiagram(box.getDiagram());
		
		// боксы
		for (int i = 0; i < children.size(); i++) {
			Element element = (Element) children.get(i);
			if (element instanceof IDEF0Box) {
		 		IDEF0Box b = (IDEF0Box) element;
				res += buildBox(b);
			}
		}
		
		// стрелки
		for (int i = 0; i < children.size(); i++) {
			Element element = (Element)children.get(i);
			if (element instanceof IDEF0Arrow) {
				res += buildArrow((IDEF0Arrow)element);
			}
		}

		res += buildEndDiagram();
		
		// Для каждого бокса строим дочернюю диаграмму
		for (int i= 0; i < children.size(); i++) {
			Element element = (Element)children.get(i);
			if (element instanceof IDEF0Box) {
				res += buildDiagrams((IDEF0Box)element);
			}
		}
		return res;
	}
	
	/** @modelguid {11AB80FD-A84A-4EE6-863C-E6D88AF10A26} */
	protected String buildHeader() {
	    SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
	    df.applyPattern("dd/MM/yyyy");
		String res = "\n" + 
			"  IDL VERSION 1.2.8 ;\n" + 
			"  TITLE 'Функция контекстной диаграммы' ;\n" + 
			"  AUTHOR '" + source.getAuthor()+	"' ;\n" + 
			"  CREATION DATE " + df.format(source.getDate())+" ;\n" + 
			"  PROJECT NAME '" + source.getProjectName() + "' ;\n" + 
			"\n" + 
			"  MODEL '" + source.getProjectName() +"'\n" + 
			"    {LWI\n" + 
			"    F 0 -13 0 0 0 400\n" + 
			"      0 0 0 0 3 2 1 34\n" + 
			"      100 'Arial Cyr'\n" + 
			"    F 1 -13 0 0 0 400\n" + 
			"      0 0 0 0 3 2 1 34\n" + 
			"      180 'Arial Cyr'\n" + 
			"    F 2 -11 0 0 0 400\n" + 
			"      0 0 0 0 3 2 1 49\n" + 
			"      80 'Arial Cyr'\n" + 
			"    F 3 -13 0 900 900 400\n" + 
			"      0 0 0 0 3 2 1 34\n" + 
			"      100 'Arial Cyr'\n" +
			"    F 4 -8 0 0 0 400\n" +
			"      0 0 0 0 3 2 1 34\n" +
			"      60 'Arial Cyr'\n" +
			"    F 5 -8 0 0 0 700\n" +
			"      0 0 0 0 3 2 1 34\n" +
			"      60 'Arial Cyr'\n" +

			"    D 0 0 0 0 0 1 0 0 2\n" + 
			"    G 0 1\n" + 
			"    T '" + source.getStatus()+	"'\n" + 
			"    R 70\n" + 
			"    M 0  }\n" + 
			";\n" + 
			"    AUTHOR '" + source.getAuthor()+"' ;\n" + 
			"    PROJECT NAME '" + source.getProjectName() +	"' ;\n" + 
			"\n";
		
		return res;
	}
	
	/** @modelguid {84AB1369-6654-46C6-8BCC-CB8A79813E9C} */
	protected String buildStartDiagram(IDEF0Diagram diagramm) {
	    SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
	    df.applyPattern("dd/MM/yyyy");
		String res = "\n" +
			"    DIAGRAM GRAPHIC " + diagramm.getNode() + " ;\n" +
			"      CREATION DATE " + df.format(source.getDate())+ " ;\n" +
			"      REVISION DATE " + df.format(source.getDate())+ " ;\n" +
			"      TITLE '" + diagramm.getTitle() + "' ;\n" +
			"      C-NUMBER " + diagramm.getCNumber()+" ;\n" +
			"      STATUS " + source.getStatus() +" ;\n" +
			"\n";

		return res;
	}

	/** @modelguid {A5960CAB-BA31-4469-AB23-73B84C9CD8DA} */
	protected String buildEndDiagram() {
		String res = "\n" +
			"    ENDDIAGRAM ;\n" +
			"\n";

		return res;
	}

	/** 
	 * Генерирует текст IDL для бокса
	 * @modelguid {1F1D7C00-B0F4-4C25-9956-5DCE3503650D} 
	 */
	protected String buildBox(IDEF0Box box) {
		String res = "\n" +
			"      BOX " + box.getId() + " ;\n" +
			"        NAME '" + box.getName() + "' ;\n" +
			"        BOX COORDINATES " + box.getPoint1() + " " + box.getPoint2() + "  ;\n" +
			"      ENDBOX ;\n" +
			"\n";

		return res;
	}
	
	/** 
	 * Генерирует текст IDL для стрелки стре
	 * @modelguid {A3D9D61F-305A-4175-B91F-2980EAA6DF71} 
	 */
	protected String buildArrow(IDEF0Arrow arrow) {
		List path = arrow.getPath();
		String strPath = "";
		String strSource = "";
		String strSink = "";
		// источник
		String srcType = "";
		String srcLetter = "";
		String srcCoords = "";
		// границы итерации по элементам пути
		int idxStart = 0;
		int idxEnd = path.size();

		if (arrow.getSource() instanceof IDEF0Border) {
			srcType =  " BORDER ";
			srcCoords = ((Point)path.get(0)) + "";
			idxStart = 1;
		}
		if (arrow.getSource() instanceof IDEF0Box) {
			srcType = " BOX " + ((IDEF0Box)arrow.getSource()).getId();
		}
		switch (arrow.getSourceType()) {
			case IDEF0Arrow.CONTROL : {
				srcLetter += "C";
			} break;
			case IDEF0Arrow.MECHANISM : {
				srcLetter += "M";
			} break;
			case IDEF0Arrow.INPUT : {
				srcLetter += "I";
			} break;
			case IDEF0Arrow.OUTPUT : {
				srcLetter += "O";
			} break;
		}
		// TODO порядковый номер для бокса (бордюра) сделать вычисляемым
		strSource += srcType + srcLetter + "1" + srcCoords;
		
		// SINK
		String sinkType = "";
		String sinkLetter = "";
		String sinkCoords = "";
		
		if (arrow.getTarget() instanceof IDEF0Border) {
			sinkType = " BORDER ";
			sinkCoords = ((Point)path.get(path.size()-1)) + "";
			idxEnd = path.size()-1;
		}
		if (arrow.getTarget() instanceof IDEF0Box) {
			sinkType = " BOX " + ((IDEF0Box)arrow.getTarget()).getId();
		}
		
		switch (arrow.getTargetType()) {
			case IDEF0Arrow.CONTROL : {
				sinkLetter += "C";
			} break;
			case IDEF0Arrow.MECHANISM : {
				sinkLetter += "M";
			} break;
			case IDEF0Arrow.INPUT : {
				sinkLetter += "I";
			} break;
			case IDEF0Arrow.OUTPUT : {
				sinkLetter += "O";
			} break;
		}
		strSink += sinkType + sinkLetter + "1" + sinkCoords;
		
		// PATH
		for (int i = idxStart; i < idxEnd; i++) {
			strPath += " " + ((Point)path.get(i)).toString();
		}
		
		String res = "\n" +
			"		ARROWSEG " + arrow.getId() + " ;\n" +
	        "			SOURCE "+ strSource + ";\n" +
			"			PATH "+ strPath + " ;\n" +
			"			LABEL '{LWI I 4 255 255 }" + arrow.getName() +"';\n" +
			"			LABEL COORDINATES " + arrow.getLabelCoordinates() + ";\n" +
			"			SINK "+ strSink +" ;\n" +
			"		ENDSEG ;\n";
		return res;		
	}

	/**
	 * Возвращает модель во внутреннем представлении 
	 * @modelguid {D283E295-2AA0-4DB8-8FE6-3422151BFE73} 
	 */
	public IDEF0Model getModel() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return source;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {80AB6432-2241-42CB-9144-E9AA9ABDC834} */
	public void setModel(IDEF0Model aModel) {
		/*Begin Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
		
		source = aModel;
		/*End Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
	}

	/**
	 * Возвращает построенную модель в формате IDL 
	 * @modelguid {8C18BD80-0DE3-4C9A-864D-3426128B5D48} 
	 */
	public Object getResult() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return result;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/**
	 * Строит модель в формате IDL 
	 * @modelguid {460E943B-7F5A-42E6-BB6C-2B9A4F186E51} 
	 */
	public void build(Object model) {
		setSource(model);
		build();
	}

	/**
	 * Указывает какую модель (в данном случе IDEF0Model) нужно преобразовать в формат IDL 
	 * @modelguid {CCBCC7AB-1D3F-49EE-A584-C98826C22811} 
	 */
	public void setSource(Object model) {
		setModel((IDEF0Model) model);
	}

}

