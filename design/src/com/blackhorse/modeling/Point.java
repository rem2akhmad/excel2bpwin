package com.blackhorse.modeling;
/** 
 * Двумерная точка
 * @modelguid {BB5875FA-84DD-45DE-8D3B-08A473D60207} 
 */
public class Point {
	/** @modelguid {37B6DE46-C60E-43DD-A332-30D65F478C03} */
	protected double x = 0;

	/** @modelguid {9E44B89F-E20B-4416-943E-49F268271FAD} */
	protected double y = 0;

	/** @modelguid {0E4638CE-2E43-43D9-8FB2-EC7D5642337F} */
	public String toString() {
		String res = "(" + x + "; " + y + ")";
		return res;
	}

	/** @modelguid {6AD9CB57-9C62-4461-9E77-5191414C9826} */
	public Point() {
		super();
	}

	/** @modelguid {CB8B8B22-E015-4F5B-B312-092454EED268} */
	public Point(double x, double y) {
		super();
		setX(x);
		setY(y);
	}

	/** @modelguid {BBEF5097-64BC-4E01-A146-1EF18F4D3C1C} */
	public double getX() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return x;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {B5EEC419-8490-46DE-9D31-96F15BD389E9} */
	public void setX(double aX) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		x = aX;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}

	/** @modelguid {B67D3905-CFA1-432E-8F7B-DF891C4E9650} */
	public double getY() {
		/*Begin Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
		
		return y;
		/*End Template Expansion{E7C2A923-82A4-486B-B18B-17F0C82C56E8}*/
	}

	/** @modelguid {2FE2E4AA-92CE-492D-B0B0-4ABF5F5A18ED} */
	public void setY(double aY) {
		/*Begin Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
		y = aY;
		/*End Template Expansion{29A86966-D8DE-43F4-8076-EC7F8A2869AE}*/
	}

}

