/**
 * 
 */
package com.demo.batch.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.batch.springbatch.model.User;

/**
 * @author kaihe
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
