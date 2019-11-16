package com.github.bex1111.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.github.bex1111.util.Constans.CHECKSUM;
import static com.github.bex1111.util.Constans.FULLNAME;

@AllArgsConstructor
public class MSVRepository {

    private DB db;
    private static final String MSVCOLLECTIONNAME = "msv_migrate";

    public List<DBObject> findAll() {
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find();
        return cursor.toArray();
    }

    public List<DBObject> findAllSortWithVersion() {
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find().sort(new BasicDBObject("version", 1));
        return cursor.toArray();
    }

    public Optional<String> findCheckSum(String fileName) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(FULLNAME, fileName);
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find(whereQuery);
        return cursor.size() == 0 ? Optional.empty() : Optional.ofNullable(cursor.next().get(CHECKSUM).toString());
    }

    public void remove(DBObject dbObject) {
        db.getCollection(MSVCOLLECTIONNAME).remove(dbObject);
    }

    public boolean versionExist(String version) {
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find(new BasicDBObject("version", version));
        return cursor.hasNext();
    }

    public Optional<String> getMaxVersion() {
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find().sort(new BasicDBObject("version", -1)).limit(1);
        return cursor.size() == 0 ? Optional.empty() : Optional.ofNullable(cursor.next().get("version").toString());
    }

    public void insertNewFile(BasicDBObject basicDBObject) {
        db.getCollection(MSVCOLLECTIONNAME).insert(basicDBObject);
    }


}
