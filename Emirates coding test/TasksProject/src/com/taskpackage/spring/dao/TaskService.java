package com.taskpackage.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.taskpackage.spring.domain.Task;
import com.taskpackage.spring.util.DBUtil;

public class TaskService {

	private Connection connection;

	public TaskService() {
		connection = DBUtil.getConnection();
	}

	public void addMyTask(Task task) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into task(task_name,task_description,task_priority,task_status");
			preparedStatement.setString(1, task.getTaskName());
			preparedStatement.setString(2, task.getTaskDescription());
			preparedStatement.setString(3, task.getTaskPriority());
			preparedStatement.setString(4, task.getTaskStatus());
			preparedStatement.setInt(5, 0);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public Task getTaskByStatus(String taskStatus) {
		Task task = new Task();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from task where task_status=?");
			preparedStatement.setString(1, taskStatus);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				task.setTaskId(rs.getInt("task_id"));
				task.setTaskName(rs.getString("task_name"));
				task.setTaskDescription(rs.getString("task_description"));
				task.setTaskPriority(rs.getString("task_priority"));
				task.setTaskStatus(rs.getString("task_status"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return task;
	}
	
	public void getCompletedTasks(int taskId){
		try{
			PreparedStatement preparedStatement = connection
					.prepareStatement("update task set task_status='COMPLETED' where task_status=?");	
		preparedStatement.setInt(1, taskId);
		preparedStatement.executeUpdate();
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
    public void deleteTask(int taskid){
    	
    	try{
    		PreparedStatement preparedStatement = connection
					.prepareStatement("delete from task where task_Id=?");
    		preparedStatement.executeUpdate();
    		
    		//retieving all records
    		Statement statement= connection.createStatement();
    		ResultSet rs = statement.executeQuery("select * from task");
    		while(rs.next()){
    			Task task = new Task();
    			task.setTaskId(rs.getInt("task_id"));
				task.setTaskName(rs.getString("task_name"));
				task.setTaskDescription(rs.getString("task_description"));
				task.setTaskPriority(rs.getString("task_priority"));
				task.setTaskStatus(rs.getString("task_status"));
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    }
    
    public List<Task> getAllTasks(){
    	List<Task> tasksList = new ArrayList<Task>();
    	try{
    		Statement statement= connection.createStatement();
    		ResultSet rs= statement.executeQuery("select * from task");
    		while(rs.next()){
    			Task task = new Task();
    			task.setTaskId(rs.getInt("task_id"));
				task.setTaskName(rs.getString("task_name"));
				task.setTaskDescription(rs.getString("task_description"));
				task.setTaskPriority(rs.getString("task_priority"));
				task.setTaskStatus(rs.getString("task_status"));
				tasksList.add(task);
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		return tasksList;
    }
}
