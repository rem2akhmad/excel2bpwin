package com.blackhorse.modeling.idef0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.blackhorse.modeling.Element;
import com.blackhorse.modeling.Point;

/**
 * Размещает элементы на диаграммах модели с одним центральным боксом
 * @modelguid {5452630B-1D19-473E-9244-C9120BF94DFC}
 */
public class IDEF0CenterLayout implements Layouter {
    /**
     * Представляет отрезок линии 
     * 
     * @modelguid {25607427-582F-47EF-B228-8294054BC6FF}
     */
    private class Segment {
        /**
         * Начало отрезка
         * @modelguid {9D0A984E-B0FF-45A7-9697-571A6575695A}
         */
        private Point start = new Point();
        /**
         * Конец отрезка
         * @modelguid {5ABA16B6-FE1F-4D51-9C21-BD1FC17E410F}
         */
        private Point end = new Point();
        
		/** @modelguid {A3026F4C-393A-46CF-AA79-03A59C09E9AD} */
        public Segment(Point pStart, Point pEnd) {
            setStart(pStart);
            setEnd(pEnd);
        }
        
        /**
         * Проверяет, накладывается ли отрезок seg на текущий
         * 
         * @param seg
         * @return true- отрезок накладывается false - отрезок не накладывается
         * @modelguid {7927AF8F-ADE2-4747-BEBA-89B2E25C5BE5}
         */
        public boolean isSuperposition(Segment seg) {
            if (this.isHorizontal() != seg.isHorizontal()) {
                return false;
            }
            if (isHorizontal()) { // горизонтальный
                // делаем отрезки однонаправленными
                double startX1 = this.getStart().getX();
                double endX1   = this.getEnd().getX();
                double startX2 = seg.getStart().getX();
                double endX2   = seg.getEnd().getX();
                
                if (this.getStart().getX() > this.getEnd().getX()) {
                    startX1 = this.getEnd().getX();
                    endX1 = this.getStart().getX();
                }
                if (seg.getStart().getX() > seg.getEnd().getX()) {
                    startX2 = seg.getEnd().getX();
                    endX2   = seg.getStart().getX();
                }
                if (endX1 > startX2 && startX1 < endX2 &&
                    this.getStart().getY() == seg.getStart().getY()) {
                    return true;
                }
                return false;
            } else { // вертикальный
                double startY1 = this.getStart().getY();
                double endY1   = this.getEnd().getY();
                double startY2 = seg.getStart().getY();
                double endY2   = seg.getEnd().getY();
                
                if (this.getStart().getY() > this.getEnd().getY()) {
                    startY1 = this.getEnd().getY();
                    endY1 = this.getStart().getY();
                }
                if (seg.getStart().getY() > seg.getEnd().getY()) {
                    startY2 = seg.getEnd().getY();
                    endY2   = seg.getStart().getY();
                }
                
                if (endY1 > startY2 && startY1 < endY2 &&
                    this.getStart().getX() == seg.getStart().getX()) {
                    return true;
                }
                return false;
            }
        }
		/** @modelguid {D2C028CF-C37A-439B-93AA-8FA60AC03A84} */
        public boolean isHorizontal() {
            if (start.getY() == end.getY()) {
                return true;
            } 
            return false;
        }
        /**
         * @return Returns the end.
         * @modelguid {DBAF8E6E-0B16-4541-9090-44F14D062AB0}
         */
        public Point getEnd() {
            return end;
        }
        /**
         * @param end The end to set.
         * @modelguid {85FAB7D3-00AE-4198-8B42-77B461EF9CC4}
         */
        public void setEnd(Point end) {
            this.end = end;
        }
        /**
         * @return Returns the start.
         * @modelguid {83E04F00-A39C-4C96-9AEE-30E12B4BA311}
         */
        public Point getStart() {
            return start;
        }
        /**
         * @param start The start to set.
         * @modelguid {8EE21DAA-3389-4DA2-8EEF-F2EEA8EAA10C}
         */
        public void setStart(Point start) {
            this.start = start;
        }
    }
	/** 
	 * Левая граница для источников, вдоль которой они выстраиваются
	 * @modelguid {155F26C9-20E5-435C-902D-AA4D135BA6B0} 
	 */
	protected double sourcesBound = 0.01;
	/** 
	 * Левая граница приемников, вдоль которой они выстраиваются
	 * @modelguid {3DAF6CDA-6F83-4FAB-A64D-C98CFD4BF220} 
	 */
	protected double receiversBound = 0.7;
	
	/** 
	 * Расстояние между боксами, на которое они могут сближаться
	 * @modelguid {5BBB9ECD-FF47-4AD2-96B3-A79A36BFDE92} 
	 */
	protected double d = 0.02;
	
	/** 
	 * Текущий идентификатор стрелки
	 * @modelguid {926A5794-C5EA-4387-B720-DDD784C68B11} 
	 */
	protected int currentArrowId = -1;
	/**
	 * Префикс для СNumber
	 * @modelguid {83806C12-1088-4827-917D-80094FA7E7D3}
	 */
    private String cNumberPrefix = "F";
	/**
	 * Резмещает объекты модели в соответствии с внутренним алгоритмом 
	 * @modelguid {BB217A2E-D012-43C7-9155-04964A53B5A6}
	 */
	public void layout(IDEF0Model model) {
	    currentArrowId = -1;
		// Контекст
		IDEF0Box contexBox = (IDEF0Box)model.getRootBox().getChildren().get(0);		
		setDimensionsBox(contexBox);
		contexBox.setId(0);
		centeringBox(contexBox);
		
		// размещаем элементы всех диаграмм
		layoutBox(contexBox);
		
	}
	/**
	 * Рекурсивно размещает дочерние боксы Бокса box
	 * @param box
	 * @modelguid {4F877D2A-0315-4DC3-8795-0FCE0975CA0A}
	 */
	private void layoutBox(IDEF0Box box) {
	    layoutDiagram(box);
	    List children = box.getChildren();
		// размещаем дочерние элементы всех боксов
		for (int i = 0; i < children.size(); i++) {
			Element el = (Element)children.get(i);
			if (el instanceof IDEF0Box) {
				IDEF0Box bx = (IDEF0Box) el;
				layoutBox(bx);
			}
		}
	}
	/** 
	 * Размещает дочериние элементы бокса в виде централизованной диаграммы
	 * @modelguid {2598E08D-76A6-4F94-A39D-3AA1377A4FEB} 
	 */
	public void layoutDiagram(IDEF0Box box) {
		currentArrowId = 1;
		
		List children = box.getChildren();
		if (children == null) {
			return;
		}
		if (children.size() == 0) {
			return;
		}
		IDEF0Box centerBox = (IDEF0Box)children.get(0);

		// выделяем боксы-источники
		List sources = getSources(children, centerBox);
		// размещаем боксы-источники
		layoutSources(sources);
		
		
		// размещаем центральный бокс
		setCenterBoxDimensions(centerBox);
		centeringBox(centerBox);
		

		// выделяем боксы-приемники
		List receivers = getReceivers(children, centerBox);
		// размещаем боксы-приемники
		layoutReceivers(receivers);
		
		// размещение стрелок
		layoutArrows(children);
		// дисперсия по границам боксов
		disperse(children);
		// дисперсия путей стрелок
		dispersePath(children);
		
		numberingBoxes(box);
		setFontsBoxes(box);
	}
	
	/**
	 * Перенумерация дочерних боксов бокса box
	 * @param box бокс
	 * @modelguid {C1947744-6DF6-4E02-AE9A-1CF140DF9ABF}
	 */
	protected void numberingBoxes(IDEF0Box box) {
	    List children = box.getChildren();
	    List boxes = new ArrayList();
	    for (int i = 0; i < children.size(); i++) {
	        Element el = (Element)children.get(i);
	        if (el instanceof IDEF0Box) {
	            boxes.add(el);
	        }
	    }
	    // сортируем
	    Collections.sort(boxes, new IDEF0BoxCoordinateComparator());
	    // нумеруем
	    for (int i = 0; i < boxes.size(); i++) {
	        IDEF0Box bx = (IDEF0Box)boxes.get(i);
	        bx.setId(i + 1);
	    }
	    // задаем названия боксов и номера диаграмм
	    for (int i = 0; i < boxes.size(); i++) {
	        IDEF0Box bx = (IDEF0Box)boxes.get(i);
	        bx.setName(box.getId() + " : " + bx.getName());
	        bx.getDiagram().setCNumber(cNumberPrefix + bx.getId());
	    }	    
	}
	
	/**
	 * Установка ширифтов для боксов
	 * должен вызываться после перенумерации
	 * @param box
	 * @modelguid {DD9037BD-B12E-44B1-9F8C-E5A9B0389B8E}
	 */
	protected void setFontsBoxes(IDEF0Box box) {
	    List children = box.getChildren();
	    for (int i = 0; i < children.size(); i++) {
	        Element el = (Element)children.get(i);
	        if (el instanceof IDEF0Box) {
	            IDEF0Box bx = (IDEF0Box)el;	            
	            if (i == 0) { // шрифт центрального бокса должен отличаться
	                bx.setName("{LWI I 5 255 255 255}" +bx.getName());
	            } else {
	                bx.setName("{LWI I 4 255 255 255}" +bx.getName());
	            }
	        }
	    }
	}
	
	/**
	 * Распределение накладывающихся отрезков путей
	 * @param children все элементы одной диаграммы 
	 * @modelguid {2D65E3ED-724C-4041-9A43-F5524091AC7D}
	 */
	protected void dispersePath(List children) {
	    // TODO сделать дисперсию отрезков
	    // величина дисперсии отрезков
	    double dD = 0.005;
	    // дисперсия по горизонтали
	    	List hSegments = getHorizontalSegments(children);
	    	List buffer = new ArrayList();	    	
	    	while (hSegments.size() > 0) {
	    	    Segment seg = (Segment) hSegments.get(0);
	    	    while (isListIntersect(buffer, seg) && seg.getStart().getY() > dD) {
	    	        double newY = seg.getStart().getY() - dD;
	    	        seg.getStart().setY(newY);
	    	        seg.getEnd().setY(newY);
	    	    }
	    	    // удаляем из списка отрезков и помещаем в буффер
	    	    buffer.add(seg);
	    	    hSegments.remove(seg);
	    	}
	    	// провести дисперсию среди этого списка
	    // дисперсия по горизонтали
	    	List vSegments = getVerticalSegments(children);
	    	buffer.clear();
	    	while (vSegments.size() > 0) {
	    	    Segment seg = (Segment)vSegments.get(0);
	    	    while (isListIntersect(buffer, seg) && seg.getStart().getX() > dD && seg.getStart().getX() < 1 - dD) {
	    	        double newX = 0;
	    	        if (seg.getStart().getX() > receiversBound ) {
	    	            newX = seg.getStart().getX() + dD;
	    	        } else {
	    	            newX = seg.getStart().getX() - dD;
	    	        }
	    	        seg.getStart().setX(newX);
	    	        seg.getEnd().setX(newX);
	    	    }
	    	    // удаляем из списка отрезков и помещаем в буффер
	    	    buffer.add(seg);
	    	    vSegments.remove(seg);
	    	}
	}
	
	/**
	 * Возвращает список горизонтальных отрезков на диаграмме
	 * @param children
	 * @return
	 * @modelguid {0949F670-97D1-4F8A-BD70-72C8718905EA}
	 */
	private List getHorizontalSegments(List children) {
	    List ret = new ArrayList();
	    for (int i = 0; i < children.size(); i++) {
	        Element el = (Element)children.get(i);
	        if (el instanceof IDEF0Arrow) {
	            List path = ((IDEF0Arrow)el).getPath();
	            // проходим по всем точкам за исключением первой и последней
	            for(int j = 1; j < path.size()-2; j++) {
	                Point p1 = (Point) path.get(j);
	                Point p2 = (Point) path.get(j+1);
	                Segment seg = new Segment(p1, p2);
	                if (seg.isHorizontal()) {
	                    ret.add(seg);
	                }
	            }
	        }
	    }
	    return ret;
	}
	
	/**
	 * Возвращает список вертикальных отрезков на диаграмме
	 * @param children
	 * @return
	 * @modelguid {2D874DFD-B680-490A-9CEB-A33E506F8EE9}
	 */
	private List getVerticalSegments(List children) {
	    // TODO слить с getHorisotalSegments
	    List ret = new ArrayList();
	    for (int i = 0; i < children.size(); i++) {
	        Element el = (Element)children.get(i);
	        if (el instanceof IDEF0Arrow) {
	            List path = ((IDEF0Arrow)el).getPath();
	            // проходим по всем точкам за исключением первой и последней
	            for(int j = 1; j < path.size()-2; j++) {
	                Point p1 = (Point) path.get(j);
	                Point p2 = (Point) path.get(j+1);
	                Segment seg = new Segment(p1, p2);
	                if (!seg.isHorizontal()) {
	                    ret.add(seg);
	                }
	            }
	        }
	    }
	    return ret;
	}
	/*
	 * проверяет, пересекается ли отрезок seg c каким-нибудь отрезком из списка
	 * 
	 * @modelguid {A4D48D43-E618-4A14-BF77-56908B0FCCCE}
	 */
	private boolean isListIntersect(List list, Segment seg) {	    
	    for (int i = 0; i < list.size(); i++) {
	        if (((Segment)list.get(i)).isSuperposition(seg)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * Разрежает концы стрелок на границах бокса
	 * @param box бокс
	 * @modelguid {053E0357-5711-45E8-8E43-55426FFD3C6C}
	 */
	protected void disperse(List children) {	    
	    List arrows = null;	    
	    // разрежение по вертикальным границам
	    for (int i = 0 ; i < children.size(); i++) {
	        Element el = (Element)children.get(i);
	        if (el instanceof IDEF0Box) {
	            IDEF0Box box = (IDEF0Box)el;
	            // по входам	            
	            verticalDisperse(children, box, IDEF0Arrow.INPUT);
	            // по выходам
	            verticalDisperse(children, box, IDEF0Arrow.OUTPUT);
	            // по управлению
	            arrows = getBoxArrows(children, box, IDEF0Arrow.CONTROL);
	            horizontalDisperse(arrows, box);
	            // по механизму
	            arrows = getBoxArrows(children, box, IDEF0Arrow.MECHANISM);
	            horizontalDisperse(arrows, box);

	        }
	    }
	}
	/**
	 * Дисперсия по вертикали
	 * @param arrows
	 * @param box
	 * @modelguid {23EE6EB9-2B9D-4511-B3CD-A98E6338FBDD}
	 */
	private void verticalDisperse(List children, IDEF0Box box, int type) {
	    double dD;
	    List arrows = getBoxArrows(children, box, type);
	    dD = box.getHeight() / (arrows.size() + 1);
        for (int j = 0; j < arrows.size(); j++) {
            IDEF0Arrow arrow = (IDEF0Arrow) arrows.get(j);
            List path = arrow.getPath();
            Point p1 = null;
            Point p2 = null;
            if (type == IDEF0Arrow.INPUT) {
                p1 = (Point)path.get(path.size() - 1);
                p2 = (Point)path.get(path.size() - 2);
            }
            if (type == IDEF0Arrow.OUTPUT) {
                p1 = (Point)path.get(0);
                p2 = (Point)path.get(1);
            }
            p1.setY(box.getY() - dD * (j + 1));
            p2.setY(box.getY() - dD * (j + 1));
        }
	}
	/**
	 * Дисперсия по горизонтали
	 * @param arrows
	 * @param box
	 * @modelguid {FE1B0B94-92B0-4EDB-A207-95E843117542}
	 */
	private void horizontalDisperse(List arrows, IDEF0Box box) {
	    double dD;
	    dD = box.getWidth() / (arrows.size() + 1);
        for (int j = 0; j < arrows.size(); j++) {
            IDEF0Arrow arrow = (IDEF0Arrow) arrows.get(j);
            List path = arrow.getPath();
            Point last = (Point)path.get(path.size() - 1);
            Point preLast = (Point)path.get(path.size() - 2);
            last.setX(box.getX() + dD * (j + 1));
            preLast.setX(box.getX() + dD * (j + 1));
        }
	    
	}
	
	/**
	 * Возвращает список стрелок, связанных с боксом box по стороне типа type (см IDEF0Arrow)
	 * @param children список элементов диаграммы
	 * @param box бокс
	 * @param type тип стороны по которой связана эта стрелка
	 * @return список стрелок
	 * @modelguid {0F25A3F4-DE6B-44C3-9254-2A71B46696AD}
	 */
	protected List getBoxArrows(List children, IDEF0Box box, int type) {
	    List arrows = new ArrayList();
	    for (int i = 0 ; i < children.size(); i++) {
	        Element el = (Element)children.get(i);
	        if (el instanceof IDEF0Arrow) {
	            IDEF0Arrow arrow = (IDEF0Arrow)el;
	            if ((arrow.getSourceType() == type && arrow.getSource().equals(box)) ||
	                (arrow.getTargetType() == type && arrow.getTarget().equals(box)) ) {
	                arrows.add(arrow);
	            }
	        }
	    }
	    return arrows;
	}

	/**
	 * Размещение стрелок
	 * @param children список элементов, которые содержаться в декомпозируемом боксе
	 * @modelguid {3CB1E832-0F84-4368-BA7A-3A057692D6E9}
	 */
	protected void layoutArrows(List children) {
		// находим входящие стрелки для бокса
		for (int i = 0; i < children.size(); i++) {
			Element el = (Element)children.get(i);
			if (el instanceof IDEF0Arrow) {
				IDEF0Arrow arrow = (IDEF0Arrow)el;
				arrow.setId(currentArrowId++);
				calcPath(arrow);
				layoutLabel(arrow);
			}
		}
		
		// TODO по возможности сделать стрелки не пересекающимися
		
	}
	
	
	/**
	 * Размещает надпись стрелки над линией
	 * @param arrow стрелка
	 * @modelguid {CBD87D83-5BD4-46E4-9CDB-70419A28C853}
	 */
	protected void layoutLabel(IDEF0Arrow arrow) {
	    // Путь должен быть не пустым и иметь минимум две точки
	    if (arrow.getPath() == null) {
	        return;
	    }
	    if (arrow.getPath().size() > 1) {
	        Point p1 = (Point)arrow.getPath().get(0);
	        Point p2 = (Point)arrow.getPath().get(arrow.getPath().size()-1);
	        Point point = null;
	        if (p1.getX() < p2.getX()) {	// стрелка идет с лева на право
	            point = new Point(p1.getX() + this.d, p1.getY());
	        } else {					 	// стрелка идет с права на лево
	            point = new Point(p1.getX() - this.d, p1.getY());
	        }
	        arrow.setLabelCoordinates(point);
	    }
	    // TODO разбивка на несколько строк
	}
	
	/** 
	 * Вычисляет путь для стрелки 
	 * @param arrow стрелка, дял которой вычисляется путь
	 * @modelguid {C9217C38-3ABD-45B5-884A-5E258D4B8AFF} 
	 */
	protected void calcPath(IDEF0Arrow arrow) {
		// получение начальной точки
		Point sourcePoint = calcSourcePoint(arrow);
		
		// получение конечной точки
		Point targetPoint = calcTargetPoint(arrow);
		
		// вычисление пути		
		if (sourcePoint.getX() <= targetPoint.getX()) {
		    calcForwardPath(arrow, sourcePoint, targetPoint);
		} else {
		    calcBackwardPath(arrow, sourcePoint, targetPoint);
		}
		// TODO возможно нужно провести оптимизацию для устранения того случая, когда три точки
		// находятся на одной линии

	}

	
	/**
	 * Вычисление обратного пути между точками sourcePoint и targetPoint
	 * В основном используется для вычисления пути, когда sourcePoint лежит правее targetPoint
     * @param arrow стрелка, для которой вычисляется путь
     * @param sourcePoint исходная точка
     * @param targetPoint конечная точка
     * @modelguid {CD5A1750-CCA8-45FC-8CD9-CF4F61157B71}
     */
    private void calcBackwardPath(IDEF0Arrow arrow, Point sourcePoint, Point targetPoint) {
        List path = arrow.getPath();
        double diff = this.d;
        
        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();
        Point p4 = new Point();
        
        p1.setX(sourcePoint.getX() + diff); p1.setY(sourcePoint.getY());
        p2.setX(sourcePoint.getX() + diff); p2.setY(1 - d);
        p3.setX(targetPoint.getX() - diff); p3.setY(1 - d);
        p4.setX(targetPoint.getX() - diff); p4.setY(targetPoint.getY());
        
        path.add(sourcePoint);
        path.add(p1);
        path.add(p2);
        path.add(p3);
        path.add(p4);
        path.add(targetPoint);
    }
    /**
	 * Вычисление прямого пути между точками sourcePoint и targetPoint.
	 * Упрощенное вычисление пути. Путь от начальной точки до конечной состоить из одной
	 * промежуточной точки. Не учитывается то, часть пути может перекрываться боксами 
	 * В основном используется для вычисления пути, когда sourcePoint лежит левее targetPoint
     * @param arrow
     * @param sourcePoint
     * @param targetPoint
     * @modelguid {7D67D308-B65F-4AA6-9A9F-579794441CA6}
     */
    private void calcForwardPath(IDEF0Arrow arrow, Point sourcePoint, Point targetPoint) {
        List path = arrow.getPath();
        path.add(sourcePoint);
		
		double diff = this.d;		

//		if (arrow.getSourceType() != IDEF0Arrow.CONTROL) {
//			Point p1 = new Point();
//			p1.setX(sourcePoint.getX() + diff);
//			p1.setY(sourcePoint.getY());
//			path.add(p1);
//		}
		
		    Point p2 = new Point();
			p2.setX(targetPoint.getX() - diff);
			p2.setY(sourcePoint.getY());
			path.add(p2);
		if (arrow.getTargetType() != IDEF0Arrow.CONTROL) {	
			Point p3 = new Point();
			p3.setX(targetPoint.getX() - diff);
			p3.setY(targetPoint.getY());
			path.add(p3);
		}
		
		path.add(targetPoint);
    }
    /**
	 * Вычисляет конечную точку для стрелки arrow
     * @param arrow
     * @return
     * @modelguid {21A7FB12-9EF5-4CF3-9DF2-FE919E0D03F4}
     */
    private Point calcTargetPoint(IDEF0Arrow arrow) {
        Point targetPoint = new Point();
		switch (arrow.getTargetType()) {
			case IDEF0Arrow.CONTROL : {
				if (arrow.getTarget() instanceof IDEF0Box) {
					IDEF0Box box = (IDEF0Box)arrow.getTarget();
					targetPoint.setX(box.getX() + box.getWidth() / 2);
					targetPoint.setY(box.getY() - box.getHeight());
				}
				if (arrow.getTarget() instanceof IDEF0Border) {
					// это мало вероятно :))
				}
			} break;
			case IDEF0Arrow.MECHANISM : {
				if (arrow.getTarget() instanceof IDEF0Box) {
					IDEF0Box box = (IDEF0Box)arrow.getTarget();
					targetPoint.setX(box.getX() + box.getWidth() / 2);
					targetPoint.setY(box.getY());
				}
			} break;
			case IDEF0Arrow.INPUT : {
				if (arrow.getTarget() instanceof IDEF0Box) {
					IDEF0Box box = (IDEF0Box)arrow.getTarget();
					targetPoint.setX(box.getX());
					targetPoint.setY(box.getY() - box.getHeight() / 2);
				}

			} break;
			case IDEF0Arrow.OUTPUT : {
				if (arrow.getTarget() instanceof IDEF0Border) {
					targetPoint.setX(0.9999);
					targetPoint.setY(0.5);
				}
			} break;
		}
        return targetPoint;
    }
    /**
	 * Вычисляет начальную точку для стрелки arrow
     * @param arrow стрелка
     * @return
     * @modelguid {15895526-928E-4821-B922-310A25859B7C}
     */
    private Point calcSourcePoint(IDEF0Arrow arrow) {
        Point sourcePoint = new Point();
		switch (arrow.getSourceType()) {
			case IDEF0Arrow.INPUT : {
				if (arrow.getSource() instanceof IDEF0Border) {
					sourcePoint.setX(0);
					sourcePoint.setY(0.5);
				}
			} break;
			case IDEF0Arrow.OUTPUT : {
				if (arrow.getSource() instanceof IDEF0Box) {
					IDEF0Box box = (IDEF0Box)arrow.getSource();
					sourcePoint.setX(box.getX() + box.getWidth());
					sourcePoint.setY(box.getY() - box.getHeight() / 2);
				}
			} break;
			case IDEF0Arrow.MECHANISM : {
				if (arrow.getSource() instanceof IDEF0Border) {
					sourcePoint.setX(0.9999);
					sourcePoint.setY(0.5);
				}
			} break;
			case IDEF0Arrow.CONTROL : {
				if (arrow.getSource() instanceof IDEF0Border) {
					sourcePoint.setX(0.5);
					sourcePoint.setY(0);
				}
			} break;
		}
        return sourcePoint;
    }
    /**
	 * Размещает бокс в середине диаграммы 
	 * @modelguid {1BE6C703-3B63-4082-A562-5FC135F72A14} */
	protected void centeringBox(IDEF0Box box) {
		box.setPoint1(0.5 - box.getWidth() / 2, 0.5 + box.getHeight() / 2);
//		box.setId(currentBoxId++);
	}
	
	/** 
	 * Установка размеров бокса в зависимости от текста, содержащегося в нем
	 * @param box бокс для которого нужно установить размеры
	 * @modelguid {C4ABB104-A0BA-4147-8224-FEA8E65BCD47} 
	 */
	protected void setDimensionsBox(IDEF0Box box) {
		// TODO заглушка. заменить на реальное вычисление размеров бокса в зависимости от отекста
		box.setHeight(0.1);
		box.setWidth(0.2);
	}
	/**
	 * Установка размеров центрального бокса
	 * @param box
	 * @modelguid {4D7EA4FA-1F97-4674-8BB4-26C85B8D11FB}
	 */
	protected void setCenterBoxDimensions(IDEF0Box box) {
	   box.setHeight(0.2);
	   box.setWidth(0.2);
	}
	/**
	 * Размещает боксы-источники
	 * @param boxes список боксов-источников (элементов типа IDEF0Box)
	 * @modelguid {A6A3462C-74E6-48B1-9564-4B6C14490C46} 
	 */
	protected void layoutSources(List boxes) {
		if (boxes == null) {
			return;
		}
		double dD = ((double)1) / (boxes.size() + 1);
		int k = 1;
		double y = d;
		for (int i = 0 ; i < boxes.size(); i++ ) {
			Element el = (Element)boxes.get(i);
			if (el instanceof IDEF0Box) {
				IDEF0Box box = (IDEF0Box) el;
				setDimensionsBox(box);
				y = dD*k + (d + box.getHeight()) / 2;				
				box.setPoint1(sourcesBound, y);
				k++;
			}
		}
	}
	
	/**
	 * Размещает боксы-приемники
	 * @param boxes список боксов-приемников (элементов типа IDEF0Box)
	 * @modelguid {603ADE82-2FFB-4E8E-B8ED-1BEBFF5B853A} */
	protected void layoutReceivers(List boxes) {
		if (boxes == null) {
			return;
		}
		
		double dD = ((double)1) / (boxes.size() + 1);
		int k = 1;
		double y = d;
		for (int i = 0 ; i < boxes.size(); i++ ) {
			Element el = (Element)boxes.get(i);
			if (el instanceof IDEF0Box) {
				IDEF0Box box = (IDEF0Box) el;
				setDimensionsBox(box);
				y = dD*k + (d + box.getHeight()) / 2;				
				box.setPoint1(receiversBound, y);
				k++;
//				box.setId(currentBoxId++);
			}
		}
	}
	
	/** 
	 * Из списка элементов выделяет боксы-источники и возвращает их в виде отдельного списка.
	 * Если источников не найдено, то возвращается null
	 * @param elements список элементов, на которые декомпозируется бокс верхнего уровня (элементов типа Element)
	 * @param box бокс, относительно которого выделяются источники
	 * @return список боксов-источников
	 * @modelguid {B5398F1F-1358-46DC-B765-66BA101D0E8D} 
	 */
	protected List getSources(List elements, IDEF0Box box) {
		Set set = new HashSet();
		// проходим по всем элементам
		for (int i = 0; i < elements.size(); i++) {
			Element el = (Element)elements.get(i);
			// выбираем из них стрелки
			if (el instanceof IDEF0Arrow) {
				IDEF0Arrow arrow = (IDEF0Arrow) el;
				// берем окончания стрелки и проверяем, что она оканчивается на том боксе,
				// относительно которого ищем источники. Если это так, то проверяем источник, и если это бокс
				// то помещаем его в итоговый список
				if (arrow.getTarget().equals(box) && 
					arrow.getTargetType() == IDEF0Arrow.INPUT &&
					arrow.getSource() instanceof IDEF0Box ) {
					set.add(arrow.getSource());
//					sources.add((IDEF0Box)arrow.getSource());
				}
			}
		}
		List sources = new ArrayList(set);
		return sources;
	}
	
	/**
	 * Из списка элементов выделяет боксы-приемники и возвращает их в виде отдельного списка
	 * @param elements список элементов, на которые декомпозируется бокс верхнего уровня (элементов типа Element)
	 * @param box бокс, относительно которго выделяются приемники
	 * @return список боксов-приемников
	 * @modelguid {B4503A99-7F03-45F0-A5C6-2003DD7E6872} */
	protected List getReceivers(List elements, IDEF0Box box) {
		Set set = new HashSet();
		// работает аналогично getSources
		for (int i = 0; i < elements.size(); i++) {
			Element el = (Element)elements.get(i);
			if (el instanceof IDEF0Arrow) {
				IDEF0Arrow arrow = (IDEF0Arrow) el;
				if (	arrow.getSource().equals(box) &&
						arrow.getSourceType() == IDEF0Arrow.OUTPUT &&
						arrow.getTarget() instanceof IDEF0Box) {
				    set.add(arrow.getTarget());
//					receivers.add((IDEF0Box)arrow.getTarget());
				}
			}			
		}
		List receivers = new ArrayList(set);
		return receivers;
	}
    /**
     * @return Returns the cNumberPrefix.
     * @modelguid {621B208E-4F24-4C47-A904-1131F6E4C682}
     */
    public String getCNumberPrefix() {
        return cNumberPrefix;
    }
    /**
     * Устанавливает префикс для CNumber
     * @param numberPrefix The cNumberPrefix to set.
     * @modelguid {5FAD1A0B-0FAB-4C78-AA82-62F5BA802BA6}
     */
    public void setCNumberPrefix(String numberPrefix) {
        cNumberPrefix = numberPrefix;
    }
    /**
     * Возвращает значение левой границы, по которой выравниваются боксы-приемники
     * @return граница приемников
     * @modelguid {A7C7E727-05A2-4F42-AA14-040C0E360F3B}
     */
    public double getReceiversBound() {
        return receiversBound;
    }
    /**
     * Устанавливает значение левой границы, по кторой выравниваются боксы-приемники
     * @param граница приемников
     * @modelguid {90E0A608-9A0B-4DBF-979D-51A28673D163}
     */
    public void setReceiversBound(double receiversBound) {
        this.receiversBound = receiversBound;
    }
    /**
     * Возвращает левую границу, по которой выравниваются боксы-источники
     * @return граница источников
     * @modelguid {45ED4D07-3CE3-4672-B28E-6A4520E6B32B}
     */
    public double getSourcesBound() {
        return sourcesBound;
    }
    /**
     * Устанавливает значение левой границы, по кторой выравниваются боксы-источники
     * @param граница источников
     * @modelguid {F2466726-5E9A-4D84-A9EE-6EA56B0A3732}
     */
    public void setSourcesBound(double sourcesBound) {
        this.sourcesBound = sourcesBound;
    }
}

