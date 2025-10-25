/*
 * Created on 15.11.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.blackhorse.modeling.idef0;

/**
 * @author ra
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface Layouter {
    /**
     * Резмещает объекты модели в соответствии с внутренним алгоритмом 
     * @modelguid {BB217A2E-D012-43C7-9155-04964A53B5A6}
     */
    public abstract void layout(IDEF0Model model);

    /** 
     * Размещает дочериние элементы бокса в виде централизованной диаграммы
     * @modelguid {2598E08D-76A6-4F94-A39D-3AA1377A4FEB} 
     */
    public abstract void layoutDiagram(IDEF0Box box);
}