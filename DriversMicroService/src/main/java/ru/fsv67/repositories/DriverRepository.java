package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fsv67.models.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    /**
     * Поиск водителя по имени, фамилии и снилс
     *
     * @param firstName имя водителя
     * @param lastName  фамилия водителя
     * @param snils     СНИЛС водителя
     * @return водитель с искомыми данными
     */
    Driver findByFirstNameOrLastNameOrSnils(String firstName, String lastName, String snils);

    Driver findBySnils(String snils);
}
