package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/*
*类名和方法不能修改
 */
public class Schedule {
    private List<TaskInfo> hangUpLists = new ArrayList<TaskInfo>();
    private List<TaskInfo> runNodeLists = null;
    private ConcurrentHashMap<Integer,TaskInfo> map = null;
    private ConcurrentHashMap<Integer,Integer> taskMap = null;
    public int init() {
        hangUpLists = null;
        runNodeLists = null;
        map = null;
        taskMap = null;
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
        if (map==null){
            map = new ConcurrentHashMap<Integer,TaskInfo>();
        }
        // 服务节点编号非法
        if(nodeId <= 0){
            return ReturnCodeKeys.E004;
        }
        // 服务节点已注册
        if (map.get(nodeId)!=null) {
            return ReturnCodeKeys.E005;
        }
        TaskInfo task = new TaskInfo();
        task.setNodeId(nodeId);
        map.put(nodeId,task);

        return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
        if (map==null){
            map = new ConcurrentHashMap<Integer,TaskInfo>();
        }
        if (hangUpLists==null){
            hangUpLists = new ArrayList<TaskInfo>();
        }
        // 如果服务节点编号未被注册
        if(map.get(nodeId)==null){
            return ReturnCodeKeys.E007;
        }
        //如果服务节点编号小于等于0
        if (nodeId <= 0 ) {
            return ReturnCodeKeys.E004;
        }
        TaskInfo taskInfo = map.get(nodeId);
        if (0!=taskInfo.getTaskId()){
            hangUpLists.add(taskInfo);
        }
        map.remove(nodeId);
        return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption) {
        if (taskMap==null){
            taskMap = new ConcurrentHashMap<Integer,Integer>();
        }
        // 如果任务编号小于等于0
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        // 如果相同任务编号任务已经被添加
        if (taskMap.get(taskId)!=null){
            return ReturnCodeKeys.E010;
        }
        taskMap.put(taskId,consumption);
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
        if (taskMap==null){
            taskMap = new ConcurrentHashMap<Integer,Integer>();
        }
        // 如果任务编号小于等于0
        if (taskId <= 0) {
            return ReturnCodeKeys.E009;
        }
        // 如果指定编号的任务未被添加
        if (taskMap.get(taskId)==null){
            return ReturnCodeKeys.E012;
        }
        taskMap.remove(taskId);
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        //TODO 方法未实现
        return ReturnCodeKeys.E000;
    }

}
