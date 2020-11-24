package com.emnsoft.emnsurvey.config.dbmigrations;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.emnsoft.emnsurvey.config.Constants;


@ChangeLog
public class InitialMigration {
    
    @ChangeSet(id="data initialization", order = "000", author = "emngaiden") 
    public void initData(MongockTemplate db) {
        db.save(Constants.ADMIN_USER);
    }

}
