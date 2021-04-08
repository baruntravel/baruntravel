package me.travelplan.component.custommysqldialect;

import org.hibernate.spatial.dialect.mysql.MySQL8SpatialDialect;

public class CustomMySqlDialect extends MySQL8SpatialDialect {
    public CustomMySqlDialect() {
        super();
    }
}
