package com.blackhorse.converting.sibsac;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.blackhorse.modeling.idef0.IDEF0MixtureLayout;
import com.blackhorse.modeling.idef0.IDEF0Model;
import com.blackhorse.modeling.idef0.Layouter;
import com.blackhorse.modeling.idl.IDEF0IDLModelBuilder;
import com.blackhorse.modeling.idl.IDLModelBuilder;
import com.blackhorse.modeling.sibsac.IDEF0SibSACFullModelBuilder;
import com.blackhorse.modeling.sibsac.SibSACModelBuilder;

/** @modelguid {456254ED-4184-4D96-A180-D1B5D99B3832} */
public class SibSACModelManager {
	/** @modelguid {ADB32259-0835-42B5-9988-0581FA285FCB} */
	protected SibSACModelBuilder sibBuilder = new IDEF0SibSACFullModelBuilder();
//    protected SibSACModelBuilder sibBuilder = new IDEF0SibSACModelBuilder();
	
	/** @modelguid {C8858C47-5AA2-490E-A2C9-8959772757B7} */
	protected Iterator modelsIterator = null;
	
	/**
	 * Список моделей
	 * @modelguid {A8DA5CDA-6A30-47AA-BC3D-D144B1563A55}
	 */
	protected List models = new ArrayList();
	
	/** @modelguid {A7EF25F4-7E20-42BB-998A-1E36458CB14E} */
	protected IDLModelBuilder idlBuilder = new IDEF0IDLModelBuilder();

	/** @modelguid {9162BEC1-6E10-498C-A498-89A9836383DB} */
//	protected Layouter layout = new IDEF0CenterLayout();
	protected Layouter layouter = new IDEF0MixtureLayout();
	
	/** @modelguid {B69B0F00-573A-4532-A379-15338B8E6DA3} */
    private int fileCounter = 0;

	/** @modelguid {9EF488FA-1D88-4171-8A0F-2C341ACFBE8C} */
	public void readModels(InputStream inStream) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(inStream);
        HSSFWorkbook book = new HSSFWorkbook(fs);

        // Строим модели по данным всех страниц
		for (int i = 0; i < book.getNumberOfSheets(); i++) {
		    HSSFSheet sheet = book.getSheetAt(i);
		    
		    // Собираем все записи страницы в один список
		    List rows = new ArrayList();
		    // Пропускаем первые 2 строки - заголовок
		    for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
			    HSSFRow xlsRow = sheet.getRow(j);
				if (xlsRow != null) {
				    rows.add(new SibSACModelRow(xlsRow));
				}
			}
			
			// Строим модели по созданному списку строк
			sibBuilder.build(rows);
			
			// Добавляем новые модели в общий список
			models.addAll((List) sibBuilder.getResult());
		}
		
		// Устанавливаем итератор моделей
		modelsIterator = models.iterator();
	}
	
	/** @modelguid {656983CB-24A1-490B-A3BE-7F70530C312B} */
	public void readModels(File file) throws IOException {
	    FileInputStream i = new FileInputStream(file);
	    readModels(i);
	}

	/** @modelguid {2959D41E-5DFA-4258-96F0-2CB786D741E8} */
	public void readModels(String fileName) throws IOException {
	    FileInputStream i = new FileInputStream(fileName);
	    readModels(i);
	}

	/** @modelguid {3789F86C-6BE4-4645-8611-54BF0D7E39E9} */
	public String getNextIDLModel() {
	    // Достаем модель из списка
	    IDEF0Model model = (IDEF0Model) modelsIterator.next();
	    
	    // Размещение модели
	    layouter.layout(model);
		// Переводим модель в IDL
		idlBuilder.build(model);
		
		// Возвращаем результат
		return idlBuilder.getResult().toString();
	}
	
	/** @modelguid {5621D3C1-4DE3-4225-AFF2-48A76619841B} */
	public boolean hasNextModel() {
	    return modelsIterator.hasNext();
	}
	
	/** @modelguid {F777F22D-4DF5-4E98-AFC3-BB0897E9D9AB} */
	public void writeNextModel(OutputStream stream) throws IOException {
	    String idl = getNextIDLModel();
	    stream.write(idl.getBytes());
	    stream.close();
	}

	/** @modelguid {A7827197-AB95-47DB-9FB0-BDAC53419896} */
	public void writeNextModel(File file) throws IOException {
	    FileOutputStream o = new FileOutputStream(file);
	    writeNextModel(o);
	}

	/** @modelguid {22BF07BF-D84F-46E6-97DC-363D5445AA6A} */
	public void writeNextModel(String directory) throws IOException {
	    writeNextModel(getNextFile(directory));
	}

    /** @modelguid {BB89E4EF-3AD7-43E4-8D89-B23CEB553646} */
    protected File getNextFile(String parentDirectory) {
        String name = null;
	    File file = null;
	    do {
		    name = "IDL" + fileCounter++ + ".idl";
	        file = new File(parentDirectory + "/" + name);
	    } while (file.exists());

	    return file;
    }
    
    /**
     * Возвращает количество загруженных моделей
     * @modelguid {200C9790-09E0-474C-AAB0-F0030BD93363}
     */
    public int modelsCount() {
        return models.size();
    }
    /**
     * @return Returns the idlBuilder.
     * @modelguid {945FE393-ABAC-4F8D-8CFD-5337C73DB135}
     */
    public IDLModelBuilder getIdlBuilder() {
        return idlBuilder;
    }
    /**
     * @param idlBuilder The idlBuilder to set.
     * @modelguid {735E77D7-C7DD-4C45-B631-D013F266F484}
     */
    public void setIdlBuilder(IDLModelBuilder idlBuilder) {
        this.idlBuilder = idlBuilder;
    }
    /**
     * @return Returns the layout.
     * @modelguid {50EBAF8F-D040-4689-A8AA-05CC503C3725}
     */
    public com.blackhorse.modeling.idef0.Layouter getLayouter() {
        return layouter;
    }
    /**
     * @param layout The layout to set.
     * @modelguid {78D5DA8F-A32B-4021-B9AE-10E54F888040}
     */
    public void setLayouter(com.blackhorse.modeling.idef0.Layouter aLayouter) {
        this.layouter = layouter;
    }
    /**
     * @return Returns the sibBuilder.
     * @modelguid {A202931A-39B7-4504-8832-807D805A88D1}
     */
    public SibSACModelBuilder getSibBuilder() {
        return sibBuilder;
    }
    /**
     * @param sibBuilder The sibBuilder to set.
     * @modelguid {EF844ADB-D211-4990-9377-A2D4BD40F3DA}
     */
    public void setSibBuilder(SibSACModelBuilder sibBuilder) {
        this.sibBuilder = sibBuilder;
    }
}

