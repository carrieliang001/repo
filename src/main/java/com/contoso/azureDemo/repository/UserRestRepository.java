package com.contoso.azureDemo.repository;
import com.contoso.azureDemo.ui.model.response.UserRest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRestRepository extends JpaRepository<UserRest, String>{



}
