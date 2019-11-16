package com.github.bex1111.migrate;

import com.mongodb.DB;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BaseImporter {

    protected DB db;


}
