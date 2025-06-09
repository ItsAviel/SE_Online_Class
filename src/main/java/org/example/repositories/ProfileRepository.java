package org.example.repositories;

import org.example.models.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    @Override
    Optional<Profile> findById(Long id);

//    Profile findByUserId(Long userId);
//    @Override
//    List<Profile> findAll();

}
