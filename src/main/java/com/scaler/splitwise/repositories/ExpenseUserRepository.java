package com.scaler.splitwise.repositories;

import com.scaler.splitwise.models.ExpenseUser;
import com.scaler.splitwise.models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.function.Function;

public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, Long> {


    List<ExpenseUser> findAllByUser(User user);
}
