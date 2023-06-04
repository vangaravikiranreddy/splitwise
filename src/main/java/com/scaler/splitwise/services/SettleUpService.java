package com.scaler.splitwise.services;

import com.scaler.splitwise.models.Expense;
import com.scaler.splitwise.models.ExpenseUser;
import com.scaler.splitwise.models.Group;
import com.scaler.splitwise.models.User;
import com.scaler.splitwise.repositories.ExpenseRepository;
import com.scaler.splitwise.repositories.ExpenseUserRepository;
import com.scaler.splitwise.repositories.GroupRepository;
import com.scaler.splitwise.repositories.UserRepository;
import com.scaler.splitwise.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class SettleUpService {

    private ExpenseRepository expenseRepository;

    private GroupRepository groupRepository;

    private SettleUpStrategy settleUpStrategy;

    private UserRepository userRepository;

    private ExpenseUserRepository expenseUserRepository;

    @Autowired
    public SettleUpService(ExpenseRepository expenseRepository,
                           GroupRepository groupRepository,
                           SettleUpStrategy settleUpStrategy,
                           UserRepository userRepository,
                           ExpenseUserRepository expenseUserRepository
                           ) {
                                this.expenseRepository = expenseRepository;
                                this.groupRepository = groupRepository;
                                this.settleUpStrategy = settleUpStrategy;
                                this.userRepository = userRepository;
                                this.expenseUserRepository = expenseUserRepository;
                              }

    List<Expense> settleUpUser(Long userId) {
        /*
           1.Get all expense in which user is part of
           2.Iterate through all of the expense and find out of all people involved in expense who owes what
           3.Use Min and Max heap approach to find all transaction to be made
           4.Return the transactions involving the user
        */
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException();
        }

        User user = userOptional.get();

        List<ExpenseUser> expenseUsers = expenseUserRepository.findAllByUser(user);

        Set<Expense> expenses = new HashSet<>();

        for (ExpenseUser expenseUser : expenseUsers) {
            expenses.add(expenseUser.getExpense());
        }

        List<Expense> expenseToSettle = settleUpStrategy.settleUp(expenses.stream().toList());

        List<Expense> expensesToReturn = new ArrayList<>();

        for (Expense expense : expenseToSettle) {
             for (ExpenseUser expenseUser: expense.getExpenseUsers()) {
                 if (expenseUser.getUser().equals(user)) {
                     expensesToReturn.add(expense);
                     break;
                 }
             }
        }

        return expensesToReturn;

    }

    public List<Expense> settleUpGroup(Long groupId) {
       /*
          1.Get all expenses that were made in that group
          2.Iterate through all of the expense and find out of all people involved in expense who owes what
          3.Use Min and Max heap approach to find all transaction to be made
          4.Return all transaction
        */

        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if (groupOptional.isEmpty()) {
            throw new RuntimeException();
        }

        Group group = groupOptional.get();

        List<Expense> expenses = expenseRepository.findAllByGroup(group);

        List<Expense> expenseToSettle = settleUpStrategy.settleUp(expenses);

        return expenseToSettle;
    }
}
