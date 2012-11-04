package com.example.cmput301;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private DatabaseManager dbman;

    private TaskManager() {
        this.dbman = new DatabaseManager("database_tables");
    }

    /**
     * Attaches task to the local database and returns the task which was added
     * along with it's id.
     *
     * @param task The task you want to be added.
     * @return The task which was added with the local task id attached.
     */
    public Task addTask(Task task) {

        Task addedTask = dbman.postLocal(task);

        return addedTask;
    }

    /**
     * Share the task with the given id.
     *
     * @param id The id of the task you want shared.
     */
    public void shareTask(String id) {

        //Get the task from the database.
        Task localTask = dbman.getLocalTask(id);

        //Post to the webservice.
        Task remoteTask = WebService.put(localTask);

        //--
        //Since we are replacing the id we need to remove the old task and put in
        //a new one with an id generated by the webservice.
        //--

        //Remove the local version of the task.
        dbman.deleteLocalTask(localTask.getId());

        //Add the remote version of the task.
        remoteTask.setStatus(Task.STATUS_SHARED);
        dbman.postLocal(remoteTask);

    }

    public boolean deleteTask(String id) {

        WebService.delete(id);

        return true;
    }

    /**
     * Post a response to a task with the given id in the database.
     *
     * @param response
     * @param task
     */
    public void postResponse(Task task, Response response) {

        //If its private dont update the webservice first.
        if (task.getStatus() == Task.STATUS_PRIVATE) {
            task.addResponse(response);
            dbman.updateTask(task);
        } else {
            Task updatedTask = WebService.post(task, response);
            dbman.updateTask(updatedTask);
        }
    }

    public ArrayList<Task> getPrivateTasks() {
        ArrayList<Task> privateList = new ArrayList<Task>();

        for (Task task : this.dbman.getLocalTaskList()) {
            if (task.getStatus() == Task.STATUS_PRIVATE) {
                privateList.add(task);
            }
        }

        return privateList;
    }

    public ArrayList<Task> getSharedTasks() {
        ArrayList<Task> sharedList = new ArrayList<Task>();

        for (Task task : this.dbman.getLocalTaskList()) {
            if (task.getStatus() == Task.STATUS_SHARED) {
                sharedList.add(task);
            }
        }

        return sharedList;
    }

    public ArrayList<Task> getUnansweredTasks() {
        ArrayList<Task> unansweredList = new ArrayList<Task>();

        for (Task task : this.dbman.getRemoteTaskList()) {
            if (task.getResponses().size() == 0) {
                unansweredList.add(task);
            }
        }

        return unansweredList;
    }

    public ArrayList<Task> getRemoteTasks() {
        return this.dbman.getRemoteTaskList();
    }
}
