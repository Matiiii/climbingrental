package pl.onsight.wypozyczalnia.model.listeners;

import pl.onsight.wypozyczalnia.model.entity.AbstractEntity;

import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.util.Date;

public class UpdateListener {
  @PreUpdate
  protected void onUpdate(AbstractEntity abstractEntity) {
    Date date = new Date();
    abstractEntity.setUpdateTime(new Timestamp(date.getTime()));
  }
}
