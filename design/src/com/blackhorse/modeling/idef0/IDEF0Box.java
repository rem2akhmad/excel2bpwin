package com.blackhorse.modeling.idef0;

import java.util.ArrayList;

import com.blackhorse.modeling.Point;


/** 
 * Бокс. Представляет прямоугольник на диаграмме (для IDEF0 это функция)
 * @modelguid {B5FEC383-4914-48DB-AA92-125947A9A02F} 
 */
public class IDEF0Box extends IDEF0Element {
	/** @modelguid {1AA443C4-6FE6-434A-974F-2844DF6C8AD1} */
	protected double x;

	/** @modelguid {EAF498B9-2B81-4352-A7AF-1CF9010B09F4} */
	protected double y;

	/** @modelguid {16602F7B-1DAE-41BE-BA80-9662A7FFB37E} */
	protected double height;

	/** @modelguid {CDFB0DBA-E1AF-47F1-AD82-8DC9C4CC6786} */
	protected double width;
	/** @modelguid {4DEBE8BC-4F0F-49BA-B59C-47E8B34DDF53} */
	protected IDEF0Border leftBorder = new IDEF0Border();
	/** @modelguid {28066DC3-9317-4EE5-AAB0-FDF88C839E8D} */
	protected IDEF0Border rightBorder = new IDEF0Border();
	/** @modelguid {43AE3953-4202-47A1-95E6-61A1B55FC61C} */
	protected IDEF0Border topBorder = new IDEF0Border();
	/** @modelguid {53107BB0-AD03-4389-9D67-CE0945DEDAE2} */
	protected IDEF0Border bottomBorder = new IDEF0Border();
	/**
	 * 
	 * @modelguid {634991AE-4A1D-4A01-9C3F-485140EEC834}
	 */
	protected IDEF0Diagram diagram = new IDEF0Diagram(this);

	/**
	 * 
	 * @modelguid {E062FBAB-B6B6-4338-9999-DD2775F295A4}
	 */
	protected java.util.List elements = new ArrayList();

	/**
	 * 
	 * @modelguid {AF6BEF82-55E4-42AC-84A4-7FB1D14ABDED}
	 */
	protected IDEF0Box parent;

	
	/** @modelguid {9F84136E-F2F5-4E3C-9BA4-3A321F84E527} */
	public IDEF0Box() {
		super();
	}

	/** @modelguid {5075532B-277B-4A06-8FFF-345EE817F157} */
	public IDEF0Box(String id) {
		super(id);
	}

	
	/** @modelguid {6001BDF7-080F-4F8C-BB26-BFCA9059EE15} */
	public Point getPoint1() {
		return new Point(this.x, this.y);
	}
	
	/** @modelguid {CB785223-BD68-4607-97C4-40A83C1996BC} */
	public Point getPoint2() {
		return new Point(this.x + width, this.y - height);
	}
	/** @modelguid {28ADF1CC-93FB-4A60-A4FB-F3AD297357D2} */
	public double getHeight() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return height;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {2672949D-ECEA-40F1-96A9-100C68A362CB} */
	public void setHeight(double aHeight) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		height = aHeight;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}

	/** @modelguid {A898C5F2-843B-42DE-862D-86E76010A039} */
	public double getY() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return y;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {364E89BF-1C7D-46CA-9CD3-D5D883E4E0EF} */
	public void setY(double aY) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		y = aY;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}

	/** @modelguid {3B0DA5BC-56D4-4CC9-A006-2099D4DF3973} */
	public double getWidth() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return width;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {7C3ABFE1-7707-4A9A-88E5-2955D5A5F10B} */
	public void setWidth(double aWidth) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		width = aWidth;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}

	/** @modelguid {B2821B96-BFE4-4F01-9355-47D73282AF74} */
	public double getX() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return x;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {2D15C71E-E4F9-4E8C-B8C3-FF3A87248EF4} */
	public void setX(double aX) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		x = aX;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}

	/** @modelguid {7B04CAA2-D9E7-4498-A215-749DBB0E8F26} */
	public IDEF0Diagram getDiagram() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return diagram;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {8EC23D9A-2A6D-412D-908F-89B9EECA3008} */
	public IDEF0Box getParent() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return parent;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {A7516968-AA37-45B3-B567-900C47ABF793} */
	public void setParent(IDEF0Box aParent) {
		/*Begin Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
		
		parent = aParent;		
		/*End Template Expansion{D1E75204-483E-4241-B354-308DD4404AD1}*/
		parent.getChildren().add(this);
	}

	/** @modelguid {930F98BE-39E2-407B-89C0-A8ACDD983214} */
	public java.util.List getChildren() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return elements;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {4CBA60F8-E370-4E82-B640-31177BDC6195} */
	public IDEF0Border getBottomBorder() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return bottomBorder;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {3D5B56CB-C8E5-43A4-A881-6C5947F29DA8} */
	public IDEF0Border getRightBorder() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return rightBorder;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {5763E314-A00D-4D3E-B630-9005B1EF88A2} */
	public IDEF0Border getLeftBorder() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return leftBorder;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {BC981992-7A16-487D-98B3-8AADF0480CC8} */
	public IDEF0Border getTopBorder() {
		/*Begin Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
		
		return topBorder;
		/*End Template Expansion{CDDD73EF-732A-4415-AB6F-287939122DAA}*/
	}

	/** @modelguid {2905FFF3-B3F0-4FE7-8FC8-B8E93C307F7A} */
	public void setPoint1(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/** @modelguid {C1705328-D555-40D0-AA7F-2EED993CF1D9} */
	public void setPoint1(Point point) {
		this.x = point.getX();
		this.y = point.getY();
	}
	
	/** @modelguid {F38A218D-DEAD-40C1-BF94-4A6181EAEE8F} */
	public void setId(int id) {
		this.id = Integer.toString(id);
	}

}

