package br.com.zup.ecommerce.shared.security;

import br.com.zup.ecommerce.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager manager;

    @Value("${security.username-query}")
    private String query;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> resultList = manager.createQuery(query)
                                    .setParameter("login", username)
                                    .getResultList();
        Assert.isTrue(resultList.size() <= 1, "More than one user found with login " + username);

        if (resultList.isEmpty())
            throw new UsernameNotFoundException("There is no user with login " + username);

        return new ActiveUser(resultList.get(0));
    }
}
