package com.emnsoft.emnsurvey.utils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.Optional;
import com.emnsoft.emnsurvey.domain.User;
import com.mongodb.client.MongoCollection;


public final class CustomRequests {

    public static Optional<User> getUserByLogin(String l, MongoTemplate t) {
        User u = null;
        final MongoCollection<Document> c = t.getCollection("user");
        final Document f = new Document("login", l);
        final Document uf = c.find(f).first();
        if (uf != null) {
            u = new User();
            u.setId(uf.get("_id", ObjectId.class).toString());
            u.setActive(uf.getBoolean("active", false));
            u.setEmail(uf.getString("email"));
            u.setFirstName(uf.getString("first_name"));
            u.setLangKey(uf.getString("lang_key"));
            u.setLastName(uf.getString("last_name"));
            u.setLogin(uf.getString("login"));
            u.setPassword(uf.getString("password"));
        }
        return Optional.ofNullable(u);
    }
}
