package com.wellgood.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * ����ڵ�ʵ��
 * @author Administrator
 *
 */
@DatabaseTable(tableName = "tb_tasknode")
public class TaskNodeEntity {
	 @DatabaseField(generatedId=true)//������������ 
	private int _id=-1;
	 @DatabaseField
	private int id=-1;
	@DatabaseField
	private String name="default";

	// �ⲿ�����ֶ�
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public TaskEntity taskinfo;
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
	public TaskEntity getTaskinfo() {
		return taskinfo;
	}
	public void setTaskinfo(TaskEntity taskinfo) {
		this.taskinfo = taskinfo;
	}
	@Override
	public String toString() {
		return "TaskNodeEntity [id=" + id + ", name=" + name + ", taskinfo="
				+ taskinfo + "]";
	}
}
