package com.blackhorse.modeling;
/** 
 * —троитель модели
 * @modelguid {953827D6-8625-4E4C-B86C-D7BFAB9D8036} 
 */
public interface ModelBuilder {
	/**
	 * —троить модель использу€ внутренние данные (уже установленный источник данных) 
	 * @modelguid {5BE4E567-FAD1-4950-A614-17EA164852A0} 
	 */
	public void build();

	/**
	 * —тоить модель использу€ источник данных source 
	 * @modelguid {34BCA2CB-F0C8-4939-A6A0-B9E4982CDB17} 
	 */
	public void build(Object source);

	/**
	 * ”станавливает источник данных по которому, в последствии, будет построена модель 
	 * @modelguid {D3D5F737-7D7A-4C54-A577-8BFFE73E5FA5} 
	 */
	public void setSource(Object source);

	/**
	 * ¬озвращает результат построени€ модели 
	 * @modelguid {5A5A8865-2D5A-4D94-A3EB-020013E3FC59} 
	 */
	public Object getResult();

}

