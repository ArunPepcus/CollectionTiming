package com.pepcus.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pepcus.models.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
