package com.blackhorse.converting.sibsac.test;

import java.util.List;

import junit.framework.TestCase;

import com.blackhorse.converting.sibsac.SibSACModelManager;
import com.blackhorse.modeling.idef0.IDEF0Arrow;
import com.blackhorse.modeling.idef0.IDEF0Border;
import com.blackhorse.modeling.idef0.IDEF0Box;
import com.blackhorse.modeling.idef0.IDEF0CenterLayout;
import com.blackhorse.modeling.idef0.IDEF0MixtureLayout;
import com.blackhorse.modeling.idef0.IDEF0Model;
import com.blackhorse.modeling.idef0.Layouter;
import com.blackhorse.modeling.idl.IDEF0IDLModelBuilder;

/**
 * @author ra
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 * @modelguid {74B35D24-8FEA-4F71-8D33-0AA15EFBC6D3}
 */
public class SibSACModelManagerTest extends TestCase {
	/** @modelguid {80243C96-9A2A-4954-AFE8-D478DF61ACE1} */
	private IDEF0IDLModelBuilder builder;

	/** @modelguid {5BBD14FD-2241-4D56-B19D-523185C2293D} */
	private IDEF0Model model;

	/** @modelguid {875A733F-8431-47BE-9F04-36A70D1E5DB9} */
    private SibSACModelManager manager;

	/**
	 * Constructor for SibSACModelManagerTest.
	 * @param arg0
	 * @modelguid {6B43B200-F844-43D3-8281-8A45CCAF004A}
	 */
	public SibSACModelManagerTest(String arg0) {
		super(arg0);
	}
	
	/** @modelguid {38F666AA-3649-4DEB-8793-A51E4B3048AF} */
	public void testBuildIDEF0Model() throws Exception {
	    manager = new SibSACModelManager();
	    manager.readModels("./test/abc.xls");
	    int i = 0;
	    while (manager.hasNextModel() && i <  10) {	        
	        manager.writeNextModel("./test/IDL/"); 
	        i++;
	    }
	}
	
	/** @modelguid {04AA5134-74E2-4C88-9033-2CCAB08A5002} */
	public void testBuildIDLModel() {
		// Боксы 
		IDEF0Box root = model.getRootBox();		
			root.setName("Функция 0");
		
		IDEF0Box box0 = new IDEF0Box("0");
			box0.setName("Функция 0");
			root.getChildren().add(box0);
		List child0 = box0.getChildren();
		// центральный
		IDEF0Box box1 = new IDEF0Box("1");
			box1.setName("Функция 1");
			child0.add(box1);
		// входяцие
		IDEF0Box box2 = new IDEF0Box("2");
			box2.setName("Функция 2");
			child0.add(box2);
		IDEF0Box box3 = new IDEF0Box("3");
			box3.setName("Функция 3");
			child0.add(box3);
		IDEF0Box box33 = new IDEF0Box("6");
			box33.setName("Функция 33");
			child0.add(box33);
		IDEF0Box box34 = new IDEF0Box("7");
			box34.setName("Функция 34");
			child0.add(box34);

		// выходящие
		IDEF0Box box4 = new IDEF0Box("4");
			box4.setName("Функция 4");
			child0.add(box4);
		IDEF0Box box5 = new IDEF0Box("5");
			box5.setName("Функция 5");
			child0.add(box5);
		
		
		// Стрелки
		IDEF0Arrow arrow;
		// входящие
		arrow = new IDEF0Arrow();		  
			child0.add(arrow);
			arrow.setSource(box2, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box3, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box33, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box34, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		
		// выходящие
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box1, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box4, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box1, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box5, IDEF0Arrow.INPUT);
		// управление
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(new IDEF0Border(), IDEF0Arrow.CONTROL);
			arrow.setTarget(box1, IDEF0Arrow.CONTROL);
		
		// размещение
		Layouter layouter = new IDEF0CenterLayout();
		layouter.layout(model);
		// Билдер
		builder = new IDEF0IDLModelBuilder();
		
		builder.setSource(model);
		builder.build();
		System.out.print(builder.getResult());
	}
	
	/** @modelguid {9B2E5655-EEA9-42B1-80D8-9CA5FB5412DF} */
	public void testBuildIDEF0Model2() {
		// Боксы 
		IDEF0Box root = model.getRootBox();		
			root.setName("Функция 0");
		// еще один уровень
		IDEF0Box box005 = new IDEF0Box("11");
			box005.setName("Промежуточный");
			root.getChildren().add(box005);
		
		IDEF0Box box0 = new IDEF0Box("0");
			box0.setName("Функция 0");
//			root.getChildren().add(box0);
			box005.getChildren().add(box0);
		IDEF0Box box01 = new IDEF0Box("12");
			box01.setName("F1");
			box005.getChildren().add(box01);
		List child0 = box0.getChildren();
		// центральный
		IDEF0Box box1 = new IDEF0Box("1");
			box1.setName("Функция 1");
			child0.add(box1);
		// входяцие
		IDEF0Box box2 = new IDEF0Box("2");
			box2.setName("Функция 2");
			child0.add(box2);
		IDEF0Box box3 = new IDEF0Box("3");
			box3.setName("Функция 3");
			child0.add(box3);
		IDEF0Box box33 = new IDEF0Box("6");
			box33.setName("Функция 33");
			child0.add(box33);
		IDEF0Box box34 = new IDEF0Box("7");
			box34.setName("Функция 34");
			child0.add(box34);

		// выходящие
		IDEF0Box box4 = new IDEF0Box("4");
			box4.setName("Функция 4");
			child0.add(box4);
		IDEF0Box box5 = new IDEF0Box("5");
			box5.setName("Функция 5");
			child0.add(box5);
		
		
		// Стрелки
		IDEF0Arrow arrow;
		// входящие
		arrow = new IDEF0Arrow();		  
			child0.add(arrow);
			arrow.setSource(box2, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box3, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box33, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box34, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		
		// выходящие
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box1, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box4, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box1, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box5, IDEF0Arrow.INPUT);
		// управление
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(new IDEF0Border(), IDEF0Arrow.CONTROL);
			arrow.setTarget(box1, IDEF0Arrow.CONTROL);
		
		// размещение
//		IDEF0CenterLayout layout = new IDEF0CenterLayout();
		IDEF0MixtureLayout layout = new IDEF0MixtureLayout();
		layout.layout(model);
		// Билдер
		builder = new IDEF0IDLModelBuilder();
		
		builder.setSource(model);
		builder.build();
		System.out.print(builder.getResult());

    }
	
	/** @modelguid {19DB3C28-FB0E-49B6-BBEE-B8F88179A5CE} */
	public void testBuildIDEF0Model3() {
		// Боксы 
		IDEF0Box root = model.getRootBox();		
			root.setName("Функция 0");
		// еще один уровень
		IDEF0Box box005 = new IDEF0Box("11");
			box005.setName("Промежуточный");
			root.getChildren().add(box005);
		
		IDEF0Box box0 = new IDEF0Box("0");
			box0.setName("Функция 0");
//			root.getChildren().add(box0);
			box005.getChildren().add(box0);
		IDEF0Box box01 = new IDEF0Box("12");
			box01.setName("F1");
			box005.getChildren().add(box01);
		List child0 = box0.getChildren();
		// центральный
		IDEF0Box box1 = new IDEF0Box("1");
			box1.setName("Функция 1");
			child0.add(box1);
		// входяцие
		IDEF0Box box2 = new IDEF0Box("2");
			box2.setName("Функция 2");
			child0.add(box2);
		IDEF0Box box3 = new IDEF0Box("3");
			box3.setName("Функция 3");
			child0.add(box3);
		IDEF0Box box33 = new IDEF0Box("6");
			box33.setName("Функция 33");
			child0.add(box33);
		IDEF0Box box34 = new IDEF0Box("7");
			box34.setName("Функция 34");
			child0.add(box34);

		// выходящие
		IDEF0Box box4 = new IDEF0Box("4");
			box4.setName("Функция 4");
			child0.add(box4);
		IDEF0Box box5 = new IDEF0Box("5");
			box5.setName("Функция 5");
			child0.add(box5);
		
		
		// Стрелки
		IDEF0Arrow arrow;
		// входящие
		arrow = new IDEF0Arrow();		  
			child0.add(arrow);
			arrow.setSource(box2, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box3, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box33, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box34, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		
		// выходящие
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box1, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box4, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box1, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box5, IDEF0Arrow.INPUT);
		// управление
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(new IDEF0Border(), IDEF0Arrow.CONTROL);
			arrow.setTarget(box1, IDEF0Arrow.CONTROL);
			
		// обратная связь
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box4, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box4, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box5, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box1, IDEF0Arrow.INPUT);

		// размещение
//		IDEF0CenterLayout layout = new IDEF0CenterLayout();
		IDEF0MixtureLayout layout = new IDEF0MixtureLayout();
		layout.layout(model);
		// Билдер
		builder = new IDEF0IDLModelBuilder();
		
		builder.setSource(model);
		builder.build();
		System.out.print(builder.getResult());

        
    }
	/**
	 * @see junit.framework.TestCase#setUp()
	 * @modelguid {B4D0B1ED-B69B-4278-AE7E-5AFF0B6F8CD7}
	 */
	protected void setUp() throws Exception {
		super.setUp();
		builder = new IDEF0IDLModelBuilder();
		model = new IDEF0Model();
	}

}
