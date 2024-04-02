package com.carboncell.assessment.Repo;




import com.carboncell.assessment.Model.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token,Integer> {

    @Query( value = "Select t from Token t inner join User u on t.user.id=u.id\n" +
            "            where t.user_id=:userId and t.loggedOut=false",nativeQuery = true
    )
    List<Token> findAllTokenByUser(Integer userId);
    Optional<Token> findByToken(String token);
    @Transactional
    @Modifying
    @Query(value = "" +
            "Delete from Token t where t.user_id=:id",nativeQuery = true)
    void deleteAllTokenById(Integer id);
}

