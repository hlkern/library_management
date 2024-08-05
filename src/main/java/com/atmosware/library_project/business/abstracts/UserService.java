package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.entities.User;
import java.util.List;

public interface UserService {

    User getById(int id);

    List<User> getAll();

    void delete(int id);

    User update(User user);

    User add(User user);
}
