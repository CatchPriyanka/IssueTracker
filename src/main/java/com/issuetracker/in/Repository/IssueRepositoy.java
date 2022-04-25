package com.issuetracker.in.Repository;


import com.issuetracker.in.entity.Issues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository

public interface IssueRepositoy extends JpaRepository<Issues,Long>  {

    @Query(value ="select * from tbl_issues order by estimated_points desc",nativeQuery = true)
    public List<Issues> findEstimatedStories();
}
