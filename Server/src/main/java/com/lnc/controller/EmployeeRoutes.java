package com.lnc.controller;

import com.lnc.service.employee.*;
import com.lnc.service.employee.improviseItem.AnswerSubmission;
import com.lnc.service.employee.improviseItem.ImproviseItemList;
import com.lnc.service.employee.improviseItem.ImproviseItemQuestions;

public class EmployeeRoutes implements RouteHandler {
    private final EmployeeFeedback employeeFeedback = new EmployeeFeedback();
    private final AllNotificationsOfEmployee allNotificationsOfEmployee = new AllNotificationsOfEmployee();
    private final TodaysMenu todaysMenu = new TodaysMenu();
    private final TomorrowsMenu tomorrowsMenu = new TomorrowsMenu();
    private final MenuVote menuVote = new MenuVote();
    private final EmployeeProfileEditor employeeProfileEditor = new EmployeeProfileEditor();
    private final ImproviseItemList improviseItemList = new ImproviseItemList();
    private final ImproviseItemQuestions improviseItemQuestions = new ImproviseItemQuestions();
    private final AnswerSubmission answerSubmission = new AnswerSubmission();

    @Override
    public String handle(String path, String data) throws Exception {
        return switch (path) {
            case "/employee/feedback" -> employeeFeedback.getEmployeeFeedback(data);
            case "/employee/getNotifications" -> allNotificationsOfEmployee.getAllNotificationsOfEmployee(data);
            case "/employee/todaysMenu" -> todaysMenu.getTodaysMenu(data);
            case "/employee/tomorrowsMenu" -> tomorrowsMenu.getTomorrowsMenu(data);
            case "/employee/vote" -> menuVote.voteForMenu(data);
            case "/employee/editProfile" -> employeeProfileEditor.editEmployeeProfile(data);
            case "/employee/viewImproviseItem" -> improviseItemList.getImproviseItemList();
            case "/employee/getQuestions" -> improviseItemQuestions.getQuestions(data);
            case "/employee/submitAnswers" -> answerSubmission.submitAnswer(data);
            default -> throw new IllegalArgumentException("Invalid path for EmployeeRoutes: " + path);
        };
    }
}
