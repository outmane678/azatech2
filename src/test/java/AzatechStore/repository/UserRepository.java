package AzatechStore.repository;

import AzatechStore.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Trouver un utilisateur par son email (utilisé pour l'authentification)
    Optional<UserEntity> findByEmail(String email);

    // Vérifier si un utilisateur existe par son email
    boolean existsByEmail(String email);

    // Trouver un utilisateur par son nom d'utilisateur
    Optional<UserEntity> findByUsername(String username);

    // Vérifier si un utilisateur existe par son nom d'utilisateur
    boolean existsByUsername(String username);
}
