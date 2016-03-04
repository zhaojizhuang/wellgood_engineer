package com.wellgood.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ����ʵ��
 * @author Administrator
 *
 */
@DatabaseTable(tableName = "tb_task")
public class TaskEntity {
	@DatabaseField(generatedId=true)//������������ 
	
	private int _id=-1;
	@DatabaseField 
	private int id=-1;
	@DatabaseField
	private String name="default";
	@DatabaseField
	private String time="1970-1-1";

	// һ�������Ӧ����ڵ�
    @ForeignCollectionField(eager = true) // ����
    public ForeignCollection<TaskNodeEntity> taskNodes;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ForeignCollection<TaskNodeEntity> getTaskNodes() {
		return taskNodes;
	}
	public void setTaskNodes(ForeignCollection<TaskNodeEntity> taskNodes) {
		this.taskNodes = taskNodes;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "TaskEntity [id=" + id + ", name=" + name + ", time=" + time
				+ ", taskNodes=" + taskNodes + "]";
	}

}
