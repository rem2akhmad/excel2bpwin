/*
 * Created on 11.11.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.blackhorse.modeling.idef0;

import java.util.List;

import com.blackhorse.modeling.Element;

/**
 * Размещает модель первого уровня "плиткой", а модель второго уровня "Централизованно"
 * @author ra
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * @modelguid {86BC08B8-31A8-49EE-B579-E035AF444CE1}
 */
public class IDEF0MixtureLayout extends IDEF0CenterLayout implements Layouter {

    /* (non-Javadoc)
     * @see com.blackhorse.modeling.idef0.IDEF0CenterLayout#layout(com.blackhorse.modeling.idef0.IDEF0Model)
     * @modelguid {88E91F37-06BE-4B60-8482-E5DA1E6B1399}
     */
    public void layout(IDEF0Model model) {
	    currentArrowId = -1;
		// Контекст
		IDEF0Box contexBox = (IDEF0Box)model.getRootBox().getChildren().get(0);
		super.setDimensionsBox(contexBox);
		contexBox.setId(0);
		centeringBox(contexBox);
		// размещение диаграммы первого уровня
		layoutGrid(contexBox);
		// размещение диаграмм второго уровня
		List kids = contexBox.getChildren();
		IDEF0CenterLayout centerLayout = new IDEF0CenterLayout();
		for(int i = 0; i < kids.size(); i++) {
		    Element el = (Element)kids.get(i);
		    if (el instanceof IDEF0Box) {
		        centerLayout.layoutDiagram((IDEF0Box)el);
		    }
		}
		
    }
    
    /**
     * Размещает дочерние элементы бокса в виде сеточной диаграммы
     * @param box 
     * @modelguid {D023A671-B196-47FA-8A7A-AE56CDEA7E66}
     */
    public void layoutGrid(IDEF0Box box) {
        double x = this.d;
        double y = 0;
//        Размещаем боксы в несколько рядов
        List children = box.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Element el = (Element)children.get(i);
            if (el instanceof IDEF0Box) {
                IDEF0Box bx = (IDEF0Box)el;
                setDimensionsBox(bx);
                if (x >= 1 - this.d - bx.getWidth()) {
                    x = this.d;
                }
                if (x == this.d) {
                    y += this.d + bx.getHeight(); 
                }
                if (y >= 1 - this.d - bx.getHeight()) {
                    y = 0;
                    x = 0;
                }
                bx.setX(x);
                bx.setY(y);
                x += this.d + bx.getWidth();
            }
        }
        // TODO перенумерация боксов в пределах диаграммы
        super.numberingBoxes(box);
    }
    
    /* (non-Javadoc)
     * @see com.blackhorse.modeling.idef0.IDEF0CenterLayout#setDimensionsBox(com.blackhorse.modeling.idef0.IDEF0Box)
     * @modelguid {37058443-3254-4033-90E6-C34D1DB97347}
     */
    protected void setDimensionsBox(IDEF0Box box) {
        box.setWidth(0.06);
        box.setHeight(0.06);
    }
}
