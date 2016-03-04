package com.wellgood.model;

/**
 * 缓存数据类，用于程序跳转的时候调用
 * @author zhaojizhuang
 *
 */
public final class TempData {
	//任务
	TaskEntity taskEntity;
	//任务节点
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
	 * 返回实例
	 */
	private static TempData ins = new TempData();
	public static TempData getIns() {
		return ins;
	}

	
}
