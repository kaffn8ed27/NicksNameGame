package android.example.nicksnamegame.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM Person")
    List<Person> loadPersonList();

    @Query("SELECT headShotUrl FROM Person WHERE id LIKE :id LIMIT 1")
    String getHeadshotUrlById(String id);

    @Query("SELECT name FROM Person WHERE id LIKE :id LIMIT 1")
    String getNameById(String id);

    @Insert
    void insertAll(List<Person> people);

    @Delete
    void delete(Person person);
}
