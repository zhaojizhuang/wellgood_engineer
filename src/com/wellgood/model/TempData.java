package com.wellgood.model;

/**
 * ���������࣬���ڳ�����ת��ʱ�����
 * @author zhaojizhuang
 *
 */
public final class TempData {
	//����
	TaskEntity taskEntity;
	//����ڵ�
	TaskNodeEntity taskNodeEntity;
	
	
	
	@Override
	public String toString() {
		return "TempData [taskEntity=" + taskEntity + ", taskNodeEntity="
				+ taskNodeEntity + "]";
	}
	public TaskEntity getTaskEntity() {
		return taskEntity;
	}
	public void setTaskEntity(TaskEntity taskEntity) {
		this.taskEntity = taskEntity;
	}
	public TaskNodeEntity getTaskNodeEntity() {
		return taskNodeEntity;
	}
	public void setTaskNodeEntity(TaskNodeEntity taskNodeEntity) {
		this.taskNodeEntity = taskNodeEntity;
	}
	/**
	 * ����ʵ��
	 */
	private static TempData ins = new TempData();
	public static TempData getIns() {
		return ins;
	}

	
}
