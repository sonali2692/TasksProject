package com.taskpackage.spring.taskcontroller;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taskpackage.spring.dao.TaskService;
import com.taskpackage.spring.domain.Task;

@RestController
public class TaskManagerController {

	TaskService taskService = new TaskService();

	
	/*retrieve all the tasks from database
	 * @author: sonali sharma
	 * methodName getAllTasks
	 */
	@RequestMapping(value = "/tasks", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Task> getAllTasks() {
		List<Task> tasks = taskService.getAllTasks();
		return tasks;

	}
	
	
	/*delete task based on taskId
	 * @author: sonali sharma
	 * methodName deleteTask
	 */
	@RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public List<Task> deleteTask(@PathVariable int taskId,
			@PathVariable String taskStatus) throws ParseException {
		taskService.deleteTask(taskId);
		return taskService.getAllTasks();

}
	
/*get all the completed tasks
 * @author sonali sharma
 * methodName getCompletedTasks
 */
	 @RequestMapping(value="/tasks/archive/{taskIds}",method = RequestMethod.POST,headers="Accept=application/json")
	 public List<Task> getCompletedTasks(@PathVariable int[] taskIds) {	
		 for(int i=0;i<taskIds.length;i++){
			 taskService.getCompletedTasks(taskIds[i]);	
			 
		 }
	  List<Task> tasks=taskService.getAllTasks();
	  return tasks;
	
	 }

	   /*add task in the table and tasklist
		 * @author: sonali sharma
		 * methodName addMyTask
		 */
	@RequestMapping(value = "/tasks/insert/{taskName}/{taskDesc}/{taskPriority}/{taskStatus}", method = RequestMethod.POST, headers = "Accept=application/json")
	public List<Task> addMyTask(@PathVariable String taskName,
			@PathVariable String taskDesc, @PathVariable String taskPriority,
			@PathVariable String taskStatus) throws ParseException {
		Task task = new Task();
		task.setTaskName(taskName);
		task.setTaskDescription(taskDesc);
		task.setTaskPriority(taskPriority);
		task.setTaskStatus(taskStatus);
		taskService.addMyTask(task);
		return taskService.getAllTasks();

	}
}