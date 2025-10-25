/*
 * Created on 11.11.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.blackhorse.modeling.idef0;

import java.util.Comparator;

/**
 * Координатный компаратор. Сравнивает боксы по их положению на диаграмме (сверху вниз, с лева на право)
 * @modelguid {6B78FCE3-5137-4E2B-BB05-64C4E8D4CC6E}
 */
public class IDEF0BoxCoordinateComparator implements Comparator {

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     * @modelguid {40F829BE-7D2C-4631-A331-BAF00446083C}
     */
    public int compare(Object arg0, Object arg1) {
        if (arg0 == null) {
            return -1;
        }
        if (arg1 == null) {
            return 1;
        }
        
        IDEF0Box box1 = (IDEF0Box)arg0;
        IDEF0Box box2 = (IDEF0Box)arg1;
        
        if (box1.getX() > box2.getX()) {
            return 1;
        }
        if (box1.getX() < box2.getX()) {
            return -1;
        }
        if (box1.getX() == box2.getX()) {
            if (box1.getY() > box2.getY()) {
                return -1;
            }
            if (box1.getY() < box2.getY()) {
                return -1;
            }
        }
        return 0;
    }

}
