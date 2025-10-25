package com.blackhorse.modeling.idl.test;

import java.util.List;

import junit.framework.TestCase;

import com.blackhorse.modeling.idef0.IDEF0Arrow;
import com.blackhorse.modeling.idef0.IDEF0Box;
import com.blackhorse.modeling.idef0.IDEF0Model;
import com.blackhorse.modeling.idl.IDEF0IDLModelBuilder;

/**
 * @author ra
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 * @modelguid {5EF64D28-4F4E-4119-AE1D-20E0B1F6C0F3}
 */
public class IDEF0IDLModelBuilderTest extends TestCase {
	/** @modelguid {B7B7358A-273C-41D8-A965-F35F97A632A2} */
	protected IDEF0Box mainBox;

	/** @modelguid {763B15DD-747D-49B7-89B6-D5D35D859FD1} */
	protected IDEF0IDLModelBuilder builder;

	/** @modelguid {0DCFD564-0BF5-4394-8D3E-3384327A6947} */
	private IDEF0Model model;

	/**
	 * Constructor for IDEF0IDLModelBuilderTest.
	 * @param arg0
	 * @modelguid {B0D41870-4D5B-4DCE-9734-8FE0E5BF7196}
	 */
	public IDEF0IDLModelBuilderTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see TestCase#setUp()
	 * @modelguid {DE5BFD11-511F-44D6-A100-E84DE3B1A16B}
	 */
	protected void setUp() throws Exception {
		super.setUp();
		

	}


	/** @modelguid {E3FD7EB2-9AFF-4168-8F53-28B987567193} */
	public void testBuildModel() {
				// Модель
		model = new IDEF0Model();
		
		// Боксы
		IDEF0Box root = model.getRootBox();		
			root.setName("Функция 0");
		
		IDEF0Box box0 = new IDEF0Box("0");
			box0.setName("Функция 0");
			root.getChildren().add(box0);
		List child0 = box0.getChildren();
		IDEF0Box box1 = new IDEF0Box("1");
			box1.setName("Функция 1");
			child0.add(box1);
		IDEF0Box box2 = new IDEF0Box("2");
			box2.setName("Функция 2");
			child0.add(box2);
		IDEF0Box box3 = new IDEF0Box("3");
			box3.setName("Функция 3");
			child0.add(box3);
		
		// Стрелки
		IDEF0Arrow arrow;
		arrow = new IDEF0Arrow();		  
			child0.add(arrow);
			arrow.setSource(box1, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box0, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box2, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box0, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box0, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box3, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box0.getTopBorder(), IDEF0Arrow.CONTROL);
			arrow.setTarget(box3, IDEF0Arrow.CONTROL);
		
		// Билдер
		builder = new IDEF0IDLModelBuilder();
		
		builder.setSource(model);
		builder.build();
		System.out.print(builder.getResult());
	}
	
	
	/** @modelguid {98E8B11B-7E9D-4E94-97BA-242F79B0A30B} */
	public void testBuildModel2() {
		// Боксы
		IDEF0Box root = model.getRootBox();		
			root.setName("Функция 0");
		
		IDEF0Box box0 = new IDEF0Box("0");
			box0.setName("Функция 0");
			root.getChildren().add(box0);
		List child0 = box0.getChildren();
		IDEF0Box box1 = new IDEF0Box("1");
			box1.setName("Функция 1");
			child0.add(box1);
		IDEF0Box box2 = new IDEF0Box("2");
			box2.setName("Функция 2");
			child0.add(box2);
		IDEF0Box box3 = new IDEF0Box("3");
			box3.setName("Функция 3");
			child0.add(box3);
		
		// Стрелки
		IDEF0Arrow arrow;
		arrow = new IDEF0Arrow();		  
			child0.add(arrow);
			arrow.setSource(box1, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box0, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box2, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box0, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box0, IDEF0Arrow.OUTPUT);
			arrow.setTarget(box3, IDEF0Arrow.INPUT);
		arrow = new IDEF0Arrow();
			child0.add(arrow);
			arrow.setSource(box0.getTopBorder(), IDEF0Arrow.CONTROL);
			arrow.setTarget(box3, IDEF0Arrow.CONTROL);
		
		// Билдер
		builder = new IDEF0IDLModelBuilder();
		
		builder.setSource(model);
		builder.build();
		System.out.print(builder.getResult());

	}
}
