package com.task_management.controller;

import com.task_management.model.Task;
import com.task_management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

        @Autowired
        private TaskService taskService;

        @GetMapping
        public String getAllTasks(Model model)
        {
            List<Task> tasks = taskService.getAllTasks();
            model.addAttribute("tasks", tasks);
            return "task-list";
        }

        //Show the Task Form
        @GetMapping("/new")
        public String showCreateTaskForm(Model model)
        {
            Task task = new Task();
            model.addAttribute("task", task);
            return "task-form";
        }

        //Show the edit
        @GetMapping("/editTask/{id}")
        public String showEditTaskForm(@PathVariable Long id, Model model) {
            Optional<Task> optionalTask = taskService.getTaskById(id);
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                model.addAttribute("task", task);
            } else {
                return "redirect:/tasks";
            }
        return "task-form";
        }

        //Create or update task
        @PostMapping("/saveTask")
        public String saveTask(@ModelAttribute("task") Task task) {
           if(task.getId() != null) {
               taskService.updateTask(task.getId(), task);
           }else{
               taskService.createTask(task);
           }
           return "redirect:/tasks";
        }

        //Delete Task
        @GetMapping("/deleteTask/{id}")
        public String deleteTask(@PathVariable Long id)
        {
            taskService.deleteTaskById(id);
            return "redirect:/tasks";
        }

    }
