package com.issuetracker.in.Repository;


import com.issuetracker.in.entity.Issues;
import com.issuetracker.in.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByusername(String username);

    @Query(value ="select count(*) from tbl_users where is_Active=true",nativeQuery = true)
    public int countActiveUsers();

    @Query(value ="select * from tbl_users where is_Active=true",nativeQuery = true)
    public List<User> findActiveUsers();
}
